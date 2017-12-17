package com.me.overlay.transparency.win10;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

import static com.me.overlay.transparency.win10.AccentState.*;


/**
 * @author Brady
 * @since 6/29/2017 7:33 PM
 */
public final class AccentPolicy extends Structure implements Structure.ByReference {

    public static final AccentPolicy BLUR, TRANSPARENT;

    static {
        BLUR = new AccentPolicy(ACCENT_ENABLE_BLURBEHIND, 0, 0, 0);
        TRANSPARENT = new AccentPolicy(ACCENT_ENABLE_TRANSPARENTGRADIENT, 2, 0, 0);
    }

    public int AccentState;
    public int AccentFlags;
    public int GradientColor;
    public int AnimationId;

    public AccentPolicy(int AccentState, int AccentFlags, int GradientColor, int AnimationId) {
        this.AccentState = AccentState;
        this.AccentFlags = AccentFlags;
        this.GradientColor = GradientColor;
        this.AnimationId = AnimationId;
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("AccentState", "AccentFlags", "GradientColor", "AnimationId");
    }
}
