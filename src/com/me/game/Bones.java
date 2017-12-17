package com.me.game;

/**
 * Created by Babbaj on 12/1/2017.
 */
public enum Bones {
    HEAD(8),
    NECK(7),
    CHEST(6),
    LOWER_CHEST(3),
    PELVIS(0);

    private final int id;

    Bones(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public static int getBoneId(Bones bone) {
        return bone.id();
    }
}
