package com.me.mods.util;

import com.me.game.EntityManager;

/**
 * Created by Babbaj on 12/6/2017.
 */
public class EntityManagerService extends BaseMod {

    private int playerCount;

    public EntityManagerService() {
        super("EntityManagerService", "handle the entity manager");
    }

    public void tick() {
        EntityManager manager = EntityManager.getInstance();

        manager.updateEntityList();
    }
}
