package com.me.overlay.transparency.win7;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

/**
 * @author Brady
 * @since 6/29/2017 7:30 PM
 */
public final class DWM {

    private DWM() {}

    static {
        Native.register("Dwmapi");
    }

    public static native WinNT.HRESULT DwmEnableBlurBehindWindow(WinDef.HWND hWnd, DWM_BLURBEHIND pBlurBehind);
}

