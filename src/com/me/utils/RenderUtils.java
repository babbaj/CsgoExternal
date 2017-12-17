package com.me.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.me.event.OverlayEvent;

/**
 * Created by Babbaj on 12/17/2017.
 */
public class RenderUtils {
    private RenderUtils() {}

    public static void setScale(Batch batch, float scale) {
        batch.getTransformMatrix().setToScaling(scale, scale, 1);
    }



    public static void drawRect(OverlayEvent event, float x, float y, float width, float height) {

    }

    public static void drawTexture(OverlayEvent event, TextureRegion texture, float x, float y, float width, float height) {
        event.getBatch().begin();
        {
            event.getBatch().draw(texture, x, y, width, height);
        }
        event.getBatch().end();
    }

    public static void drawTexture(OverlayEvent event, TextureRegion texture, float x, float y, float scale) {
        event.getBatch().begin();
        {
            setScale(event.getBatch(), scale);
            event.getBatch().draw(texture, x * (1 / scale), y * (1 / scale));
        }
        event.getBatch().end();
    }

}
