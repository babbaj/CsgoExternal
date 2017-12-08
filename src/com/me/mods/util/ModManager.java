package com.me.mods.util;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class ModManager {

    private static final ModManager INSTANCE = new ModManager();

    private final Map<String, BaseMod> mods = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static ModManager getInstance() {
        return INSTANCE;
    }

    public void registerMod(BaseMod mod) {
        mods.put(mod.getName(), mod);
    }

    public BaseMod getMod(String name) {
        return mods.get(name);
    }

    public void tickAllMods() {
        forEach(BaseMod::tick);
    }

    public void forEach(Consumer<BaseMod> consumer) {
        mods.values().forEach(consumer);
    }
}
