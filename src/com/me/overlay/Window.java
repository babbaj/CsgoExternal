package com.me.overlay;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;

/**
 * @author Brady
 * @since 6/30/2017 12:29 PM
 */
public final class Window {

    private static final User32 user32 = User32.INSTANCE;

    private final WinDef.HWND window;
    private final WinDef.RECT rect;
    private final Rectangle bounds;

    public Window(WinDef.HWND window) {
        this.window = window;
        this.rect = new WinDef.RECT();
        this.bounds = new Rectangle();
    }

    public final Rectangle getBounds() {
        if (!user32.GetWindowRect(window, rect))
            return null;

        this.bounds.width = rect.right - rect.left;
        this.bounds.height = rect.bottom - rect.top;

        if (!user32.GetWindowRect(window, rect))
            return null;

        this.bounds.x = rect.left + (((rect.right - rect.left) - this.bounds.width) / 2);
        this.bounds.y = rect.top + ((rect.bottom - rect.top) - this.bounds.height);

        // absolutely retarded fix for the overlay not being transparent
        this.bounds.width += 1;
        this.bounds.height += 1;

        return this.bounds;
    }

    public final void setBounds(Rectangle bounds) {
        this.bounds.x = bounds.x;
        this.bounds.y = bounds.y;
        this.bounds.width = bounds.width;
        this.bounds.height = bounds.height;
        user32.MoveWindow(window, bounds.x, bounds.y, bounds.width, bounds.height, false);
    }

    public boolean isVisible() {
        return window.equals(user32.GetForegroundWindow());
    }

    public final WinDef.HWND getHWnd() {
        return this.window;
    }

    public static Window get(String lpWindowName) {
        WinDef.HWND hwnd = user32.FindWindow(null, lpWindowName);
        if (hwnd == null)
            return null;

        return new Window(hwnd);
    }
}