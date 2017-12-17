package com.me.overlay;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * @author Brady
 * @since 7/1/2017 11:17 AM
 */
public class CustomBitmapFont extends BitmapFont {

    public CustomBitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip) {
        super(fontFile, imageFile, flip);
    }

    public void drawOutlined(Batch batch, CharSequence str, float x, float y, Color color) {
        setColor(Color.BLACK);
        draw(batch, str, x + 1, y);
        draw(batch, str, x - 1, y);
        draw(batch, str, x, y + 1);
        draw(batch, str, x, y - 1);
        setColor(color);
        draw(batch, str, x, y);
    }
}
