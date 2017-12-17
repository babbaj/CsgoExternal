package com.me.overlay;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.event.OverlayEvent;
import com.me.mods.util.ModManager;
//import org.jex.core.JEX;
//import org.jex.core.natives.Window;

import java.awt.*;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.graphics.GL20.*;

/**
 * @author Brady
 * @since 6/30/2017 2:22 PM
 */
public final class OverlayRenderer extends ApplicationAdapter {

    private final OverlayEvent event;
    private final Overlay overlay;

    private OrthographicCamera camera;

    private ShapeRenderer renderer;
    private CustomBitmapFont textRenderer;
    private Batch batch;

    OverlayRenderer(Overlay overlay) {
        this.overlay = overlay;
        this.event = new OverlayEvent(batch);
    }

    @Override
    public final void create() {
        update();
    }

    @Override
    public final void render() {
        update();

        gl.glClearColor(0.0F, 0.0F, 0.0F, 0F);
        gl.glClear(GL_COLOR_BUFFER_BIT);

        if (overlay.getTarget().isVisible()) {
            gl.glEnable(GL_BLEND);
            gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            //JEX.EVENT_BUS.post(event.setBatch(batch).setRenderer(renderer).setTextRenderer(textRenderer));
            ModManager.getInstance().forEach(mod -> mod.render(event.setBatch(batch).setRenderer(renderer).setTextRenderer(textRenderer)));

            gl.glDisable(GL_BLEND);
        }
    }

    @Override
    public final void dispose() {
        batch.dispose();
    }

    private void update() {
        Rectangle bounds = overlay.getTarget().getBounds();
        if (bounds == null)
            return;

        Window overlay = this.overlay.getWindow();
        if (overlay != null)
            overlay.setBounds(bounds);

        if (camera == null)
            camera = new OrthographicCamera((float) bounds.getWidth(), (float) bounds.getHeight());
        camera.setToOrtho(true, (float) bounds.getWidth(), (float) bounds.getHeight());

        if (batch == null)
            batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        if (renderer == null)
            renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(camera.combined);
        renderer.setAutoShapeType(true);

        // FIXME
        if (textRenderer == null)
            textRenderer = new CustomBitmapFont(Gdx.files.classpath("font.fnt"), Gdx.files.classpath("font.png"), true);
    }
}
