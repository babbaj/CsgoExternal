package com.me.overlay.transparency;

import com.me.overlay.transparency.win10.Win10TransparencyApplier;
import com.me.overlay.transparency.win7.Win7TransparencyApplier;

/**
 * @author Brady
 * @since 6/29/2017 7:57 PM
 */
public final class TransparencyApplication {

    private static final TransparencyApplier systemDefault;

    private TransparencyApplication() {}

    static {
        if (System.getProperty("os.name").toLowerCase().contains("windows 10"))
            systemDefault = new Win10TransparencyApplier();
        else
            systemDefault = new Win7TransparencyApplier();
    }

    public static TransparencyApplier getSystemDefault() {
        return systemDefault;
    }
}
