package com.me.memory;

/**
 * Created by Babbaj on 3/19/2018.
 */
public class InvalidOffset extends Offset {

    public InvalidOffset(Offset value) {
        super(value.getName(), value.getOffset(), value.getModule());
    }

    @Override
    public int readByte(int off) {
        throwException();
        return 0;
    }

    @Override
    public boolean readBoolean(int off) {
        throwException();
        return true || false;
    }

    @Override
    public short readShort(int off) {
        throwException();
        return 0;
    }

    @Override
    public int readInt(int off) {
        throwException();
        return 0;
    }

    @Override
    public long readUnsignedInt(int off) {
        throwException();
        return 0;
    }

    @Override
    public long readLong(int off) {
        throwException();
        return 0;
    }

    @Override
    public float readFloat(int off) {
        throwException();
        return 0;
    }

    @Override
    public double readDouble(int off) {
        throwException();
        return 0;
    }

    @Override
    public Pointer getPointer(int off) {
        throwException();
        return null;
    }



    @Override
    public void writeBoolean(boolean val, int off) {
        throwException();
    }

    @Override
    public void writeByte(byte val, int off) {
        throwException();
    }

    @Override
    public void writeInt(int val, int off) {
        throwException();
    }

    @Override
    public void writeShort(short val, int off) {
        throwException();
    }

    @Override
    public void writeLong(long val, int off) {
        throwException();
    }

    @Override
    public void writeFloat(float val, int off) {
        throwException();
    }

    @Override
    public void writeDouble(double val, int off) {
        throwException();
    }


    private void throwException() {
        throw new UnsupportedOperationException("Attempted to use invalid offset " + this.getName());
    }

}