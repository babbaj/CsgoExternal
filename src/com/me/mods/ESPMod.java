package com.me.mods;

import com.me.mods.util.BaseMod;

import java.awt.*;

/**
 * Created by Babbaj on 12/1/2017.
 */
public class ESPMod extends BaseMod {

    public ESPMod() { super("ESPMod", "see through walls and shit"); }

    @Override
    public void tick() {
        //System.out.println("test mod");
    }

    @Override
    public void draw2d(Graphics g) {
        //g.drawRect(100,100,300,300);
        //g.fillRect(50,50,100,100);
    }


}
