package com.me.overlay;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;

import java.awt.*;

import static com.sun.jna.platform.win32.WinUser.*;

/**
 * @author Brady
 * @since 6/30/2017 4:39 PM
 */
final class OverlaySetup {

    private static final User32 user32 = User32.INSTANCE;
    private static final HWND HWND_TOPPOS = new HWND(new Pointer(-1));

    private static final int SWP_NOSIZE = 0x0001;
    private static final int SWP_NOMOVE = 0x0002;
    private static final int WS_EX_TOOLWINDOW = 0x00000080;
    private static final int WS_EX_APPWINDOW  = 0x00040000;

    private OverlaySetup() {}

    public static boolean setup(Overlay overlay) {
        HWND hwnd = overlay.getWindow().getHWnd();

        int wl = user32.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
        wl = wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT;
        user32.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl);

        wl &= ~(WS_VISIBLE);

        wl |= WS_EX_TOOLWINDOW;   // flags don't work - windows remains in taskbar
        wl &= ~(WS_EX_APPWINDOW);

        user32.ShowWindow(hwnd, SW_HIDE); // hide the window
        user32.SetWindowLong(hwnd, GWL_STYLE, wl); // set the style
        user32.ShowWindow(hwnd, SW_SHOW); // show the window for the new style to come into effect
        user32.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl);

        Rectangle bounds = overlay.getTarget().getBounds();
        if (bounds == null)
            return false;

        return user32.SetWindowPos(hwnd, HWND_TOPPOS, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), SWP_NOMOVE | SWP_NOSIZE);
    }
}
