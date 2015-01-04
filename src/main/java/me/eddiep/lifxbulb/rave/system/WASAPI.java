package me.eddiep.lifxbulb.rave.system;

import java.io.File;

public class WASAPI {
    public native boolean loadWrapper();

    public native float[] getSoundData();

    static {
        String fileName = "wasapi.dll";
        if (System.getProperty("os.arch").contains("64")) {
            fileName = "wasapi64.dll";
        }

        System.load(new File(fileName).getAbsolutePath());
    }
}
