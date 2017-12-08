package com.me.keys;

import com.me.mods.util.ModManager;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;


public class GlobalMouseListener implements NativeMouseInputListener {

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        ModManager.getInstance().forEach(mod -> mod.mouseClicked(nativeEvent));

    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeEvent) {
        ModManager.getInstance().forEach(mod -> mod.mousePressed(nativeEvent));
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {

    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {

    }

}
