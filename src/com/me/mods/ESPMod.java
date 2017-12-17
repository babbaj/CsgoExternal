package com.me.mods;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.Main;
import com.me.event.OverlayEvent;
import com.me.game.*;
import com.me.mods.util.BaseMod;
import com.me.mods.util.ModManager;
import com.me.utils.RenderUtils;
import com.me.utils.Utils;
import com.me.utils.Vec2f;
import com.me.utils.Vec3f;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.graphics.GL20.*;


/**
 * Created by Babbaj on 12/1/2017.
 */
public class ESPMod extends BaseMod {

    public ESPMod() {
        super("ESPMod", "see through walls and shit");
    }

    private Texture waifuTex;
    private Sprite waifu;


    @Override
    public void render(OverlayEvent event) {
        if (waifuTex == null) {
            waifuTex = new Texture(Main.waifuFile.getPath());
            waifu = new Sprite(waifuTex);
            waifu.flip(false ,true);
        }

        EntityManager.getInstance().forEach(entity -> drawWaifu(entity, event));


    }

    public void drawWaifu(Entity entity, OverlayEvent event) {
        float[][] viewMatrix = ViewMatrix.getInstance().getViewMatrix();
        Vec3f bottom = entity.getPos(); // base position
        Vec3f offset = entity.getViewOffsets(); // height
        Vec3f top = bottom.copy().add(offset);

        Vec2f topPos = Utils.toScreen(top, viewMatrix);
        Vec2f bottompos = Utils.toScreen(bottom, viewMatrix);

        float height = Math.abs(topPos.y - bottompos.y);
        float width = height *1f;

        // stretch
        topPos.y -= height *.2f;
        height += height *.3f;

        RenderUtils.drawTexture(event, waifu, topPos.x - width/1.8f, topPos.y, width, height);
        try {
            Thread.sleep(1);
        } catch (Exception e) {

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
