package com.me.memory;


/**
 * Created by Babbaj on 3/19/2018.
 */
// interface for offset/netvar keys
public interface Offsets {
    // TODO: set all of these with reflection

    interface Netvars {
        String m_iHealth = "m_iHealth";
        String m_iTeamNum = "m_iTeamNum";
        String m_iGlowIndex = "m_iGlowIndex";
        String m_bDormant = "m_bDormant";
        String m_fFlags = "m_fFlags";
        String m_clrRender = "m_clrRender";
        String m_vecOrigin = "m_vecOrigin";
        String m_vecVelocity = "m_vecVelocity";
        String m_dwViewAngles = "m_dwViewAngles";
        String m_vecViewOffset = "m_vecViewOffset";
        String m_dwBoneMatrix = "m_dwBoneMatrix";
        String m_iShotsFired = "m_iShotsFired";
        String m_vecPunch = "m_vecPunch";
        String m_flFlashMaxAlpha = "m_flFlashMaxAlpha";
        String m_iCrosshairId = "m_iCrosshairId";
    }

    //
    // OFFSETS
    //
    String m_dwLocalPlayer = "m_dwLocalPlayer";
    String m_dwEntityList = "m_dwEntityList";
    String m_dwGlowObjectManager = "m_dwGlowObjectManager";
    String dwClientState = "dwClientState";
    String dwClientState_ViewAngles = "dwClientState_ViewAngles";
    String dwbSendPackets = "dwbSendPackets";
    String dwRadarBase = "dwRadarBase";
    String dwViewMatrix = "dwViewMatrix";
    String dwForceJump = "dwForceJump";
    String dwForceAttack = "dwForceAttack";


}
