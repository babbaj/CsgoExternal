package com.me.overlay.transparency.win10;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brady
 * @since 6/29/2017 7:53 PM
 */
public final class WindowCompositionAttributeData extends Structure implements Structure.ByReference {

    public int Attribute;
    public Pointer Data;
    public int SizeOfData;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("Attribute", "Data", "SizeOfData");
    }
}
