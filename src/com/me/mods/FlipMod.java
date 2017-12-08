package com.me.mods;

import com.me.Main;
import com.me.mods.util.BaseMod;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;

/**
 * Created by Babbaj on 12/2/2017.
 */
public class FlipMod extends BaseMod {

    public FlipMod() { super("FlipMod", "do sick 180s"); }

    @Override
    public void keyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_L) {
            long yawAddress = Main.getMemory().getEngine().address() + 0x47F1B8;
            float yaw = Main.getMemory().getProc().readFloat(yawAddress);
            Main.getMemory().getProc().writeFloat(yawAddress, yaw + 180f);
        }
    }

    @Override
    public void mouseClicked(NativeMouseEvent e) {
        if (e.getButton() == NativeMouseEvent.BUTTON3) {
            long yawAddress = Main.getMemory().getEngine().address() + 0x47F1B8;
            float yaw = Main.getMemory().getProc().readFloat(yawAddress);
            Main.getMemory().getProc().writeFloat(yawAddress, yaw + 180f);
        }
    }
}
