package com.me.mods;

import com.me.game.EntityManager;
import com.me.game.LocalPlayer;
import com.me.mods.util.BaseMod;

/**
 * Created by Babbaj on 12/5/2017.
 */
public class AntiFlashMod extends BaseMod {

    public AntiFlashMod() {
        super("AntiFlashMod", "no flashbangs");
    }

    @Override
    public void tick() {
        LocalPlayer player = EntityManager.getInstance().getLocalPlayer();
        if (player != null) {
            if (player.getFlashAlpha() > 0f)
                player.writeFlashAlpha(0f);
        }
    }
}
