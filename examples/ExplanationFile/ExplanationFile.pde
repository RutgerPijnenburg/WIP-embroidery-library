/*
 ExplanationFile.pde 2026 Rutger Pijnenburg

 This is a file created to explain some of the basics of using this library.
 This library can be used to create embroidery files and can send these files directly into a Brother embroidery machine.

 You can create whatever code you want, however there are requirements, every file needs to import the library and initialize my system, this code is already there.
 Furthermore, Every file has to start with a header, which you can call with "file.footer()" and end with a footer "file.footer()"
 what you put in between these two is up to you, the basic commands are stitch.direct(x,y) and stitch.relative(dx, dy) look on the second tab for in depth explanation on all commands.

 When you are done, you can save your embroidery file using "file.save(name)" if you call it "example.dst" a file named "example.dst" will be added to the sketch folder, which can be opened with ctrl+K
 if you make the name a filepath: "D:/example.dst" it will save the file to the corresponding location.

 This particular file creates a grid of points in two seperate ways, each with its own colour. the entire thing is encased in a frame.
 */

import com.three.DirectEmbroideryControl.*; //This calls my library, meaning that we can call custom functions

void setup() {
  stitch.initialize(this); //This initializes the system, every sketch needs it.
  stitch.multiplier = 1; //This will increase the size of the entire piece. a multiplier of 1 means that one pixel on the canvas is

  size(500, 500);

  file.makeHeader();

  for (int x=1; x<10; x++) {
    for (int y=1; y<10; y++) {
      stitch.direct(x*20, y*20);
    }
  }

  stitch.changeColour();

  stitch.direct(250, 250);
  for (int x=0; x<15; x++) {
    for (int y=0; y<15; y++) {
      stitch.relative(15, 0);
    }
    if (x!=14) {
      stitch.relative(-225, 15);
    }
  }

  stitch.frame(5);

  file.makeFooter();
  file.save("D:/example.dst");
}
