package com.me.utils;

import com.me.Main;
import com.me.game.Entity;
import com.me.game.EntityManager;
import com.me.game.LocalPlayer;
import com.me.game.ViewMatrix;

/**
 * Created by Babbaj on 12/15/2017.
 */
public class Utils {

    public static ScreenPos toScreen(Vec3f vec, float[][] viewMatrix, LocalPlayer player) {
        float x = viewMatrix[0][0] * vec.x + viewMatrix[0][1] * vec.z + viewMatrix[0][2] * vec.y + viewMatrix[0][3];
        float y = viewMatrix[1][0] * vec.x + viewMatrix[1][1] * vec.z + viewMatrix[1][2] * vec.y + viewMatrix[1][3];

        double w = viewMatrix[3][0] * vec.x + viewMatrix[3][1] * vec.z + viewMatrix[3][2] * vec.y + viewMatrix[3][3];

        //if (Double.isNaN(w) || w < 0.01F) return null;

        double invw = 1.0 / w;

        x *= invw;
        y *= invw;

        double width = Main.getRes()[0];
        double height = Main.getRes()[1];

        double x2 = width / 2.0;
        double y2 = height / 2.0;

        x2 += 0.5 * x * width + 0.5;
        y2 -= 0.5 * y * height + 0.5;

        boolean visible = true;
        Vec2f diffAngle = new Vec2f(0, 0);
        Vec3f myPos = player.getPos().add(player.getViewOffsets());
        Vec3f playerAngles = player.getViewAngles();
        Vec2f angles = new Vec2f(playerAngles.y, playerAngles.x);
        calcAngles(myPos, vec, diffAngle);
        float distanceFromCrosshair = Math.abs(MathHelper.normalizeAngle(MathHelper.distanceBetweenPoints(diffAngle, angles)));
        if (distanceFromCrosshair > 90f) visible = false;


        Vec2f pos = new Vec2f((float)x2, (float)y2);
        return new ScreenPos(pos, visible);
    }

    // TODO: refactor this
    public static Entity closestToCrosshair(LocalPlayer localPlayer) {
        float[][] matrix = ViewMatrix.getInstance().getViewMatrix();
        float centerX = Main.getRes()[0]/2;
        float centerY = Main.getRes()[1]/2;
        Vec2f center = new Vec2f(centerX, centerY);
        Entity out = null;
        float shortest = Float.MAX_VALUE;
        for (Entity ent : EntityManager.getInstance().getEntityList()) {
            if (ent == localPlayer) continue;
            if (!ent.isValidEntity()) continue;
            if (ent.getTeam() == localPlayer.getTeam()) continue; // comment this out
            ScreenPos pos = Utils.toScreen(ent.getHeadPos(), matrix, localPlayer);
            if (pos == null || !pos.isVisible) continue;
            float dist = MathHelper.distanceBetweenPoints(center, pos.vec);

            if (dist < shortest) {
                out = ent;
                shortest = dist;
            }
        }
        return out;
    }


    // aimbot angles
    public static void calcAngles(Vec3f src, Vec3f dst, Vec2f angles) {
        double[] delta = { (src.x-dst.x), (src.z-dst.z), (src.y-dst.y) };
        double hyp = Math.sqrt(delta[0]*delta[0] + delta[1]*delta[1]);

        angles.x = (float) Math.toDegrees(Math.asin(delta[2]/hyp)); // pitch
        angles.y = (float) Math.toDegrees(Math.atan(delta[1]/delta[0])); // yaw
        if(delta[0] >= 0.0)
        {
            angles.y += 180.0f;
        }
    }

    public static class ScreenPos {
        public boolean isVisible;
        public Vec2f vec;

        public ScreenPos(Vec2f vec, boolean visible) {
            this.vec = vec;
            this.isVisible = visible;
        }
    }
}
