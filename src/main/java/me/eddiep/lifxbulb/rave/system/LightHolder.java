package me.eddiep.lifxbulb.rave.system;

import com.github.besherman.lifx.LFXHSBKColor;
import com.github.besherman.lifx.LFXLight;

import java.awt.*;

public class LightHolder {
    private LFXLight light;
    private Color originalColor;
    private int r, g, b;
    private int kelvin;

    public LightHolder(LFXLight light) {
        this.light = light;

        LFXHSBKColor color = light.getColor();
        this.originalColor = Color.getHSBColor(color.getHue(), color.getSaturation(), color.getBrightness());

        this.r = originalColor.getRed();
        this.g = originalColor.getGreen();
        this.b = originalColor.getBlue();
        this.kelvin = color.getKelvin();
    }

    public void raveWithValue(float num) {
        int newR = Math.min((int)(r * num), 255), newG = Math.min((int)(g * num), 255), newB = Math.min((int)(b * num), 255);

        Color color = new Color(newR, newG, newB);
        light.setColor(new LFXHSBKColor(color, kelvin));
    }
}
