package com.me.utils;

/**
 * Created by Babbaj on 12/5/2017.
 */
public class MathHelper {

    public static float clamp(float val, float min, float max) {
        if (val < min) return min;
        if (val > max) return max;
        return val;
    }

    public static float normalizeAngle(float a) {
        while (a > 180f) a -= 360f;
        while (a < -180f) a += 360f;
        return a;
    }
}
