package com.me.mods;

import com.me.Main;
import com.me.game.Entity;
import com.me.game.EntityManager;
import com.me.mods.util.BaseMod;

import java.util.Arrays;

import static com.me.memory.OffsetManager.getOffset;
import static com.me.memory.OffsetManager.getOffsetVal;
import static com.me.memory.OffsetManager.getStructOffset;

/**
 * Created by Babbaj on 12/6/2017.
 */
public class GlowMod extends BaseMod {

    public GlowMod() {
        super("GlowMod", "make players glow");
    }

    public static final float[] COLOR_T = {1f, 0, 0};
    public static final float[] COLOR_CT = {0, 0, 1f};

    public void tick() {
        for (int i = 0; i < 64; i++) {
            long currPlayer = Main.getMemory().getClient().readUnsignedInt(getOffsetVal("m_dwEntityList") + (i * 16));
            if (currPlayer == 0) continue;
            int currPlayerIndex = Main.getMemory().getProc().readInt(currPlayer + getStructOffset("m_iGlowIndex"));
            int teamNum = Main.getMemory().getProc().readInt(currPlayer + getStructOffset("m_iTeamNum"));
            float[] color = teamNum == Entity.TEAM_T ? COLOR_T : COLOR_CT;

            Main.getMemory().getProc().writeFloat(getOffset("m_dwGlowObject").readUnsignedInt(0) + ((currPlayerIndex * 0x38) + 0x4), color[0]);
            Main.getMemory().getProc().writeFloat(getOffset("m_dwGlowObject").readUnsignedInt(0) + ((currPlayerIndex * 0x38) + 0x8), color[1]);
            Main.getMemory().getProc().writeFloat(getOffset("m_dwGlowObject").readUnsignedInt(0) + ((currPlayerIndex * 0x38) + 0xC), color[2]);
            Main.getMemory().getProc().writeFloat(getOffset("m_dwGlowObject").readUnsignedInt(0) + ((currPlayerIndex * 0x38) + 0x10), 1f);

            Main.getMemory().getProc().writeBoolean(getOffset("m_dwGlowObject").readUnsignedInt(0) + ((currPlayerIndex * 0x38) + 0x24), true);
            Main.getMemory().getProc().writeBoolean(getOffset("m_dwGlowObject").readUnsignedInt(0) + ((currPlayerIndex * 0x38) + 0x25), false);
        }
    }
}
