package com.me.keys;

/**
 * Created by Babbaj on 12/1/2017.
 */
import com.me.mods.util.BaseMod;
import com.me.mods.util.ModManager;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        ModManager.getInstance().forEachUnsynchronized(mod -> mod.keyPressed(e));
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        ModManager.getInstance().forEachUnsynchronized(mod -> mod.keyReleased(e));
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        ModManager.getInstance().forEachUnsynchronized(mod -> mod.keyPressed(e));
    }

}
