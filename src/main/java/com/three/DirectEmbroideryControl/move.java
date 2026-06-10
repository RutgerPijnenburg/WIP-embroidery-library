package com.three.DirectEmbroideryControl;
import static java.lang.Math.round;

/**
 * This class allows you to move the needle without stabbing the cloth
 */
public class move {
    /**
     * This tells the machine to move (without stitching) to a certain location
     * @param x horizontal location
     * @param y horizontal location
     */
    public static void direct(float x, float y){
        stitch.intendedX=x;
        stitch.intendedY=y;
        stitch.getApp().circle(stitch.intendedX, stitch.intendedY, stitch.stitchSize);

        float dx = stitch.intendedX - stitch.machineX / stitch.multiplier;
        float dy = stitch.intendedY - stitch.machineY / stitch.multiplier;

        int ix = round(dx * stitch.multiplier);
        int iy = round(dy * stitch.multiplier);

        stitch.makeSafeCommand(ix, iy, 1, 0);

        stitch.machineX += ix;
        stitch.machineY += iy;

        stitch.stitchCount++;

    }

    /**
     * This tells the machine to move (without stitching) relative to the current needle position
     * @param dx change in horizontal position
     * @param dy change in vertical position
     */
    public static void relative(float dx, float dy){
        stitch.intendedX += dx;
        stitch.intendedY += dy;
        stitch.getApp().circle(stitch.intendedX, stitch.intendedY, stitch.stitchSize);

        float dx2 = stitch.intendedX - stitch.machineX / stitch.multiplier;
        float dy2 = stitch.intendedY - stitch.machineY / stitch.multiplier;

        int ix = round(dx2 * stitch.multiplier);
        int iy = round(dy2 * stitch.multiplier);

        stitch.makeSafeCommand(ix, iy, 1, 0);

        stitch.machineX += ix;
        stitch.machineY += iy;

        stitch.stitchCount++;
    }
}
