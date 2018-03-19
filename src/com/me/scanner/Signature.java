package com.me.scanner;

import com.beaudoin.jmm.process.Module;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class Signature {

    /**
     * too lazy to get/set
     */
    public Module module;
    public int flags;
    public int pattern_offset;
    public int address_offset;
    public int[] pattern;
    public String mask;

    public String name;

    public Signature(String nameIn, Module moduleIn, int[] patternIn, @Nullable String maskIn, int flagsIn, int pattern_offsetIn, int address_offsetIn) {
        this.name = nameIn;
        this.module = moduleIn;
        this.flags = flagsIn;
        this.pattern_offset = pattern_offsetIn;
        this.address_offset = address_offsetIn;
        this.pattern = patternIn;
        if (maskIn == null) maskIn = maskFromPattern(patternIn);
        this.mask = maskIn;
    }

    // Alternate constructor using a string to represent the pattern
    public Signature(String nameIn, Module moduleIn, String patternIn, String maskIn, int flagsIn, int pattern_offsetIn, int address_offsetIn) {
        this(nameIn, moduleIn, patternFromString(patternIn), maskIn, flagsIn, pattern_offsetIn, address_offsetIn);
    }

    public static String maskFromPattern(int[] pattern) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pattern.length; i++) {
            sb.append(pattern[i] != 0x00 ? "x" : "?");
        }
        return sb.toString();
    }

    public static int[] patternFromString(String values) {
        if (values.equals("")) return new int[0];
        String[] s_values = values.split(" +");
        int[] i_values = new int[s_values.length];
        for (int i = 0; i < s_values.length; i++) {
            String s = s_values[i];
            if (s.equals("?"))
                i_values[i] = 0x0;
            else
                i_values[i] = Integer.parseInt(s_values[i], 16);
        }
        return i_values;
    }

    public static String maskFromPatternString(String values) {
        return values.replaceAll("(?<=^|\\b)[\\w|\\d]{1,2}(?=$|\\b)", "x") // replace all bytes with "x" - may be no more than 2 chars long
                     .replaceAll("[^x|?]", "") // filter any invalid characters that shouldn't be there
                     .replaceAll("\\s", ""); // remove all spaces
    }

    /*public Signature flags(int flagsIn) {
        this.flags = flagsIn;
        return this;
    }

    public Signature pattern_offset(int offset) {
        this.pattern_offset = offset;
        return this;
    }

    public Signature address_offset(int offset) {
        this.address_offset = offset;
        return this;
    }

    public Signature module(Module moduleIn) {
        this.module = moduleIn;
        return this;
    }

    public Signature pattern(int[] patternIn) {
        this.pattern = patternIn;
        return this;
    }

    public Signature pattern(String patternIn) {
        this.pattern = patternFromString(patternIn);
        return this;
    }*/


}
