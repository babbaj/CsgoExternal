package com.me.mods;

import com.me.Main;
import com.me.event.OverlayEvent;
import com.me.game.*;
import com.me.mods.util.BaseMod;
import com.me.mods.util.ModManager;
import com.me.utils.Utils;
import com.me.utils.Vec2f;
import com.me.utils.Vec3f;


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
    public void render(OverlayEvent event) {


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
