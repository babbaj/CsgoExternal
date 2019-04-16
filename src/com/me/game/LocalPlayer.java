package com.me.game;

import com.me.Main;
import com.me.memory.Pointer;
import com.me.utils.Vec2f;

import java.awt.*;

import static com.me.memory.Offsets.*;
import static com.me.memory.Offsets.Netvars.*;
import static com.me.memory.OffsetManager.*;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class LocalPlayer extends Entity {

    public LocalPlayer(Pointer pointer) {
        super(pointer);
    }

    @Override
    public boolean isValidEntity() {
        return true;
    }

    @Deprecated // not implemented
    public int getShotsFired() {
        return (int)getPointer().readUnsignedInt(m_iShotsFired);
    }

    public Vec2f getPunch() {
        float x = getPointer().readFloat(m_vecPunch);
        float y = getPointer().readFloat(m_vecPunch + 0x4);
        return new Vec2f(x, y);
    }

    public float getFlashAlpha() {
        return getPointer().readFloat(m_flFlashMaxAlpha);
    }

    public int getCrosshairId() {
        return getPointer().readInt(m_iCrosshairId);
    }

    public void writePunch(float x, float y) {
        getPointer().writeFloat(x, m_vecPunch);
        getPointer().writeFloat(y, m_vecPunch + 0x4);
    }

    public void writeFlashAlpha(float alpha) {
        getPointer().writeFloat(alpha, m_flFlashMaxAlpha);
    }

    @Deprecated // not implemented
    public void writeAttack(int mode) {
        getPointer().writeInt(mode, getNetVar("m_dwForceAttack"));
    }

    public void writeJump(int mode) {
        dwForceJump.writeInt(mode, 0);
    }

    public void writeViewAngles(Vec2f vec) {
        Pointer clientState = dwClientState.getPointer(0);
        int viewAngleOffset = dwClientState_ViewAngles.getOffset();
        clientState.writeFloat(vec.x, viewAngleOffset);
        clientState.writeFloat(vec.y, viewAngleOffset + 0x4);
    }

    public void writeRoll(float roll) {
        Pointer angles = dwClientState.getPointer(0);
        angles.writeFloat(roll, dwClientState_ViewAngles.getOffset() + 0x8);
    }

    public void pressMouse(int button) {
        Robot bot = Main.getRobot();
        bot.mousePress(button);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bot.mouseRelease(button);
    }

}
