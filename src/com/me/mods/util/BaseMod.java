package com.me.mods.util;

import com.me.event.OverlayEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;

import java.awt.*;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class BaseMod {

    private String name;
    private String description;

    private boolean enabled;

    public BaseMod(String name, String description) {
        this.name = name;
        this.description = description;
        this.onLoad();
    }

    // TODO: Events

    public void onLoad() {

    }

    public void tick() {
    }

    public void render(OverlayEvent event) {
    }

    public void keyPressed(NativeKeyEvent e) {
    }

    public void keyReleased(NativeKeyEvent e) {
    }

    public void mousePressed(NativeMouseEvent e) {
    }

    public void mouseClicked(NativeMouseEvent e) {
    }

    public void enable() {
        this.enabled = true;
    }
    public void disable() {
        this.enabled = false;
    }
    public void toggle() {
        this.enabled = !this.enabled;
    }

    public String getName() {
        return this.name;
    }
}
