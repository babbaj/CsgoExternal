package com.me;

import java.awt.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.me.config.ConfigManager;
import com.me.game.ViewMatrix;
import com.me.keys.GlobalKeyListener;
import com.me.keys.GlobalMouseListener;
import com.me.memory.Memory;
import com.me.mods.*;
import com.me.mods.AntiFlashMod;
import com.me.mods.util.EntityManagerService;
import com.me.mods.util.ModManager;
import com.me.overlay.*;
import com.me.overlay.Window;
import com.me.scanner.SigScanner;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;


public class Main {

    private static Memory memory;
    private static Overlay overlay;
    private static Robot robot;
    private static float[] res;

    public static final File waifu_file = new File("src/com/me/assets/waifu.png"); // TODO: put in root directory


    public static void main(String[] args) {
        System.out.println("Init Cheat");
        setupKeyListener();
        setMemory(new Memory("csgo.exe"));
        ConfigManager cfg = ConfigManager.getInstance();
        cfg.getAllOffsets();
        cfg.getAllNetVars();
        SigScanner.freeMemory();
        setupMods();
        setOverlay(new Overlay(Window.get("Counter-Strike: Global Offensive")));
        overlay.display();
        Runtime.getRuntime().addShutdownHook(new Thread(overlay::close));

        try {
            while (true) {
                ViewMatrix.getInstance().updateMatrix();
                ModManager.getInstance().tickAllMods();
            }
        } catch (Throwable t) {
            // uncaught exception thrown from a mod
            // (((shut it down)))
            t.printStackTrace();
            System.exit(1);
        }

    }


    private static void setupMods() {
        ModManager modManager = ModManager.getInstance();

        modManager.registerMod(new EntityManagerService());
        modManager.registerMod(new GlowMod());
        modManager.registerMod(new AntiFlashMod());
        modManager.registerMod(new TriggerbotMod());
        modManager.registerMod(new AimbotMod());
        //modManager.registerMod(new ESPMod());
        modManager.registerMod(new BhopMod());
        modManager.registerMod(new AntiRollMod());
        //modManager.registerMod(new ChamsMod());
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
        // TODO: use something better
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
    public static void setOverlay(Overlay overlayIn) {
        overlay = overlayIn;
    }
    public static Overlay getOverlay() {
        return overlay;
    }
    public static Robot getRobot() {
        try {
            return robot == null ? robot = new Robot() : robot;
        } catch (AWTException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static float[] getRes() {
        if (res == null) {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            res = new float[] {dim.width, dim.height};
        }
        return res;
    }
}
