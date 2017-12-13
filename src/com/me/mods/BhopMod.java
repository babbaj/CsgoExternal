package com.me.mods;

import com.me.game.EntityManager;
import com.me.game.LocalPlayer;
import com.me.mods.util.BaseMod;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Created by Babbaj on 12/13/2017.
 */
public class BhopMod extends BaseMod {

    public BhopMod() {
        super("Bhop", "sick hops");
    }

    private boolean state;

    @Override
    public void keyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) {
            this.state = true;
        }
    }
    @Override
    public void keyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) {
            this.state = false;
        }
    }

    @Override
    public void tick() {
        if (this.state) {
            LocalPlayer player = EntityManager.getInstance().getLocalPlayer();
            if ((player.getFlags() & 1) == 1)
                player.writeJump(6);

        }
    }
}
