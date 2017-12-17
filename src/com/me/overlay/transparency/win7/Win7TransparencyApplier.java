package com.me.overlay.transparency.win7;

import com.sun.jna.platform.win32.WinDef;
import com.me.overlay.transparency.TransparencyApplier;

/**
 * @author Brady
 * @since 6/29/2017 7:28 PM
 */
public final class Win7TransparencyApplier implements TransparencyApplier {

    private static final WinDef.DWORD DWM_BB_ENABLE = new WinDef.DWORD(0x1);
    private static final DWM_BLURBEHIND pBlurBehind;

    static {
        pBlurBehind = new DWM_BLURBEHIND();
        pBlurBehind.dwFlags = DWM_BB_ENABLE;
        pBlurBehind.fEnable = true;
        pBlurBehind.hRgnBlur = null;
    }

    @Override
    public void accept(WinDef.HWND hwnd) {
        DWM.DwmEnableBlurBehindWindow(hwnd, pBlurBehind);
    }
}
