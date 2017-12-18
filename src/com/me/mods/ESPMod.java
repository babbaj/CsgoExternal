package com.me.mods;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.Main;
import com.me.event.OverlayEvent;
import com.me.game.*;
import com.me.mods.util.BaseMod;
import com.me.utils.RenderUtils;
import com.me.utils.Utils;
import com.me.utils.Vec2f;
import com.me.utils.Vec3f;
import org.lwjgl.opengl.GL11;


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

        EntityManager.getInstance().forEachUnsynchronized(entity -> drawWaifu(entity, event));


    }

    public void drawWaifu(Entity entity, OverlayEvent event) {
        float[][] viewMatrix = ViewMatrix.getInstance().getViewMatrix();
        Vec3f bottom = entity.getPos(); // base position
        Vec3f offset = entity.getViewOffsets(); // height
        Vec3f top = bottom.add(offset);

        Utils.ScreenPos topPos = Utils.toScreen(top, viewMatrix);
        Utils.ScreenPos bottompos = Utils.toScreen(bottom, viewMatrix);
        if (!topPos.isVisible || !bottompos.isVisible) return;

        float height = Math.abs(topPos.vec.y - bottompos.vec.y);
        float width = height *1f;

        // stretch
        topPos.vec.y -= height *.2f;
        height += height *.3f;

        RenderUtils.drawTexture(event, waifu, topPos.vec.x - width/1.8f, topPos.vec.y, width, height);
    }


}
