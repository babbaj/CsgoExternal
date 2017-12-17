package com.me.mods;

import com.me.Main;
import com.me.game.*;
import com.me.mods.util.BaseMod;
import com.me.mods.util.ModManager;
import com.me.utils.*;
import com.sun.glass.events.ViewEvent;
import org.jnativehook.keyboard.NativeKeyEvent;


/**
 * Created by Babbaj on 12/11/2017.
 */
public class AimbotMod extends BaseMod {

    public AimbotMod() {
        super("Aimbot", "easy headshots");
    }

    private long lastTimeChecked = System.currentTimeMillis();
    private long delay = 5; // 5 ms
    private boolean state;
    private final int targetBone = Bones.HEAD.id();

    @Override
    public void keyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F) {
            this.state = true;
        }
    }
    @Override
    public void keyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F) {
            this.state = false;
        }
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() - lastTimeChecked >= delay) {
            LocalPlayer player = EntityManager.getInstance().getLocalPlayer();
            if (player == null) return;
            if (state) {
                Entity target = closestToCrosshair();
                if (target == null) return;
                Vec2f angles = new Vec2f(0, 0);
                Vec3f myPos = player.getPos().add(player.getViewOffsets());

                Vec3f targetPos = target.getBonePos(targetBone);

                calcAngles(myPos, targetPos, angles);
                if (angles.isValid())
                    player.writeViewAngles(angles);
                //this.state = false;
            }
            lastTimeChecked = System.currentTimeMillis();
        }
    }

    //TODO: this is shit and need to check visibility
    private Entity closestToCrosshair() {
        LocalPlayer localPlayer = EntityManager.getInstance().getLocalPlayer();
        float[][] matrix = ViewMatrix.getInstance().getViewMatrix();
        float centerX = 1366/2;
        float centerY = 768/2;
        Vec2f center = new Vec2f(centerX, centerY);
        Entity out = null;
        float shortest = Float.MAX_VALUE;
        for (Entity ent : EntityManager.getInstance().getEntityList()) {
            if (ent == localPlayer) continue;
            Vec2f vec = Utils.toScreen(ent.getBonePos(Bones.HEAD.id()), matrix);
            if (vec == null) return null;
            float dist = distanceBetweenAngles(center, vec);

            if (dist < shortest) {
                out = ent;
                shortest = dist;
            }
        }
        return out;
    }


    public float getDistanceBetweenAngles(final float ang1, final float ang2) {
        return Math.abs(((ang1 - ang2 + 180) % 360 + 360) % 360 - 180);
    }

    public float distanceBetweenAngles(Vec2f a, Vec2f b) {
        float diffX = b.x - a.x;
        float diffY = b.y - a.y;
        return (float)Math.sqrt(diffX*diffX + diffY*diffY);
    }


    public void calcAngles(Vec3f src, Vec3f dst, Vec2f angles) {
        double[] delta = { (src.x-dst.x), (src.z-dst.z), (src.y-dst.y) };
        double hyp = Math.sqrt(delta[0]*delta[0] + delta[1]*delta[1]);

        angles.x = (float) Math.toDegrees(Math.asin(delta[2]/hyp)); // pitch
        angles.y = (float) Math.toDegrees(Math.atan(delta[1]/delta[0])); // yaw
        if(delta[0] >= 0.0)
        {
            angles.y += 180.0f;
        }
    }


}
