// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.exbin.voxella.launcher.game.terasology.util;

public interface ProgressListener {

    void update();

    void update(int progress);

    boolean isCancelled();

}
