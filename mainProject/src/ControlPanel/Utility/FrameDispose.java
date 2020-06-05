package ControlPanel.Utility;

import java.awt.*;

import static java.awt.Frame.getFrames;

public class FrameDispose {
    /**
     * This function takes all the frames that are currently displayed and disposes of them.
     */
    public static void disposeFrames(){
        Frame[] frames = getFrames();
        for (int i = 0; i < frames.length; i++) {
            frames[i].dispose();

        }
    }
}

