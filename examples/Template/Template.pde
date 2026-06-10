/*
 Template.pde 2026 Rutger Pijnenburg

 This file is a template with all the setup to start.
 */

import com.three.DirectEmbroideryControl.*; //This calls my library, meaning that we can call custom functions

void setup() {
  stitch.initialize(this); //This initializes the system, every sketch needs it.
  stitch.multiplier = 1; //This will increase the size of the entire piece. a multiplier of 1 means that one pixel on the canvas is

  size(500, 500); //You can increase this to get a bigger canvas

  file.makeHeader();

  //Your code goes here!

  file.makeFooter();

  file.save("example.dst"); //Change this name to the location you want to save to, for example: "D:/example.dst" creates the file in the D disk with the name "example.dst"
}