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
        return (int)getPointer().readUnsignedInt(getNetVar(m_iShotsFired));
    }

    public Vec2f getPunch() {
        float x = getPointer().readFloat(getNetVar(m_vecPunch));
        float y = getPointer().readFloat(getNetVar(m_vecPunch) + 0x4);
        return new Vec2f(x, y);
    }

    public float getFlashAlpha() {
        return getPointer().readFloat(getNetVar(m_flFlashMaxAlpha));
    }

    public int getCrosshairId() {
        return getPointer().readInt(getNetVar(m_iCrosshairId));
    }

    public void writePunch(float x, float y) {
        getPointer().writeFloat(x, getNetVar(m_vecPunch));
        getPointer().writeFloat(y, getNetVar(m_vecPunch) + 0x4);
    }

    public void writeFlashAlpha(float alpha) {
        getPointer().writeFloat(alpha, getNetVar(m_flFlashMaxAlpha));
    }

    @Deprecated // not implemented
    public void writeAttack(int mode) {
        getPointer().writeInt(mode, getNetVar("m_dwForceAttack"));
    }

    public void writeJump(int mode) {
        getOffset(dwForceJump).writeInt(mode, 0);
    }

    public void writeViewAngles(Vec2f vec) {
        Pointer clientState = getOffset(dwClientState).getPointer(0);
        int viewAngleOffset = getOffsetVal(dwClientState_ViewAngles);
        clientState.writeFloat(vec.x, viewAngleOffset);
        clientState.writeFloat(vec.y, viewAngleOffset + 0x4);
    }

    public void writeRoll(float roll) {
        Pointer angles = getOffset(dwClientState).getPointer(0);
        angles.writeFloat(roll, getOffsetVal(dwClientState_ViewAngles) + 0x8);
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
