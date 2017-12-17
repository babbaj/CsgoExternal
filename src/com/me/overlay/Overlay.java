package com.me.overlay;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.me.overlay.transparency.TransparencyApplication;
//import org.jex.core.util.Flow;

import java.awt.*;
import java.util.Random;

/**
 * @author Brady
 * @since 6/29/2017 7:56 PM
 */
public final class Overlay {

    private final Window target;
    private OverlayRenderer renderer;
    private Window window;
    private LwjglApplication application;

    public Overlay(Window target) {
        this.target = target;
    }

    public final boolean display() {
        if (renderer != null)
            return true;

        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        Rectangle bounds = target.getBounds();
        while (bounds == null)
            return false;

        config.width = bounds.width;
        config.height = bounds.height;
        config.title = String.valueOf(new Random().nextLong());
        config.x = bounds.x;
        config.y = bounds.y;

        config.resizable = false;
        config.fullscreen = false;
        config.vSyncEnabled = false;

        config.foregroundFPS = 144;
        config.backgroundFPS = 144;

        application = new LwjglApplication(renderer = new OverlayRenderer(this), config);

        while (window == null) {
            window = Window.get(config.title);
            //Flow.sleep(50);
        }

        if (!OverlaySetup.setup(this))
            return false;

        TransparencyApplication.getSystemDefault().accept(window.getHWnd());
        return true;
    }

    public final Window getTarget() {
        return this.target;
    }

    public final OverlayRenderer getRenderer() {
        return this.renderer;
    }

    public final Window getWindow() {
        return this.window;
    }

    public final void close() {
        renderer = null;
        this.application.exit();
    }
}
