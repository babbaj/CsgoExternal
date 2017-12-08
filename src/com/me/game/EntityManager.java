package com.me.game;

import com.me.Main;
import com.me.memory.Offset;
import com.me.memory.OffsetManager;
import com.me.memory.Pointer;

import static com.me.memory.OffsetManager.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class EntityManager {

    private static EntityManager INSTANCE = new EntityManager();

    private List<Entity> entityList = new ArrayList<>();

    private EntityManager() {
    }

    public static EntityManager getInstance() {
        return INSTANCE;
    }

    // if the player count changes then update everything
    public void updateEntityList() {
        LocalPlayer localPlayer = getLocalPlayer();
        // do we exist?
        if (localPlayer != null) {
            int playerCount = getPlayerCount();
            //if (playerCount > 250)
                //throw new IllegalStateException(String.format("Player count too high! (%d), bad offset?", playerCount)); // if we find an impossible player count
            for (int i = 0; i < 64/*playerCount*/; i++) {
                long entityBase = OffsetManager.getOffset("m_dwEntityList").readUnsignedInt(0) + i * 16;
                Entity entity = new Entity(new Pointer(entityBase));
                if (entity.equals(localPlayer) && !containsEntity(localPlayer)) {
                    this.entityList.add(localPlayer);
                    System.out.printf("Added LocalPlayer %d, Index: %d, Size: %d\n", localPlayer.getPointer().getAddress(), i, this.entityList.size());
                } else if(!entity.getPointer().isNull() && !containsEntity(entityBase) && entity.isValidEntity()) {
                    this.entityList.add(entity);
                    System.out.printf("Added entity %d, Index: %d, Size: %d\n", entity.getPointer().getAddress(), i, this.entityList.size());
                }

            }
        }

        removeBadEntities();
    }

    private void removeBadEntities() {
        entityList.removeIf(ent -> !ent.isValidEntity());
    }

    public boolean containsEntity(long base) {
        return entityList.stream()
                         .anyMatch(ent -> ent.getPointer().getAddress() == base);
    }

    public boolean containsEntity(Entity entity) {
        return entityList.stream()
                         .anyMatch(ent -> ent.equals(entity));
    }

    // get the number of players in the world
    private int getPlayerCount() {
        return (int)getOffset("m_dwGlowObject").readUnsignedInt(0x4);
    }

    public Entity getEntity(int index) {
        return entityList.get(index);
    }

    // get our local player entity
    public LocalPlayer getLocalPlayer() {
        return (LocalPlayer)entityList.stream()
                        .filter(ent -> ent instanceof LocalPlayer)
                        .findFirst()
                        .orElseGet(() -> {
                        long p = Main.getMemory().getClient().address() + getOffset("m_dwLocalPlayer").getOffset();
                        LocalPlayer player = new LocalPlayer(new Pointer(p));
                        if (player.getPointer().isNull()) return null;
                        return player;
                        });
    }



}
