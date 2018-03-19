package com.me.game;

import com.me.memory.Offset;

import static com.me.memory.OffsetManager.getOffset;
import static com.me.memory.Offsets.*;

/**
 * Created by Babbaj on 12/12/2017.
 */
public class ViewMatrix {

    private static ViewMatrix INSTANCE;

    private Offset matrixOffset;

    private float[][] matrix = new float[4][4];

    private ViewMatrix(Offset offset) {
        this.matrixOffset = offset;
    }

    public static ViewMatrix getInstance() {
        return INSTANCE == null ? INSTANCE = new ViewMatrix(getOffset(dwViewMatrix)) : INSTANCE;
    }

    public float[][] getViewMatrix() {
        return this.matrix;
    }

    public void updateMatrix() {
        readMatrix();
    }

    private void readMatrix() {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = matrixOffset.readFloat(count * 4);
                count++;
            }
        }
    }
}
