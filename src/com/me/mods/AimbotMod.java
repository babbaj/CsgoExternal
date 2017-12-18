package com.me.mods;

import com.me.Main;
import com.me.event.OverlayEvent;
import com.me.game.*;
import com.me.mods.util.BaseMod;
import com.me.utils.*;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.*;



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
    private final float FOV = 80;


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
                Entity target = Utils.closestToCrosshair();
                if (target == null) return;
                Vec2f angles = new Vec2f(0, 0);
                Vec3f myPos = player.getPos().add(player.getViewOffsets());
                Vec3f targetPos = target.getHeadPos();
                calcAngles(myPos, targetPos, angles);

                // FIXME: broken as shit
                Vec2f diffAngle = new Vec2f(0, 0);
                Vec3f playerAngles = player.getViewAngles();
                Vec2f myAngles = new Vec2f(playerAngles.y, playerAngles.x);
                calcAngles(myPos, targetPos, diffAngle);
                float distanceFromCrosshair = MathHelper.normalizeAngle(Math.abs(Utils.distanceBetweenAngles(diffAngle, myAngles)));
                System.out.println(distanceFromCrosshair);

                //System.out.println(distanceFromCrosshair);
                if (angles.isValid() /*&& distanceFromCrosshair < FOV*/)
                    player.writeViewAngles(angles);

                this.state = false;
            }
            lastTimeChecked = System.currentTimeMillis();
        }
    }




    public float getDistanceBetweenAngles(final float ang1, final float ang2) {
        return Math.abs(((ang1 - ang2 + 180) % 360 + 360) % 360 - 180);
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
