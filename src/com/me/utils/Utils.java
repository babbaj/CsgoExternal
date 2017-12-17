package com.me.utils;

import com.me.Main;

/**
 * Created by Babbaj on 12/15/2017.
 */
public class Utils {

    public static Vec2f toScreen(Vec3f vec, float[][] viewMatrix) {
        float x = viewMatrix[0][0] * vec.x + viewMatrix[0][1] * vec.z + viewMatrix[0][2] * vec.y + viewMatrix[0][3];
        float y = viewMatrix[1][0] * vec.x + viewMatrix[1][1] * vec.z + viewMatrix[1][2] * vec.y + viewMatrix[1][3];

        double w = viewMatrix[3][0] * vec.x + viewMatrix[3][1] * vec.z + viewMatrix[3][2] * vec.y + viewMatrix[3][3];

        //if (Double.isNaN(w) || w < 0.01F) return null;

        double invw = 1.0 / w;

        x *= invw;
        y *= invw;

        double width = Main.getOverlay().getWidth();
        double height = Main.getOverlay().getHeight();

        double x2 = width / 2.0;
        double y2 = height / 2.0;

        x2 += 0.5 * x * width + 0.5;
        y2 -= 0.5 * y * height + 0.5;

        return new Vec2f((float)x2, (float)y2);
    }
}
