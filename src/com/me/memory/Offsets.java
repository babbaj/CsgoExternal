package com.me.memory;


/**
 * Created by Babbaj on 3/19/2018.
 */
// interface for offset/netvar keys
public interface Offsets {
    // to be initialized with reflection

    // All fields must me initialized to null so the compiler chooses to get the value at runtime.

    interface Netvars {
        Integer m_iHealth = null;
        Integer m_iTeamNum = null;
        Integer m_iGlowIndex = null;
        Integer m_bDormant = null;
        Integer m_fFlags = null;
        Integer m_clrRender = null;
        Integer m_vecOrigin = null;
        Integer m_vecVelocity = null;
        Integer m_dwViewAngles = null;
        Integer m_vecViewOffset = null;
        Integer m_dwBoneMatrix = null;
        Integer m_iShotsFired = null;
        Integer m_vecPunch = null;
        Integer m_flFlashMaxAlpha = null;
        Integer m_iCrosshairId = null;
    }

    //
    // OFFSETS
    //
    Offset m_dwLocalPlayer = null;
    Offset m_dwEntityList = null;
    Offset m_dwGlowObjectManager = null;
    Offset dwClientState = null;
    Offset dwClientState_ViewAngles = null;
    Offset dwbSendPackets = null;
    Offset dwRadarBase = null;
    Offset dwViewMatrix = null;
    Offset dwForceJump = null;
    Offset dwForceAttack = null;
}
