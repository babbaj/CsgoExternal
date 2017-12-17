package com.me.mods;

import com.me.game.Entity;
import com.me.game.EntityManager;
import com.me.game.LocalPlayer;
import com.me.mods.util.BaseMod;

import java.awt.event.InputEvent;

/**
 * Created by Babbaj on 12/11/2017.
 */
public class TriggerbotMod extends BaseMod {

    private long lastTimeChecked = System.currentTimeMillis();
    final long delay = 0;

    public TriggerbotMod() {
        super("Triggerbot", "autoshoot");
    }

    public void tick() {
        if (System.currentTimeMillis() - lastTimeChecked > delay) {
            LocalPlayer player = EntityManager.getInstance().getLocalPlayer();
            int crosshairId = player.getCrosshairId();
            if (crosshairId > 0 && crosshairId < 64) {
                Entity target = EntityManager.getInstance().entityFromId(crosshairId);
                if (target == null) return;
                if (target.getTeam() != player.getTeam()) {
                    player.pressMouse(InputEvent.BUTTON1_MASK);
                }
            }

            this.lastTimeChecked = System.currentTimeMillis();
        }
    }
}
