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
import com.me.mods.*;
import com.me.mods.AntiFlashMod;
import com.me.mods.util.BaseMod;
import com.me.mods.util.EntityManagerService;
import com.me.mods.util.ModManager;
import com.me.overlay.ExternalOverlay;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import javax.swing.*;

import static com.me.memory.OffsetManager.getOffset;
import static com.me.memory.OffsetManager.getOffsetVal;
import static com.me.memory.OffsetManager.getStructOffset;

public class Main {

    private static Memory memory;
    private static JFrame overlay;
    private static Robot robot;


    public static void main(String[] args) {
        System.out.println("Init Cheat");
        setupKeyListener();
        setMemory(new Memory("csgo.exe"));
        ConfigManager cfg = ConfigManager.getInstance();
        cfg.getAllOffsets();
        cfg.getAllStructOffsets();
        setupMods();

        setOverlay(new ExternalOverlay() {
            @Override
            public void draw(Graphics g) {
                ModManager.getInstance().forEach(mod -> mod.draw2d(g));

            }
        });


        System.out.println(Integer.toHexString(getOffset("dwViewMatrix").getOffset()));
        while (true) {
            ModManager.getInstance().forEach(BaseMod::tick);
        }

    }


    private static void setupMods() {
        ModManager modManager = ModManager.getInstance();

        modManager.registerMod(new EntityManagerService());
        modManager.registerMod(new GlowMod());
        modManager.registerMod(new AntiFlashMod());
        //modManager.registerMod(new TriggerbotMod());
        modManager.registerMod(new AimbotMod());
        modManager.registerMod(new ESPMod());
        modManager.registerMod(new BhopMod());
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
    public static Robot getRobot() {
        try {
            return robot == null ? robot = new Robot() : robot;
        } catch (AWTException e) {
            return null;
        }
    }
}
