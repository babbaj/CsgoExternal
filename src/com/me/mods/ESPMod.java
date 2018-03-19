package com.me.mods;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.Main;
import com.me.event.OverlayEvent;
import com.me.game.*;
import com.me.mods.util.BaseMod;
import com.me.utils.RenderUtils;
import com.me.utils.Utils;
import com.me.utils.Vec3f;


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
            waifuTex = new Texture(Main.waifu_file.getPath());
            waifu = new Sprite(waifuTex);
            waifu.flip(false ,true);
        }

        LocalPlayer player = EntityManager.getInstance().getLocalPlayer();
        if (player == null) return;

        EntityManager.getInstance().forEachUnsynchronized(entity -> {
            drawWaifu(entity, event, player);
        });
    }

    public void drawWaifu(Entity entity, OverlayEvent event, LocalPlayer player) {
        float[][] viewMatrix = ViewMatrix.getInstance().getViewMatrix();
        Vec3f bottom = entity.getPos(); // base position
        Vec3f offset = entity.getViewOffsets(); // height
        Vec3f top = bottom.add(offset);

        Utils.ScreenPos topPos = Utils.toScreen(top, viewMatrix, player);
        Utils.ScreenPos bottompos = Utils.toScreen(bottom, viewMatrix, player);
        if (!topPos.isVisible || !bottompos.isVisible) return;

        float height = Math.abs(topPos.vec.y - bottompos.vec.y);
        float width = height ;

        // stretch
        topPos.vec.y -= height *.2f;
        height *= 1.3f;

        RenderUtils.drawTexture(event, waifu, topPos.vec.x - width/1.6f, topPos.vec.y, width, height);
    }

}
