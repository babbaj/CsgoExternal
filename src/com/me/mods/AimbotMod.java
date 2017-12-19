package com.me.mods;

import com.me.game.*;
import com.me.mods.util.BaseMod;
import com.me.utils.*;
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
    private boolean findNewTarget;
    private final int targetBone = Bones.HEAD.id();
    private final float FOV = 50;

    private Entity target;


    // TODO: keyPressed is spammed if a key is being held
    @Override
    public void keyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F) {
            this.state = true;
            this.findNewTarget = true;
        }
    }
    @Override
    public void keyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F) {
            this.state = false;
            this.findNewTarget = false;
        }
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() - lastTimeChecked >= delay) {
            LocalPlayer player = EntityManager.getInstance().getLocalPlayer();
            if (player == null) return;
            if (state) {
                if (findNewTarget) {
                    target = Utils.closestToCrosshair(player);
                    findNewTarget = false;
                }
                if (target == null || !target.isValidEntity()) {
                    return;
                }
                Vec2f angles = new Vec2f(0, 0);
                Vec3f myPos = player.getPos().add(player.getViewOffsets());
                Vec3f targetPos = target.getHeadPos();
                calcAngles(myPos, targetPos, angles);

                Vec2f currentAngles = new Vec2f(player.getViewAngles().y, player.getViewAngles().x);
                float diffYaw = MathHelper.differenceBetweenAngles(currentAngles.x, angles.x);
                float diffPitch = MathHelper.differenceBetweenAngles(currentAngles.y, angles.y);
                float distanceFromCrosshair = (float)Math.sqrt(diffYaw*diffYaw + diffPitch*diffPitch);

                if (angles.isValid() && distanceFromCrosshair < FOV)
                    player.writeViewAngles(angles);

                //this.state = false;
            }
            lastTimeChecked = System.currentTimeMillis();
        }
    }



    public void calcAngles(Vec3f src, Vec3f dst, Vec2f angles) {
        double[] delta = { (src.x-dst.x), (src.z-dst.z), (src.y-dst.y) };
        double hyp = Math.sqrt(delta[0]*delta[0] + delta[1]*delta[1]);

        angles.x = (float) Math.toDegrees(Math.asin(delta[2]/hyp)); // pitch
        angles.y = (float) Math.toDegrees(Math.atan(delta[1]/delta[0])); // yaw

        angles.x = MathHelper.normalizeAngle(angles.x); // make sure they're valid
        angles.y = MathHelper.normalizeAngle(angles.y);
        if(delta[0] >= 0.0)
        {
            angles.y += 180.0f;
        }
    }


}
