package com.me.memory;

import java.util.*;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class OffsetManager {

    private static List<Offset> offsets = new ArrayList<>();

    private static Map<String, Integer> structOffsetMap = new HashMap<>();

    public static void addOffset(Offset offset){
        offsets.add(offset);
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
        return offsets.stream()
                      .filter(off -> off.getName().equals(name))
                      .findFirst()
                      .orElseThrow(() -> new NullPointerException("Unknwown offset: " + name));

    }

    public static int getStructOffset(String name) {
        return Objects.requireNonNull(structOffsetMap.get(name), "Unknown offset: " + name);
    }

    public static void addStructOffset(String name, int value) {
        structOffsetMap.put(name, value);
    }

}
