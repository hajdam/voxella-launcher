/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.voxella.launcher.api.utils;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Logger utility methods.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class LoggerUtils {

    private LoggerUtils() {
    }

    public static void log(@Nonnull Logger logger, @Nonnull Level level, @Nonnull String text, @Nonnull Object... params) {
        LogRecord logRecord = new LogRecord(level, text);
        logRecord.setParameters(params);
        logger.log(logRecord);
    }

    public static void log(@Nonnull Logger logger, @Nonnull Level level, @Nonnull String text, @Nullable Throwable throwable, @Nonnull Object... params) {
        LogRecord logRecord = new LogRecord(level, text);
        if (throwable != null) {
            logRecord.setThrown(throwable);
        }
        logRecord.setParameters(params);
        logger.log(logRecord);
    }
}
