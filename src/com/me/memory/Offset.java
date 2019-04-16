package com.me.memory;

import com.beaudoin.jmm.process.Module;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class Offset {

    private String name;
    private int offset;
    private Module module;


    public Offset(String nameIn, int offsetIn, Module moduleIn) {
        this.name = nameIn;
        this.offset = offsetIn;
        this.module = moduleIn;
    }

    public String getName() {
        return name;
    }


    public int getOffset() {
        return offset;
    }

    public boolean isNull() {
        return offset == 0;
    }

    public Module getModule() {
        return this.module;
    }


    //
    // Dereferencing methods
    //
    public int readByte(int off) {
        nullCheck();
        return module.readByte(offset + off);
    }

    public boolean readBoolean(int off) {
        nullCheck();
        return module.readBoolean(offset + off);
    }

    public short readShort(int off) {
        nullCheck();
        return (short)module.readShort(offset + off);
    }

    public int readInt(int off) {
        nullCheck();
        return module.readInt(offset + off);
    }

    public long readUnsignedInt(int off) {
        nullCheck();
        return module.readUnsignedInt(offset + off);
    }

    public long readLong(int off) {
        nullCheck();
        return module.readLong(offset + off);
    }

    public float readFloat(int off) {
        nullCheck();
        return module.readFloat(offset + off);
    }

    public double readDouble(int off) {
        nullCheck();
        return module.readDouble(offset + off);
    }

    public Pointer getPointer(int off) {
        nullCheck();
        return Pointer.of(module.readUnsignedInt(offset + off));
    }

    //
    // Write to memory
    //
    public void writeBoolean(boolean val, int off) {
        nullCheck();
        module.writeBoolean(offset + off, val);
    }

    public void writeByte(byte val, int off) {
        nullCheck();
        module.writeByte(offset + off, val);
    }

    public void writeInt(int val, int off) {
        nullCheck();
        module.writeInt(offset + off, val);
    }

    public void writeShort(short val, int off) {
        nullCheck();
        module.writeShort(offset + off, val);
    }

    public void writeLong(long val, int off) {
        nullCheck();
        module.writeLong(offset + off, val);
    }

    public void writeFloat(float val, int off) {
        nullCheck();
        module.writeFloat(offset + off, val);
    }

    public void writeDouble(double val, int off) {
        nullCheck();
        module.writeDouble(offset + off, val);
    }


    protected void nullCheck() {
        if (isNull())
            throw new NullPointerException("null pointer: " + offset);
    }


}
