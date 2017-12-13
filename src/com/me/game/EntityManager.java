package com.me.game;

import com.me.memory.Pointer;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.me.memory.OffsetManager.*;

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
            for (int i = 0; i < 64; i++) {
                long entityBase = getOffset("m_dwEntityList").readUnsignedInt(i * 16);
                if (entityBase == 0) continue;

                Entity entity = new Entity(new Pointer(entityBase));
                if (!entity.isValidEntity()) continue;

                if (entity.equals(localPlayer) && !containsEntity(localPlayer)) {
                    this.entityList.add(localPlayer);
                    System.out.printf("Added LocalPlayer %s, Size: %d\n", Long.toHexString(localPlayer.getPointer().getAddress()), this.entityList.size());

                } else if(!entity.getPointer().isNull() && !containsEntity(entityBase)) {
                    this.entityList.add(entity);
                    System.out.printf("Added entity %s, Size: %d\n", Long.toHexString(entity.getPointer().getAddress()), this.entityList.size());
                }

            }
        }

    }

    // clear the entity list
    public void clearEntities() {
        entityList.clear();
    }

    public boolean containsEntity(Pointer p) {
        return containsEntity(p.getAddress());
    }

    public boolean containsEntity(long base) {
        return entityList.stream()
                         .anyMatch(ent -> ent.getPointer().getAddress() == base);
    }

    public boolean containsEntity(Entity entity) {
        return entityList.stream()
                         .anyMatch(ent -> ent.equals(entity));
    }


    public Entity getEntity(int index) {
        return entityList.get(index);
    }

    public Entity getEntityFromBase(long base) {
        return entityList.stream()
                         .filter(ent -> ent.getPointer().getAddress() == base)
                         .findFirst()
                         .orElse(null);
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    // get our local player entity
    @Nullable
    public LocalPlayer getLocalPlayer() {
        return (LocalPlayer)entityList.stream()
                        .filter(ent -> ent instanceof LocalPlayer)
                        .findFirst()
                        .orElseGet(() -> {
                        long p = getOffset("m_dwLocalPlayer").readUnsignedInt(0);
                        LocalPlayer player = new LocalPlayer(new Pointer(p));
                        if (player.getPointer().isNull()) return null;
                        return player;
                        });
    }

    public void forEach(Consumer<Entity> consumer) {
        entityList.stream()
                  .filter(ent -> !(ent instanceof LocalPlayer))
                  .filter(Entity::isValidEntity)
                  .forEach(consumer);
    }

    public Entity entityFromId(int id) {
        long entBase = getOffset("m_dwEntityList").readUnsignedInt((id-1) * 16);
        return getEntityFromBase(entBase);
    }


}
