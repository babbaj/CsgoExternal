package com.me.config;

import com.beaudoin.jmm.process.Module;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.me.Main;
import com.me.memory.InvalidOffset;
import com.me.memory.Offset;
import com.me.memory.OffsetManager;
import com.me.memory.Offsets;
import com.me.scanner.SigScanner;
import com.me.scanner.Signature;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Map;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class ConfigManager {

    public static final File config = new File("config.json");

    public static final File signatures = new File("signatures.json");

    public static final ConfigManager INSTANCE = new ConfigManager();

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    public void writeToFile(File file, byte[] bytes, StandardOpenOption... options) {
        try {
            if (!file.exists()) {
                Files.createFile(file.toPath());
            }
            Files.write(file.toPath(), bytes, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject readJson(File file) {
        try (FileReader fr = new FileReader(file.getAbsolutePath())){
            JsonParser parser = new JsonParser();
            return parser.parse(fr).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getAllNetVars() {
        JsonObject json = readJson(signatures);
        JsonObject netvars = json.getAsJsonObject("netvars");
        netvars.entrySet().forEach(entry -> {
            final String name = entry.getKey();
            final int val = entry.getValue().getAsInt();
            if (val < 0)
                System.err.println("Invalid netvar: " + entry.getKey() + " " + val);
            //OffsetManager.addNetVar(name, val);
            //System.out.printf("Name: %s, value: %d\n", name, val);
            setNetvarField(name, val);
        });
    }

    public void getAllOffsets() {
        JsonObject json = readJson(signatures);
        JsonObject signatures = json.getAsJsonObject("signatures");
        signatures.entrySet()
                .forEach(entry -> {
                    Signature sig = signatureFromJson(entry);
                    int off = SigScanner.findOffset(sig);
                    Offset offset = new Offset(entry.getKey(), off, sig.module);
                    if (off == -1) {
                        System.err.println("Invalid offset: " + offset.getName());
                        offset = new InvalidOffset(offset);
                    }
                    OffsetManager.addOffset(offset);
                    setOffsetField(entry.getKey(), offset);
                });
    }

    private static void setOffsetField(String name, Offset value) {
        try {
            Field field = Offsets.class.getDeclaredField(name);
            stripFinal(field);
            field.set(null, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setNetvarField(String name, Integer value) {
        try {
            Field field = Offsets.Netvars.class.getDeclaredField(name);
            stripFinal(field);
            field.set(null, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void stripFinal(Field field) {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (Exception e) {
            throw new Error(e);
        }
    }



    private static Signature signatureFromJson(Map.Entry<String, JsonElement> entry) {
        return signatureFromJson(entry.getKey(), entry.getValue().getAsJsonObject());
    }

    private static Signature signatureFromJson(String name, JsonObject json) {
        Module module = Main.getMemory().getModule(json.get("module").getAsString());
        int flags = 0;
        if (json.get("mode_read").getAsBoolean()) flags |= SigScanner.READ;
        if (json.get("mode_subtract").getAsBoolean()) flags |= SigScanner.SUBTRACT;
        int pattern_offset = json.get("pattern_offset").getAsInt();
        int address_offset = json.get("address_offset").getAsInt();
        String pattern = json.get("pattern").getAsString()
                .replaceAll("\\?+(?=$)|(?<=^)\\?+", "") // remove any leading/trailing unknowns because they're useless
                .replaceAll("\\?{2,}", "?") // remove duplicate ?s
                .trim();
        String mask = Signature.maskFromPatternString(pattern);

        return new Signature(name, module, pattern, mask, flags, pattern_offset, address_offset);
    }


}
