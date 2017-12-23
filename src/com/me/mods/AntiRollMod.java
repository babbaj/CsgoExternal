package com.me.mods;

import com.me.game.EntityManager;
import com.me.game.LocalPlayer;
import com.me.mods.util.BaseMod;

/**
 * Created by Babbaj on 12/21/2017.
 */
public class AntiRollMod extends BaseMod {

    public AntiRollMod() {
        super("AntiRoll", "roll angle always 0");
    }

    @Override
    public void tick() {
        LocalPlayer player = EntityManager.getInstance().getLocalPlayer();
        if (player == null) return;
        if (player.getViewAngles().z != 0f)
            player.writeRoll(0f);
    }
}
