package com.me.utils;

/**
 * Created by Babbaj on 12/2/2017.
 */
public class Vec3f {

    public float x;
    public float y;
    public float z;

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void normalize() {
        this.x = MathHelper.normalizeAngle(this.x);
        this.y = MathHelper.clamp(y, -89f, 89f);
        this.z = MathHelper.normalizeAngle(this.z);
    }
}
