package com.me;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.me.config.ConfigManager;
import com.me.game.Entity;
import com.me.game.EntityManager;
import com.me.keys.GlobalKeyListener;
import com.me.keys.GlobalMouseListener;
import com.me.memory.Memory;
import com.me.memory.Offset;
import com.me.memory.OffsetManager;
import com.me.memory.Pointer;
import com.me.mods.util.BaseMod;
import com.me.mods.util.EntityManagerService;
import com.me.mods.util.ModManager;
import com.me.overlay.ExternalOverlay;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import javax.swing.*;

public class Main {

    private static Memory memory;
    private static JFrame overlay;


    public static void main(String[] args) {
        System.out.println("Init Cheat");
        setupKeyListener();
        setMemory(new Memory("csgo.exe"));
        setupMods();

        setOverlay(new ExternalOverlay() {
            @Override
            public void draw(Graphics g) {
                ModManager.getInstance().forEach(mod -> mod.draw2d(g));

            }
        });
        //long yawAddress = memory.getEngine().address() + 0x47F1B8;
        //float yaw = memory.getProc().readFloat(yawAddress);
        //memory.getProc().writeFloat(yawAddress, yaw + 90f);


        ConfigManager cfg = ConfigManager.getInstance();
        cfg.getAllOffsets();
        cfg.getAllStructOffsets();
        System.out.println(OffsetManager.getOffsetVal("m_dwLocalPlayer")); // correct
        System.out.println(OffsetManager.getOffsetVal("m_dwEntityList")); // correct
        System.out.println(OffsetManager.getOffsetVal("m_dwClientState")); // correct?
        System.out.println(OffsetManager.getOffsetVal("dwClientState_ViewAngles")); // correct
        System.out.println(OffsetManager.getOffsetVal("m_dwGlowObject")); // correct


        //System.out.println(new Offset("meme",100, memory.getClient()).readUnsignedInt(0x4));
        //for (int i = 0; i < 128; i += 4)
        //System.out.println("players: " + OffsetManager.getOffset("m_dwGlowObject").readUnsignedInt(i));
        //System.exit(1);
        //while(true) {
            ModManager.getInstance().tickAllMods();
        //}
        Entity meme = EntityManager.getInstance().getEntity(0);
        System.out.println("test " + meme.getPointer().getAddress());
        long health = meme.getPointer().readUnsignedInt(252);
        System.out.println("health: " + health);

        long address = OffsetManager.getOffset("m_dwEntityList").readUnsignedInt(0);
        System.out.println("meme: " + address);
    }


    public static void setupMods() {
        ModManager modManager = ModManager.getInstance();

        modManager.registerMod(new EntityManagerService());
        //modManager.registerMod(new ESPMod());
        //modManager.registerMod(new FlipMod());
    }

    public static void setupKeyListener() {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
        GlobalMouseListener m = new GlobalMouseListener();
        GlobalScreen.addNativeMouseListener(m);
        GlobalScreen.addNativeMouseMotionListener(m);
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    }

    public static void setMemory(Memory memory) {
        Main.memory = memory;
    }
    public static Memory getMemory() {
        return memory;
    }
    public static void setOverlay(JFrame overlayIn) {
        overlay = overlayIn;
    }
    public static JFrame getOverlay() {
        return overlay;
    }
}
