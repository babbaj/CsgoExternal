package com.me.memory;

import com.me.Main;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class Pointer {

    private long address;

    public Pointer(long addressIn) {
        this.address = addressIn;
    }


    public long getAddress() {
        return this.address;
    }

    //
    // Dereferencing methods
    //
    public boolean readBoolean(int off) {
        nullCheck();
        return Main.getMemory().getProc().readBoolean(address + off);
    }

    public int readByte(int off) {
        nullCheck();
        return Main.getMemory().getProc().readByte(address + off);
    }

    public int readInt(int off) {
        nullCheck();
        return Main.getMemory().getProc().readInt(address + off);
    }

    public long readUnsignedInt(int off) {
        nullCheck();
        return Main.getMemory().getProc().readUnsignedInt(address + off);
    }

    public short readShort(int off) {
        nullCheck();
        return (short)Main.getMemory().getProc().readShort(address + off);
    }

    public long readLong(int off) {
        nullCheck();
        return Main.getMemory().getProc().readLong(address + off);
    }

    public float readFloat(int off) {
        nullCheck();
        return Main.getMemory().getProc().readFloat(address + off);
    }

    public double readDouble(int off) {
        nullCheck();
        return Main.getMemory().getProc().readDouble(address + off);
    }


    //
    // Write to memory
    //
    public void writeBoolean(boolean val, int off) {
        nullCheck();
        Main.getMemory().getProc().writeBoolean(address + off, val);
    }

    public void writeByte(byte val, int off) {
        nullCheck();
        Main.getMemory().getProc().writeByte(address + off, val);
    }

    public void writeInt(int val, int off) {
        nullCheck();
        Main.getMemory().getProc().writeInt(address + off, val);
    }

    public void writeUnsignedInt(long val, int off) {
        nullCheck();
        Main.getMemory().getProc().writeInt(address + off, (int)val);
    }

    public void writeShort(short val, int off) {
        nullCheck();
        Main.getMemory().getProc().writeShort(address + off, val);
    }

    public void writeLong(long val, int off) {
        nullCheck();
        Main.getMemory().getProc().writeLong(address + off, val);
    }

    public void writeFloat(float val, int off) {
        nullCheck();
        Main.getMemory().getProc().writeFloat(address + off, val);
    }

    public void writeDouble(double val, int off) {
        nullCheck();
        Main.getMemory().getProc().writeDouble(address + off, val);
    }


    private void nullCheck() {
        if (isNull()) throw new NullPointerException("NULL pointer");
    }

    public boolean isNull() {
        return !Main.getMemory().getProc().canRead(address, 8);
    }
}
