package com.me.game;

import com.me.memory.Pointer;
import com.me.memory.OffsetManager;
import com.me.utils.Vec2f;
import com.me.utils.Vec3f;

import static com.me.memory.OffsetManager.*;


/**
 * Created by Babbaj on 12/1/2017.
 */
public class Entity {

    public static final int TEAM_SPEC = 1;
    public static final int TEAM_T = 2;
    public static final int TEAM_CT = 3;

    private Pointer pointer;

    public Entity(Pointer pointerIn) {
        this.pointer = pointerIn;
    }

    public Pointer getPointer() {
        return this.pointer;
    }

    public boolean equals(Entity other) {
        if (other == this) return true;
        return other.getPointer().getAddress() == this.pointer.getAddress();
    }

    public boolean isValidEntity() {
        return !getPointer().isNull() /*&& (getTeam() == TEAM_T || getTeam() == TEAM_CT) && health() > 0 && !getDormant()*/;
    }


    public int getHealth() {
        return pointer.readInt(getStructOffset("m_iHealth"));
    }

    public int getTeam() {
        return pointer.readInt(getStructOffset("m_iTeamNum"));
    }

    public boolean getDormant() {
        return pointer.readInt(getStructOffset("m_bDormant")) != 0;
    }

    public int getFlags() {
        return (int)pointer.readUnsignedInt(getStructOffset("m_fFlags"));
    }

    public int getColor() {
        return (int)pointer.readUnsignedInt(getStructOffset("m_clrRender"));
    }

    public Vec3f getPos() {
        float x = pointer.readFloat(getStructOffset("m_vecOrigin"));
        float z = pointer.readFloat(getStructOffset("m_vecOrigin") + 0x4);
        float y = pointer.readFloat(getStructOffset("m_vecOrigin") + 0x8);
        return new Vec3f(x, y, z);
    }

    public Vec3f getVelocity() {
        float x = pointer.readFloat(getStructOffset("m_vecVelocity"));
        float z = pointer.readFloat(getStructOffset("m_vecVelocity") + 0x4);
        float y = pointer.readFloat(getStructOffset("m_vecVelocity") + 0x8);
        return new Vec3f(x, y, z);
    }

    public float getPitch() {
        return pointer.readFloat(getStructOffset("m_dwViewAngles"));
    }

    public float getYaw() {
        return pointer.readFloat(getStructOffset("m_dwViewAngles") + 0x4);
    }

    public float getRoll() {
        return pointer.readFloat(getStructOffset("m_dwViewAngles") + 0x8);
    }

    public Vec3f getViewAngles() {
        float pitch = getPitch();
        float yaw = getYaw();
        float roll = getRoll();
        return new Vec3f(pitch, yaw, roll);
    }

    public Vec3f getViewOffsets() {
        float x = pointer.readFloat(getStructOffset("m_vecViewOffset"));
        float y = pointer.readFloat(getStructOffset("m_vecViewOffset") + 0x4);
        float z = pointer.readFloat(getStructOffset("m_vecViewOffset") + 0x8);
        return new Vec3f(x, y, z);
    }

    public Vec3f getBonePos(int bone) {
        Pointer boneMatrix = getBoneMatrix();
        float x = boneMatrix.readFloat(0x30 * bone + 0x0C);
        float y = boneMatrix.readFloat(0x30 * bone + 0x1C);
        float z = boneMatrix.readFloat(0x30 * bone + 0x2C);
        return new Vec3f(x, y, z);
    }

    public Pointer getBoneMatrix() {
        return new Pointer(pointer.readUnsignedInt(getStructOffset("m_dwBoneMatrix")));
    }

    public void writeColor(int color) {
        pointer.writeInt(color, getStructOffset("m_clrRender"));
    }

    public void writeSpotted(boolean b) {
        pointer.writeBoolean(b, getStructOffset("m_bSpotted"));
    }


}
