package com.me.mods;

import com.me.Main;
import com.me.game.*;
import com.me.mods.util.BaseMod;
import com.me.mods.util.ModManager;
import com.me.utils.Utils;
import com.me.utils.Vec2f;
import com.me.utils.Vec3f;

import java.awt.*;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class ESPMod extends BaseMod {

    public ESPMod() {
        super("ESPMod", "see through walls and shit");
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw2d(Graphics g) {
        float[][] viewMatrix = ViewMatrix.getInstance().getViewMatrix();
        Entity ent = closestToCrosshair();
        if (ent == null) return;
        Vec3f head = ent.getBonePos(Bones.HEAD.id());
        Vec2f screen = Utils.toScreen(head, viewMatrix);
        try {
            g.fillRect((int) screen.x, (int) screen.y, 10, 10);
        } catch (NullPointerException e) {
        }


    }


    private Entity closestToCrosshair() {
        LocalPlayer localPlayer = EntityManager.getInstance().getLocalPlayer();
        float[][] matrix = ViewMatrix.getInstance().getViewMatrix();
        float centerX = 1366/2;
        float centerY = 768/2;
        Vec2f center = new Vec2f(centerX, centerY);
        Entity out = null;
        float shortest = Float.MAX_VALUE;
        for (Entity ent : EntityManager.getInstance().getEntityList()) {
            if (ent == localPlayer) continue;
            Vec2f vec = Utils.toScreen(ent.getBonePos(Bones.HEAD.id()), matrix);
            float dist = distanceBetweenAngles(center, vec);

            if (dist < shortest) {
                out = ent;
                shortest = dist;
            }
        }
        return out;
    }

    public float distanceBetweenAngles(Vec2f a, Vec2f b) {
        float diffX = b.x - a.x;
        float diffY = b.y - a.y;
        return (float)Math.sqrt(diffX*diffX + diffY*diffY);
    }

}
