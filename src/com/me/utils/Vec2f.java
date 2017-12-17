package com.me.utils;

/**
 * Created by Babbaj on 12/2/2017.
 */
public class Vec2f {

    public float x;
    public float y;

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isValid() {
        return !Double.isNaN(this.x) &&  !Double.isNaN(this.y);
    }
}
