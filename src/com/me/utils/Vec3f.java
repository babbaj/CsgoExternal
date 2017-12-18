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
        return new Vec3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vec3f copy() {
        return new Vec3f(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return String.format("X: %.2f, Y: %.2f, Z:%.2f", this.x, this.y, this.z);
    }
}
