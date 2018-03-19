package com.me.mods;

import com.me.game.Entity;
import com.me.game.EntityManager;
import com.me.mods.util.BaseMod;


/**
 * Created by Babbaj on 12/6/2017.
 */
public class GlowMod extends BaseMod {

    public GlowMod() {
        super("GlowMod", "make players glow");
    }

    private static final float[] COLOR_T =  {1f, 0, 0};
    private static final float[] COLOR_CT = {0, 0, 1f};

    @Override
    public void tick() {
        EntityManager.getInstance()
                     .forEach(ent -> {
                         float[] color = ent.getTeam() == Entity.TEAM_T ? COLOR_T : COLOR_CT;
                         ent.writeGlow(color[0], color[1], color[2], 1f);
                     });
    }
}
