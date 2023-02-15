// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.exbin.voxella.launcher.game.terasology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.regex.Pattern;

import java.util.logging.Logger;

/**
 * Starts and manages a game process.
 * <p>
 * Many of the characteristics of this task are described in
 * {@link GameService}, as it's the expected access point to this.
 * <p>
 * (An individual {@link Task} lasts as long as the process does, the
 * {@link javafx.concurrent.Service Service} provides a stable reference that
 * doesn't have to be updated with each new task.)
 * <p>
 * Beware javafx's treatment of exceptions that come up in {@link Task Tasks}.
 * An uncaught exception will be stored in its {@link #exceptionProperty()} and
 * never see its way to an executor's uncaught exception handler. It won't be
 * until you call this object's {@link #get()} that it will be thrown again.
 */
class RunGameTask /*implements Runnable*/ {

    static final int EXIT_CODE_OK = 0;

    /**
     * The output of the process is tested against this for a sign that it
     * launched successfully.
     * <p>
     * (We don't yet have a formal protocol about this with Terasology, so it
     * won't show up if its minimum log level is higher than
     * {@link Level#INFO INFO}. But the default is to include
     * {@link Level#INFO INFO}, so this should often work.)
     */
    static final Predicate<String> START_MATCH = Pattern.compile("TerasologyEngine.+Initialization completed")
            .asPredicate();

    /**
     * How long a process has to live in order for us to call it successful.
     * <p>
     * If there's no output with {@link #START_MATCH} and it hasn't crashed
     * after being up this long, it's probably okay.
     */
    static final Duration SURVIVAL_THRESHOLD = Duration.ofSeconds(10);

    private static final Logger logger = Logger.getLogger(RunGameTask.class.getName());

    protected final Callable<Process> starter;

    /**
     * Indicates whether we have set the {@link Task#updateValue value} of this
     * Task yet.
     * <p>
     * The value is stored in a
     * {@link javafx.beans.property.SimpleObjectProperty property} we can't
     * directly access from this task's thread, so it remembers it here.
     */
    private boolean valueSet;

//    private FxTimer successTimer;

    /**
     * @param starter called as soon as the Task starts to start a new process
     */
    RunGameTask(Callable<Process> starter) {
        this.starter = starter;
    }

    /**
     * Starts the process, returns when it's done.
     *
     * @return true when the process exits with no error code
     * @throws GameStartError if the process failed to start at all
     * @throws GameExitError if the process terminates with an error code
     * @throws GameExitError if the process quit before
     * {@link #SURVIVAL_THRESHOLD}
     * @throws InterruptedException if this thread was interrupted while waiting
     * for something — doesn't come up as much as you might expect, because
     * waiting on a {@code read} call of the process's output <em>can not be
     * interrupted</em> (Java's rule, not ours)
     */
    public void run() throws GameStartError, GameExitError, InterruptedException, GameExitTooSoon {
        Objects.requireNonNull(this.starter);
//        verify(!this.isDone());
        Process process;
        try {
            process = this.starter.call();
        } catch (Exception e) {
            throw new GameStartError(e);
        }
        monitorProcess(process);
    }

    /**
     * Monitors the output and exit status of a process.
     *
     * @param process the running process
     */
    void monitorProcess(Process process) throws InterruptedException, GameExitError, GameExitTooSoon {
        Objects.requireNonNull(process);
        logger.log(Level.INFO, "Game process is {0}", process);
        updateMessage("Game running as process " + process.pid());

//        startTimer();

        // log each line of process output
        try (BufferedReader gameOutput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            gameOutput.lines().forEachOrdered(this::handleOutputLine);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception reading process output.", e);
        }

        try {
            // The output has closed, so we _often_ have the exit value immediately, but apparently
            // not always — the tests were flaky. To be safe, waitFor.
            int exitValue = process.waitFor();
            logger.log(Level.INFO, "Game closed with the exit value '{0}'.", exitValue);

            if (exitValue == EXIT_CODE_OK) {
                updateMessage("Process complete.");
            } else {
                updateMessage("Process exited with code " + exitValue);
                throw new GameExitError(exitValue);
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Interrupted while waiting for game process exit.", e);
            throw e;
        }

//        if (successTimer != null) {
//            // No error code, but the game quit before our timer went off? That doesn't
//            // seem right!
//            removeTimer();
//            throw new GameExitTooSoon();
//        }
    }

    /**
     * Called with each line of the process's output.
     * <p>
     * Expect the Process has
     * {@linkplain ProcessBuilder#redirectErrorStream() merged output and error streams}.
     *
     * @param line a line of output, decoded to String, with trailing newline
     * stripped
     */
    protected void handleOutputLine(String line) {
        if ((!valueSet) && START_MATCH.test(line)) {
//            declareSurvival();
        }
        logger.log(Level.INFO, "Game output: {0}", line);
    }

    private void updateMessage(String message) {

    }

//    private void declareSurvival() {
//        valueSet = true;
//        this.updateValue(true);
//        removeTimer();
//    }
//
//    private void timerComplete() {
//        logger.log(Level.INFO, "Process has been alive at least {0}, calling it good.", SURVIVAL_THRESHOLD);
//        declareSurvival();
//    }
//
//    protected void startTimer() {
//        successTimer = FxTimer.runLater(SURVIVAL_THRESHOLD, this::timerComplete);
//    }
//
//    protected void removeTimer() {
//        if (successTimer != null) {
//            successTimer.stop();
//            successTimer = null;
//        }
//    }
//
//    protected void failed() {
//        removeTimer();
//    }

    public abstract static class RunGameError extends Exception {
    }

    /**
     * The process failed to start.
     * <p>
     * Check its {@link #getCause() cause}.
     */
    public static class GameStartError extends RunGameError {

        GameStartError(final Exception e) {
            super();
            this.initCause(e);
        }
    }

    /**
     * The process quit with an {@linkplain #exitValue error code}.
     * <p>
     * These codes are platform-dependent. All know for sure is that it is not
     * {@link #EXIT_CODE_OK}.
     */
    public static class GameExitError extends RunGameError {

        public final int exitValue;

        GameExitError(final int exitValue) {
            this.exitValue = exitValue;
        }

        @Override
        public String getMessage() {
            return toString();
        }
    }

    /**
     * The process only lasted a brief time.
     */
    public static class GameExitTooSoon extends RunGameError {
    }
}
