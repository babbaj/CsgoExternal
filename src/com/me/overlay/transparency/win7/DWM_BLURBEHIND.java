package com.me.overlay.transparency.win7;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brady
 * @since 6/29/2017 7:30 PM
 */
public final class DWM_BLURBEHIND extends Structure {

    public static final List<String> FIELDS = createFieldsOrder("dwFlags", "fEnable", "hRgnBlur", "fTransitionOnMaximized");

    public WinDef.DWORD dwFlags;
    public boolean fEnable;
    public WinDef.HRGN hRgnBlur;
    public boolean fTransitionOnMaximized;

    @Override
    protected List<String> getFieldOrder() {
        return FIELDS;
    }

}

