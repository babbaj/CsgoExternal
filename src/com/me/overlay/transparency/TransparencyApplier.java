package com.me.overlay.transparency;

import com.sun.jna.platform.win32.WinDef;

import java.util.function.Consumer;

/**
 * @author Brady
 * @since 6/29/2017 7:27 PM
 */
public interface TransparencyApplier extends Consumer<WinDef.HWND> {}
