package com.three.DirectEmbroideryControl;
import processing.core.PApplet;

import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static processing.core.PApplet.constrain;

/**
 * This class is used for most control over the machine. it allows you to stich, change colour, cut the thread and set how big you want things to be.
 */
public class stitch {
    /**
     * This is used to connect to processing
     */
    private static PApplet app;
    /**
     * This is the command we are contructing in binary form.
     */
    private static int[] bits={0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0};

    /**
     * This is the horizontal location we want the needle to be in
     */
    public static float intendedX =0;
    /**
     * This is the vertical location we want the needle to be in
     */
    public static float intendedY=0;
    /**
     * This is the actual horizontal location of the needle, rounded up as the machine only allows intergers
     */
    public static int machineX =0;
    /**
     * This is the actual vertical location of the needle, rounded up as the machine only allows intergers
     */
    public static int machineY=0;

    /**
     * this chooses how big the visual indicator on processin will be
     */
    public static int stitchSize = 5;
    /**
     * This counts how many stitches have been made
     */
    public static int stitchCount = 1;
    /**
     *    This allows you to change how many times bigger you want your file to be on the machine, a multiplier of 1 makes one pixel 0.1 mm whereas a multiplier of 230 makes one pixel 2.3 cm
     */
    public static float multiplier = 1;

    /**
     * this initializes the PApplet
     * @param p the processing sketch
     */
    public static void initialize(PApplet p) {
        app = p;
    }

    /**
     * This connects the PApplet to all of the seperate classes
     * @return The PApplet
     */
    static PApplet getApp() {
        return app;
    }

    /**
     *    This denotes how big you want the stitches to show in processing, it doesnt do anything to the file or the machine
     * @param size The size of the visual indicator in processing
     */
    public  static void setSize(int size){
        stitchSize=size;
    }

    /**
     * This takes vertical and horizontal displacement as well as what command has to be done and clamps it so that it fits within one command's maximum size
     * @param dx horizontal displacement
     * @param dy vertical displacement
     * @param c0 control code 1
     * @param c1 control code 2
     */
    static void makeSafeCommand(int dx, int dy, int c0, int c1) {
        dy=-dy;
        if (dx == 0 && dy == 0) {
            makeCommand(0, 0, c0, c1);
            return;
        }

        while (dx != 0 || dy != 0) {

            int stepX = constrain(dx, -121, 121);
            int stepY = constrain(dy, -121, 121);

            makeCommand(stepX, stepY, 1, 0);

            dx -= stepX;
            dy -= stepY;
        }
        makeCommand(0, 0, c0, c1);
    }

    /**
     * This takes the displacement in X and Y direction together with what command has to be done to create the command in binary
     * @param number1 horizontal displacement
     * @param number2 vertical displacement
     * @param controlCode1 each command can be represennted with two control codes, this is the first of the two
     * @param controlCode2 the second control code
     */
    static void makeCommand(int number1, int number2, int controlCode1, int controlCode2) {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = 0;
        }

        int[] weights = {81, 27, 9, 3, 1};

        int[] xPos = {21, 13, 5, 15, 7};
        int[] xNeg = {20, 12, 4, 14, 6};

        int[] yPos = {18, 10, 2, 8, 0};
        int[] yNeg = {19, 11, 3, 9, 1};

        int x = number1;
        int y = number2;

        // --- encode X ---
        for (int i = 0; i < weights.length; i++) {
            int w = weights[i];

            // try positive contribution
            if (x > 0 && abs(x - w) < abs(x)) {
                bits[xPos[i]] = 1;
                x -= w;
            }

            // try negative contribution (independently!)
            if (x < 0 && abs(x + w) < abs(x)) {
                bits[xNeg[i]] = 1;
                x += w;
            }
        }

        // --- encode Y ---
        for (int i = 0; i < weights.length; i++) {
            int w = weights[i];

            if (y > 0 && abs(y - w) < abs(y)) {
                bits[yPos[i]] = 1;
                y -= w;
            }

            if (y < 0 && abs(y + w) < abs(y)) {
                bits[yNeg[i]] = 1;
                y += w;
            }
        }

        // control bits
        bits[16] = controlCode1;
        bits[17] = controlCode2;
        bits[22] = 1;
        bits[23] = 1;

        // pack bytes
        int byte1 =
                bits[0]*128 + bits[1]*64 + bits[2]*32 + bits[3]*16 +
                        bits[4]*8 + bits[5]*4 + bits[6]*2 + bits[7];

        int byte2 =
                bits[8]*128 + bits[9]*64 + bits[10]*32 + bits[11]*16 +
                        bits[12]*8 + bits[13]*4 + bits[14]*2 + bits[15];

        int byte3 =
                bits[16]*128 + bits[17]*64 + bits[18]*32 + bits[19]*16 +
                        bits[20]*8 + bits[21]*4 + bits[22]*2 + bits[23];

        file.commands.add((byte)byte1);
        file.commands.add((byte)byte2);
        file.commands.add((byte)byte3);
    }

    /**
     * This tells the machine to stitch at a specific location
     * @param x horizontal location
     * @param y vertical location
     */
    public static void direct(float x, float y){
        intendedX=x;
        intendedY=y;
        app.circle(intendedX, intendedY, stitchSize);

        float dx = intendedX - machineX / multiplier;
        float dy = intendedY - machineY / multiplier;

        int ix = round(dx * multiplier);
        int iy = round(dy * multiplier);

        makeSafeCommand(ix, iy, 0, 0);

        machineX += ix;
        machineY += iy;

        stitchCount++;

    }

    /**
     * This tells the machine to stitch relative to where the needle currently is
     * @param dx the change in horizontal location
     * @param dy the change in vertical location
     */
    public static void relative(float dx, float dy){
        intendedX += dx;
        intendedY += dy;
        app.circle(intendedX, intendedY, stitchSize);

        float dx2 = intendedX - machineX / multiplier;
        float dy2 = intendedY - machineY / multiplier;

        int ix = round(dx2 * multiplier);
        int iy = round(dy2 * multiplier);

        makeSafeCommand(ix, iy, 0, 0);

        machineX += ix;
        machineY += iy;

        stitchCount++;

    }

    /**
     * This changes the colour of the current thread, the colour has to be selected on the machine itself
     */
    public static void changeColour(){
        makeSafeCommand(0, 0, 1, 1);
    }

    /**
     * This changes the colour of the current thread, the colour has to be selected on the machine itself
     */
    public static void changeColor(){
        makeSafeCommand(0, 0, 1, 1);
    }

    /**
     * This cuts the thread of the machine
     */
    public static void cut(){
        makeSafeCommand(0, 0, 0, 1);
    }

    /**
     * This creates a frame around the entire canvas. This is a useful trick in cases where you want to overlap multiple files as it counteracts the "centering" the machine normally does. You can turn off the machine just before it actually makes the frame if needed
     * @param size the distance between two stitches within the frame
     */
    public static void frame(int size){
        cut();
        for (int x=0; x<app.width; x+=size) {
            direct(x, 0);
        }
        direct(app.width, 0);

        for (int y=0; y<app.height; y+=size) {
            direct(app.width, y);
        }
        direct(app.width, app.height);

        for (int x=app.width; x>0; x-=size) {
            direct(x, app.height);
        }
        direct(0, app.height);

        for (int y=app.height; y>0; y-=size) {
            direct(0, y);
        }
        direct(0, 0);
        direct(0, 0);
        changeColour();
    }
}
