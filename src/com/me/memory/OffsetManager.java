package com.me.memory;

import java.util.*;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class OffsetManager {

    private static final Map<String, Offset> offsetMap = new HashMap<>();

    private static final Map<String, Integer> netVarMap = new HashMap<>();

    public static void addOffset(Offset offset){
        offsetMap.put(offset.getName(), offset);
    }

    public static void addOffset(String name, Offset offset){
        offsetMap.put(name, offset);
    }

    /**
     *
     * @param name name of the offset
     * @return the offset's value
     * @see OffsetManager#getOffset(String)
     */
    public static int getOffsetVal(String name) {
        return getOffset(name).getOffset();
    }

    /**
     * Get an offset object
     *
     * @param name name of the offset
     * @return the offset object
     * @throws NullPointerException if no offset is found
     */
    public static Offset getOffset(String name) {
        return Objects.requireNonNull(offsetMap.get(name), "Unknown offset: " + name);
    }

    public static int getNetVar(String name) {
        Integer val = netVarMap.get(name);
        if (val < 0) throw new IllegalStateException("Attempted to use invalid netvar " + name);
        return Objects.requireNonNull(val, "Unknown netvar: " + name);
    }

    public static void addNetVar(String name, int value) {
        netVarMap.put(name, value);
    }

}
