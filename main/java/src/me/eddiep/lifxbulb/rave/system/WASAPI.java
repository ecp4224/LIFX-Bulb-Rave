package me.eddiep.lifxbulb.rave.system;

public class WASAPI {
    public native boolean loadWrapper();

    public native float[] getSoundData();

    static {
        System.loadLibrary("wasapi");
    }
}
