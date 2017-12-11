package com.me.mods.util;

import com.me.game.EntityManager;

import static com.me.memory.OffsetManager.getOffset;

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
        int playerCount = getPlayerCount();
        if (playerCount != manager.getEntityList().size()) {
            manager.clearEntities();
            if (playerCount > 0)
                manager.updateEntityList();
            return;
        }

        // clear the entity list if we are not in the game
        if (manager.getLocalPlayer() == null && manager.getEntityList().size() > 0) {
            manager.clearEntities();
        }

    }

    private int getPlayerCount() {
        int count = 0;
        for (int i = 0; i < 64; i++) {
            long entBase = getOffset("m_dwEntityList").readUnsignedInt(i * 16);
            if (entBase == 0) break;
            count++;
        }
        return count;
    }
}
