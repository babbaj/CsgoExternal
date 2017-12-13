package com.me.game;

import com.me.Main;
import com.me.memory.Pointer;
import com.me.utils.Vec2f;

import java.awt.*;

import static com.me.memory.OffsetManager.getOffset;
import static com.me.memory.OffsetManager.getOffsetVal;
import static com.me.memory.OffsetManager.getStructOffset;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class LocalPlayer extends Entity {

    public LocalPlayer(Pointer pointer) {
        super(pointer);
    }

    public int getShotsFired() {
        return (int)getPointer().readUnsignedInt(getStructOffset("m_iShotsFired"));
    }

    public Vec2f getPunch() {
        float x = getPointer().readFloat(getStructOffset("m_vecPunch"));
        float y = getPointer().readFloat(getStructOffset("m_vecPunch") + 0x4);
        return new Vec2f(x, y);
    }

    public float getFlashAlpha() {
        return getPointer().readFloat(getStructOffset("m_flFlashMaxAlpha"));
    }

    public int getCrosshairId() {
        return getPointer().readInt(getStructOffset("m_iCrosshairId"));
    }

    public void writePunch(float x, float y) {
        getPointer().writeFloat(x, getStructOffset("m_vecPunch"));
        getPointer().writeFloat(y, getStructOffset("m_vecPunch") + 0x4);
    }

    public void writeFlashAlpha(float alpha) {
        getPointer().writeFloat(alpha, getStructOffset("m_flFlashMaxAlpha"));
    }

    public void writeAttack(int mode) {
        getPointer().writeInt(mode, getStructOffset("m_dwForceAttack"));
    }

    public void writeJump(int mode) {
        Main.getMemory().getClient().writeInt(getStructOffset("m_dwForceJump"), mode);
    }

    // pitch, yaw?
    public void writeViewAngles(Vec2f vec) {
        Pointer clientState = getOffset("m_dwClientState").getPointer(0);
        clientState.writeFloat(vec.y, getStructOffset("m_dwViewAngles"));
        clientState.writeFloat(vec.x, getStructOffset("m_dwViewAngles") + 0x4);
    }

    public void writeRoll(float roll) {
        Pointer angles = new Pointer(getOffset("m_dwClientState").readUnsignedInt(0));
        angles.writeFloat(roll, 0x8);
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
