package com.me.overlay.transparency.win10;

import com.sun.jna.Function;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.me.overlay.transparency.TransparencyApplier;

/**
 * @author Brady
 * @since 6/29/2017 7:53 PM
 */
public final class Win10TransparencyApplier implements TransparencyApplier {

    @Override
    public void accept(WinDef.HWND hwnd) {
        NativeLibrary user32 = NativeLibrary.getInstance("user32");

        AccentPolicy accent = AccentPolicy.TRANSPARENT;
        int accentStructSize = accent.size();
        accent.write();
        Pointer accentPtr = accent.getPointer();

        WindowCompositionAttributeData data = new WindowCompositionAttributeData();
        data.Attribute = WindowCompositionAttribute.WCA_ACCENT_POLICY;
        data.SizeOfData = accentStructSize;
        data.Data = accentPtr;

        Function setWindowCompositionAttribute = user32.getFunction("SetWindowCompositionAttribute");
        setWindowCompositionAttribute.invoke(WinNT.HRESULT.class, new Object[] { hwnd, data });
    }
}
