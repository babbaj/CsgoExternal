package com.me.scanner;

import com.beaudoin.jmm.misc.Cacheable;
import com.beaudoin.jmm.misc.MemoryBuffer;
import com.beaudoin.jmm.process.Module;
import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Babbaj on 11/29/2017.
 */
public class SigScanner {
    // flags
    // if the address we find is a pointer
    public static final int READ = 1;
    // subtract the address from the modules base address to get the offset
    public static final int SUBTRACT = 2;


    private byte[] region;
    private Module module;

    private static Map<Module, SigScanner> moduleScannerMap = new HashMap<>();

    /**
     * @param moduleIn module to scan
     */
    public SigScanner(Module moduleIn) {
        this.module = moduleIn;
    }

    public void dumpMemory() {
        // copy the memory from the process to our local copy
        MemoryBuffer buff = this.module.read(0, module.size());
        region = buff.array();
        System.out.printf("successfully read %d bytes of memory\n", region.length);
    }

    public static int findOffset(Signature sig) {
        SigScanner scanner = moduleScannerMap.get(sig.module);
        if (scanner == null) {
            scanner = new SigScanner(sig.module);
            scanner.dumpMemory();
            moduleScannerMap.put(sig.module, scanner);
        }
        return scanner.findOffset(sig.pattern, sig.mask, sig.flags, sig.pattern_offset, sig.address_offset);
    }

    /**
     *
     * @param pattern pattern of bytes to find
     * @param mask String representing which bytes of the pattern to match - 'x' indicates that a byte will be checked
     * @return if a match is found return the found index + the param offset
     */
    //TODO: probably need to optimize this shit
    public int findOffset(int[] pattern, @Nullable String mask, int flags, int pattern_offset, int address_offset) {
        if (mask == null) mask = Signature.maskFromPattern(pattern);
        if (pattern.length != mask.length()) throw new IllegalArgumentException("pattern length not equal to mask length");

        byte[] subRegion = new byte[pattern.length];
        for (int i = 0; i < region.length - pattern.length; i++) {// iterate through memory space
            System.arraycopy(region, i, subRegion, 0, subRegion.length);
            if (regionMatches(subRegion, pattern, mask)) {
                i += pattern_offset;
                if ((flags & READ) == READ) {
                    i = module.readInt(i);
                }
                if ((flags & SUBTRACT) == SUBTRACT) {
                    i -= module.address();
                }
                return i + address_offset;
            }
        }

        return -1; // not found
    }

    private boolean regionMatches(byte[] subRegion, int[] pattern, String mask) {
        for (int i = 0; i < subRegion.length; i++) {
            if (mask.charAt(i) == 'x' && subRegion[i] != (byte)pattern[i]) {
                return false;
            }
        }

        return true;
    }

    public static void freeMemory() {
        // let the gc clean this up
        //moduleScannerMap.values().forEach(scanner -> scanner.region = null);
        moduleScannerMap.clear();
    }


    //0x22 meme method
    /*public static int getAddressForPattern(Module module, int pattern_offset, int address_offset, int flags, byte... values) {
        long off = module.size() - values.length;
        for (int i = 0; i < off; i++) {
            if (checkMask(module, i, values)) {
                i += module.address() + pattern_offset;
                if ((flags & READ) == READ) {
                    i = module.process().readInt(i);
                }
                i -= module.address();
                return i + address_offset;
            }
        }
        return -1;
    }

    public static int getAddressForPattern(Module module, int pattern_offset, int address_offset, int flags, String values) {
        String[] s_values = values.split(" ");
        int[] i_values = new int[s_values.length];
        for (int i = 0; i < s_values.length; i++) {
            String s = s_values[i];
            if (s.equals("?"))
                i_values[i] = 0x0;
            else
                i_values[i] = Integer.parseInt(s_values[i], 16);
        }
        return getAddressForPattern(module, pattern_offset, address_offset, flags, toByteArray(i_values));
    }
    private static byte[] toByteArray(int... value) {
        byte[] byteVals = Cacheable.array(value.length);
        for (int i = 0; i < value.length; i++) {
            byteVals[i] = (byte) value[i];
        }
        return byteVals;
    }

    private static boolean checkMask(Module module, int offset, byte[] pMask) {
        for (int i = 0; i < pMask.length; i++) {
            if (pMask[i] != 0x0 && (pMask[i] != module.data().getByte(offset + i))) {
                return false;
            }
        }
        return true;
    }*/

}
