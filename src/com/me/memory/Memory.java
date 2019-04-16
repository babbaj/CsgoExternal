package com.me.memory;

import com.beaudoin.jmm.process.Module;
import com.beaudoin.jmm.process.NativeProcess;
import com.beaudoin.jmm.process.impl.win32.Win32Process;

import java.lang.reflect.Field;
import java.util.Map;

public class Memory {

    private final NativeProcess proc;
    private final Module client;
    private final Module engine;

    public Memory(String processName) {
        proc = NativeProcess.byName(processName);
        System.out.println("Process found : " + getProc().id());
        Win32Process process = ((Win32Process)this.getProc());
        process.initModules();
        /*try {
            Field f = Win32Process.class.getDeclaredField("modules");
            f.setAccessible(true);
            Map<String, Module> map = (Map)f.get(process);
            map.keySet().forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        this.client = this.getProc().findModule("client.dll");


        System.out.println("Client found : " + this.getClient().address());
        this.engine = this.getProc().findModule("engine.dll");
        System.out.println("Engine found : " + this.getEngine().address());
    }

    public NativeProcess getProc() {
        return proc;
    }

    public Module getClient() {
        return client;
    }

    public Module getEngine() {
        return engine;
    }


    public Module getModule(String module) {
        Module mod = null;
        switch (module) {
            case "client.dll": mod = getClient();
                break;
            case "engine.dll": mod = getEngine();
                break;
        }
        return mod;
    }
}
