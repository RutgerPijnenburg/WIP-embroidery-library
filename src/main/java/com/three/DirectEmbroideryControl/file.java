package com.three.DirectEmbroideryControl;
import java.util.ArrayList;

/**
 * This class is used for anything that has to do with the file itself, it allows you to add the header, footer, reset but also save and send the current file to a location.
 */
public class file {
    /**
     * This is all the data the header needs to have. it is added on top of any file.
     */
    private static final byte[] header= {76,65,58,108,111,110,103,32,104,111,114,105,122,111,110,116,97,108,32,13,83,84,58,32,32,32,32,32,51,48,13,67,49,58,32,32,48,13,43,88,58,32,32,50,49,57,13,45,88,58,32,32,50,49,57,13,43,89,58,32,32,32,32,48,13,45,89,58,32,32,32,32,48,13,65,88,58,43,32,32,50,49,57,13,65,89,58,43,32,32,32,32,48,13,77,88,58,43,32,32,32,32,48,13,77,89,58,43,32,32,32,32,48,13,80,68,58,42,42,42,42,42,42,13,26,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32};
    /**
     * This arraylist stores all commands that have been added to the current file
     */
    static ArrayList<Byte> commands = new ArrayList<Byte>();

    /**
     *    This creates the header of the file, every file needs one at the very start else the machine cannot read your file
     */
    public static void makeHeader() {
        for (byte b : header) {
            commands.add(b);
        }
    }

    /**
     *    This creates the footer of the file, every file needs one at the very start else the machine cannot read your file
     */
    public static void makeFooter() {
        commands.add((byte)0);
        commands.add((byte)0);
        commands.add((byte)243);
    }

    /**
     *    This saves the current file to a selected location. if you just pass a name such as "name.dst" it will save the file in the project folder, but you can select a filepath: "D:/name.dst" to save a specific location (including the embroidery machine if you are connceted!) ".DST" or ".dst" must be added to the end of every filename.
     * @param filename you can select a filepath: "D:/name.dst" to save a specific location
     */
    public static void send(String filename) {
        byte[] data = new byte[commands.size()];

        for (int i = 0; i < commands.size(); i++) {
            data[i] = commands.get(i);
        }
        stitch.getApp().saveBytes(filename, data);
        System.out.println("the file was saved at: "+filename);
    }

    /**
     *    This saves the current file to a selected location. if you just pass a name such as "name.dst" it will save the file in the project folder, but you can select a filepath: "D:/name.dst" to save a specific location (including the embroidery machine if you are connceted!) ".DST" or ".dst" must be added to the end of every filename.
     * @param filename you can select a filepath: "D:/name.dst" to save a specific location
     */
    public static void save(String filename) {
    send(filename);
    }

    /**
     *    This clears all commands up until this point (Including the header) and sets all coordinates to (0,0)
     */
    public static void reset(){
        commands.clear();
        for (byte b : header) {
            commands.add(b);
        }

        stitch.intendedX =0;
        stitch.intendedY =0;
        stitch.machineX =0;
        stitch.machineY =0;
    }

    /**
     * This creates the footer, sends the file to the specified location and then resets the canvas
     *
     * @param filename you can select a filepath: "D:/name.dst" to save a specific location
     */
    public static void finish(String filename) { //footer, send and reset
    makeFooter();
    send(filename);
    reset();
    }
}
