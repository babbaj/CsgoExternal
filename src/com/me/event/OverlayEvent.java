package com.me.event;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.overlay.CustomBitmapFont;

/**
 * Created by Babbaj on 12/16/2017.
 */
public class OverlayEvent extends Event {

    private Batch batch;
    private ShapeRenderer renderer;
    private CustomBitmapFont fontRenderer;

    public OverlayEvent(Batch batchIn) {
        this.batch = batchIn;
    }

    public OverlayEvent setBatch(Batch batchIn) {
        this.batch = batchIn;
        return this;
    }
    public OverlayEvent setRenderer(ShapeRenderer rendererIn) {
        this.renderer = rendererIn;
        return this;
    }
    public OverlayEvent setTextRenderer(CustomBitmapFont fontRendererIn) {
        this.fontRenderer = fontRendererIn;
        return this;
    }

    public Batch getBatch() {
        return this.batch;
    }

    public ShapeRenderer getRenderer() {
        return this.renderer;
    }

    public CustomBitmapFont getFontRenderer() {
        return this.fontRenderer;
    }

}
