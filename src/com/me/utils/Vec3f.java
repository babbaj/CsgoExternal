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

    public Vec3f normalize() {
        this.x = MathHelper.normalizeAngle(this.x);
        this.y = MathHelper.clamp(y, -89f, 89f);
        this.z = MathHelper.normalizeAngle(this.z);
        return this;
    }

    public boolean isValid() {
        return !Double.isNaN(this.x) &&  !Double.isNaN(this.y) && !Double.isNaN(this.z);
    }

    public Vec3f add(Vec3f other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;

        return this;
    }

    @Override
    public String toString() {
        return String.format("X: %.2f, Y: %.2f, Z:%.2f", this.z, this.y, this.z);
    }
}
