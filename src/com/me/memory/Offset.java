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
        return module.readByte(offset + off);
    }

    public boolean readBoolean(int off) {
        return module.readBoolean(offset + off);
    }

    public short readShort(int off) {
        return (short)module.readShort(offset + off);
    }

    public int readInt(int off) {
        return module.readInt(offset + off);
    }

    public long readUnsignedInt(int off) {
        return module.readUnsignedInt(offset + off);
    }

    public long readLong(int off) {
        return module.readLong(offset + off);
    }

    public float readFloat(int off) {
        return module.readFloat(offset + off);
    }

    public double readDouble(int off) {
        return module.readDouble(offset + off);
    }

    public Pointer getAsPointer(int off) {
        return new Pointer(module.readUnsignedInt(offset + off));
    }

}
