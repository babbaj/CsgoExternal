package com.me.mods.util;

import com.me.Main;
import com.me.game.Entity;
import com.me.game.EntityManager;

import static com.me.memory.OffsetManager.getOffset;
import static com.me.memory.OffsetManager.getStructOffset;

/**
 * Created by Babbaj on 12/6/2017.
 */
public class EntityManagerService extends BaseMod {


    public EntityManagerService() {
        super("EntityManagerService", "handle the entity manager");
    }


    @Override
    public void tick() {
        EntityManager manager = EntityManager.getInstance();

        //int playerCount = getPlayerCount();
        //if (playerCount != manager.getEntityList().size()) {
        //    manager.clearEntities();
        //}
        manager.updateEntityList();
    }

    private int getPlayerCount() {
        int count = 0;
        for (int i = 0; i < 64; i++) {
            long entBase = getOffset("m_dwEntityList").readUnsignedInt(i * 16);
            if (!EntityManager.isEntityValid(entBase)) continue;
            count++;
        }
        return count;
    }
}
