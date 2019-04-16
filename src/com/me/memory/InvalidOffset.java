package com.me.memory;

/**
 * Created by Babbaj on 3/19/2018.
 */
public class InvalidOffset extends Offset {

    public InvalidOffset(Offset value) {
        super(value.getName(), value.getOffset(), value.getModule());
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    protected void nullCheck() {
        throwException();
    }

    private void throwException() {
        throw new UnsupportedOperationException("Attempted to use invalid offset " + this.getName());
    }

}