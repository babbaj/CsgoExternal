package com.me.game;

import com.beaudoin.jmm.process.NativeProcess;
import com.google.common.collect.ForwardingSet;
import com.me.Main;
import com.me.memory.Pointer;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

import static com.me.memory.OffsetManager.*;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class EntityManager {

    private static EntityManager INSTANCE = new EntityManager();

    private List<Entity> entityList = new CopyOnWriteArrayList<>();

    private EntityManager() {
    }

    public static EntityManager getInstance() {
        return INSTANCE;
    }

    // if the player count changes then update everything
    public void updateEntityList() {
        long localPlayerBase = getOffset("m_dwLocalPlayer").readUnsignedInt(0);
        LocalPlayer localPlayer = new LocalPlayer(new Pointer(localPlayerBase));
        if (localPlayer.getPointer().isNull()) localPlayer = null;
        if (localPlayer != null && !containsEntity(localPlayer))  {
            entityList.add(localPlayer);
            localPlayer.updateEntity();
            System.out.printf("Added LocalPlayer %s, Size: %d\n", Long.toHexString(localPlayer.getPointer().getAddress()).toUpperCase(), this.entityList.size());
        }


        // do we exist?
        if (localPlayer != null) {
            for (int i = 0; i < 64; i++) {
                long entityBase = getOffset("m_dwEntityList").readUnsignedInt(i * 16);
                if (entityBase == localPlayerBase) continue;
                if (!isEntityValid(entityBase)) continue;

                Entity entity = new Entity(new Pointer(entityBase));

                if(!entity.getPointer().isNull() && !containsEntity(entityBase)) {
                    entity.updateEntity();
                    entityList.add(entity);
                    System.out.printf("Added entity %s, Size: %d\n", Long.toHexString(entity.getPointer().getAddress()), this.entityList.size());
                }
            }
        }

        //entityList.removeIf(ent -> !ent.isValidEntity());
        entityList.removeIf(ent -> !isEntityValid(ent));
        entityList.forEach(Entity::updateEntity);
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
                        /*.orElseGet(() -> {
                        long p = getOffset("m_dwLocalPlayer").readUnsignedInt(0);
                        LocalPlayer player = new LocalPlayer(new Pointer(p));
                        if (player.getPointer().isNull()) return null;
                        return player;
                        });*/
                        .orElse(null);
    }

    public void forEach(Consumer<Entity> consumer) {
        entityList.stream()
                  .filter(ent -> !(ent instanceof LocalPlayer))
                  .filter(Entity::isValidEntity)
                  .forEach(consumer);
    }

    public void forEachUnsynchronized(Consumer<Entity> consumer) {
        Collections.unmodifiableList(entityList)
                   .stream()
                   .filter(ent -> !(ent instanceof LocalPlayer))
                   .filter(Entity::isValidEntity)
                   .forEach(consumer);

    }

    public Entity entityFromId(int id) {
        long entBase = getOffset("m_dwEntityList").readUnsignedInt((id - 1) * 16);
        return getEntityFromBase(entBase);
    }

    public static boolean isEntityValid(Entity entity) {
        if (entity instanceof LocalPlayer) return true;
        return isEntityValid(entity.getPointer().getAddress());
    }

    public static boolean isEntityValid(long base) {
        NativeProcess proc = Main.getMemory().getProc();
        if (base == 0) return false; // null check
        if (getOffset("m_dwLocalPlayer").readUnsignedInt(0) == base) return true; // check if local player
        if (proc.readBoolean(base + getStructOffset("m_bDormant"))) return false; // dormant check
        if (proc.readInt(base + getStructOffset("m_iHealth")) <= 0) return false; // health check
        int team = proc.readInt(base + getStructOffset("m_iTeamNum"));
        if (!(team == Entity.TEAM_T || team == Entity.TEAM_CT)) return false; // team check
        if (proc.readUnsignedInt(base + getStructOffset("m_dwBoneMatrix")) == 0) return false; // bone matrix check

        // all checks have passed
        return true;
    }


}
