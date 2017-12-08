package com.me.mods.util;

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

    public BaseMod(String nameIn, String descriptionIn) {
        this.name = nameIn;
        this.description = descriptionIn;
    }

    public void tick() {
    }

    public void draw2d(Graphics g) {
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
