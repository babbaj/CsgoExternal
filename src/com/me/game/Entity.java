package com.me.game;

import com.me.memory.Pointer;
import com.me.utils.Vec3f;

import static com.me.memory.OffsetManager.*;


/**
 * Created by Babbaj on 12/1/2017.
 */
public class Entity {

    public static final int TEAM_SPEC = 1;
    public static final int TEAM_T = 2;
    public static final int TEAM_CT = 3;

    private String name;
    private int health = 100;
    private int team = -1;
    private int flags;
    private int glowIndex;
    //private Vec3f[] bones;
    private Vec3f headPos;
    private Vec3f pos;
    private Vec3f viewAngles;
    private Vec3f viewOffsets;
    private Vec3f velocity;
    private boolean dormant;


    private Pointer pointer;


    public Entity(Pointer pointerIn) {
        this.pointer = pointerIn;
    }

    public Pointer getPointer() {
        return this.pointer;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Entity)) return false;
        return ((Entity)other).getPointer().getAddress() == this.pointer.getAddress();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.pointer.getAddress());
    }

    public boolean isValidEntity() {
        int team = getTeam();
        if (team == -1) team = readTeam(); // if this entity hasn't yet been updated the team number will be -1
        return !getPointer().isNull() && getHealth() > 0 && (team == TEAM_T || team == TEAM_CT) && !getDormant();
    }

    public void updateEntity() {
        this.headPos = readBonePos(Bones.HEAD.id()); // this should be done first?
        this.name = readName();
        this.health = readHealth();
        this.team = readTeam();
        this.flags = readFlags(); // https://github.com/ValveSoftware/source-sdk-2013/blob/master/mp/src/public/const.h#L147
        this.glowIndex = readGlowIndex();
        this.pos = readPos();
        this.viewAngles = readViewAngles();
        this.viewOffsets = readViewOffsets();
        this.velocity = readVelocity();
        this.dormant = readDormant();
    }


    public int readHealth() {
        return pointer.readInt(getStructOffset("m_iHealth"));
    }

    public int readTeam() {
        return pointer.readInt(getStructOffset("m_iTeamNum"));
    }

    public boolean readDormant() {
        return pointer.readBoolean(getStructOffset("m_bDormant"));
    }

    public int readFlags() {
        return (int)pointer.readUnsignedInt(getStructOffset("m_fFlags"));
    }

    public int readColor() {
        return (int)pointer.readUnsignedInt(getStructOffset("m_clrRender"));
    }

    public int readGlowIndex() {
        return pointer.readInt(getStructOffset("m_iGlowIndex"));
    }

    public Vec3f readPos() {
        float x = pointer.readFloat(getStructOffset("m_vecOrigin"));
        float z = pointer.readFloat(getStructOffset("m_vecOrigin") + 0x4);
        float y = pointer.readFloat(getStructOffset("m_vecOrigin") + 0x8);
        return new Vec3f(x, y, z);
    }

    public Vec3f readBonePos(int bone) {
        Pointer boneMatrix = readBoneMatrix();
        if (boneMatrix.isNull()) {
            System.err.println("null bone matrix");
            return new Vec3f(0, 0, 0); // i shouldnt have to do this
        }
        float x = boneMatrix.readFloat(0x30 * bone + 0x0C);
        float z = boneMatrix.readFloat(0x30 * bone + 0x1C);
        float y = boneMatrix.readFloat(0x30 * bone + 0x2C);
        return new Vec3f(x, y, z);
    }


    public Vec3f readVelocity() {
        float x = pointer.readFloat(getStructOffset("m_vecVelocity"));
        float z = pointer.readFloat(getStructOffset("m_vecVelocity") + 0x4);
        float y = pointer.readFloat(getStructOffset("m_vecVelocity") + 0x8);
        return new Vec3f(x, y, z);
    }

    public float readPitch() {
        return pointer.readFloat(getStructOffset("m_dwViewAngles"));
    }

    public float readYaw() {
        return pointer.readFloat(getStructOffset("m_dwViewAngles") + 0x4);
    }

    public float readRoll() {
        return pointer.readFloat(getStructOffset("m_dwViewAngles") + 0x8);
    }

    public Vec3f readViewAngles() {
        float yaw = readYaw();
        float pitch = readPitch();
        float roll = readRoll();
        return new Vec3f(yaw, pitch, roll);
    }

    public Vec3f readViewOffsets() {
        float x = pointer.readFloat(getStructOffset("m_vecViewOffset"));
        float z = pointer.readFloat(getStructOffset("m_vecViewOffset") + 0x4);
        float y = pointer.readFloat(getStructOffset("m_vecViewOffset") + 0x8);
        if (y == 0) y = this.isCrouching() ? 32f : 64.06f; // TODO: get correct crouch offset
        return new Vec3f(x, y, z);
    }

    public Pointer readBoneMatrix() {
        return new Pointer(pointer.readUnsignedInt(getStructOffset("m_dwBoneMatrix")));
    }

    public String readName() {
        Pointer radarBase = getOffset("dwRadarBase").getPointer(0);
        long radar = radarBase.readUnsignedInt(0x54);
        int id = EntityManager.getInstance().getEntityList().indexOf(this);
        Pointer namePointer = new Pointer(radar + ((0x1E0 * (id)) + 0x24));
        String name = namePointer.readString(0);
        return name;
    }

    public void writeColor(int color) {
        pointer.writeInt(color, getStructOffset("m_clrRender"));
    }

    public void writeSpotted(boolean b) {
        pointer.writeBoolean(b, getStructOffset("m_bSpotted"));
    }

    public void writeGlow(float r, float g, float b, float a) {
        Pointer glowObj = getOffset("m_dwGlowObject").getPointer(0);
        int glowIndex = readGlowIndex();
        glowObj.writeFloat(r, (glowIndex * 0x38) + 0x4);
        glowObj.writeFloat(g, (glowIndex * 0x38) + 0x8);
        glowObj.writeFloat(b, (glowIndex * 0x38) + 0xC);
        glowObj.writeFloat(a, (glowIndex * 0x38) + 0x10);

        glowObj.writeBoolean(true, (glowIndex * 0x38) + 0x24);
        glowObj.writeBoolean(false, (glowIndex * 0x38) + 0x25);
    }


    public String getName() {
        return this.name;
    }
    public int getHealth() {
        return this.health;
    }
    public int getTeam() {
        return this.team;
    }
    public int getFlags() {
        return this.flags;
    }
    public int getGlowIndex() {
        return this.glowIndex;
    }
    public Vec3f getHeadPos() {
        return this.headPos;
    }
    public Vec3f getPos() {
        return this.pos;
    }
    public Vec3f getViewAngles() {
        return this.viewAngles;
    }
    public Vec3f getVelocity() {
        return this.velocity;
    }
    public Vec3f getViewOffsets() {
        return this.viewOffsets;
    }
    public boolean getDormant() {
        return this.dormant;
    }


    public boolean isCrouching() {
        return (this.getFlags() & (1 << 1)) == (1 << 1);
    }
    public boolean isOnGround() {
        return (this.getFlags() & 1) == 1;
    }


}
