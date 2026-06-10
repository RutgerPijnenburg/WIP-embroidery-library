/*
 FillShapes.pde 2026 Rutger Pijnenburg

 This file is an example for how you can fill a shape, it also shows how draw() can be used to have continuous interaction.
 First select multiple points with your mouse and then press space for it to fill in.

 this code was originally created with the help of chatGPT
 */

import com.three.DirectEmbroideryControl.*; //This calls my library, meaning that we can call custom functions

ArrayList<PVector> polygon;
int cooldown;
int iterations;

void setup() {
  stitch.initialize(this); //This initializes the system, every sketch needs it.
  stitch.multiplier = 1; //This will increase the size of the entire piece. a multiplier of 1 means that one pixel on the canvas is
  polygon = new ArrayList<PVector>();

  size(500, 500); //You can increase this to get a bigger canvas

  file.makeHeader();
}

void draw() {
  beginShape();
  for (PVector v : polygon) {
    circle(v.x, v.y, 20);
  }
  endShape(CLOSE);

  if (cooldown<1) {
    if (mousePressed) {
      polygon.add(new PVector(mouseX, mouseY));
      cooldown=20;
    }
  } else {
    cooldown--;
  }

    if (keyPressed&&polygon.size()>2) {
      fillWithLines(radians(45), 10);
      fillWithLines(radians(0), 20);

    polygon = new ArrayList<PVector>();
    stitch.cut();
    stitch.changeColour();
    stitch.frame(10);
    file.finish("ScanLines."+iterations+".dst"); //Using this iterations trick, you can save all files used in a full session
    iterations++;
  }
}

//This functions takes your current shape and fills it in with lines that have certain rotation and spacing.
void fillWithLines(float angle, float spacing) {
  float cosA = cos(angle);
  float sinA = sin(angle);

  ArrayList<PVector> rotated = new ArrayList<PVector>();
  for (PVector v : polygon) {
    float rx = v.x * cosA + v.y * sinA;
    float ry = -v.x * sinA + v.y * cosA;
    rotated.add(new PVector(rx, ry));
  }

  float minY = Float.MAX_VALUE;
  float maxY = -Float.MAX_VALUE;

  for (PVector v : rotated) {
    minY = min(minY, v.y);
    maxY = max(maxY, v.y);
  }

  boolean leftToRight = true;

  for (float y = minY; y <= maxY; y += spacing) {
    ArrayList<Float> intersections = new ArrayList<Float>();

    // Intersections in rotated space
    for (int i = 0; i < rotated.size(); i++) {
      PVector a = rotated.get(i);
      PVector b = rotated.get((i + 1) % rotated.size());

      if ((a.y <= y && b.y > y) || (b.y <= y && a.y > y)) {
        float t = (y - a.y) / (b.y - a.y);
        float x = a.x + t * (b.x - a.x);
        intersections.add(x);
      }
    }

    intersections.sort(null);

    for (int i = 0; i < intersections.size(); i += 2) {
      float x1 = intersections.get(i);
      float x2 = intersections.get(i + 1);

      float sx, ex;

      if (leftToRight) {
        sx = x1;
        ex = x2;
      } else {
        sx = x2;
        ex = x1;
      }

      PVector p1 = invRotate(sx, y, cosA, sinA);
      PVector p2 = invRotate(ex, y, cosA, sinA);

      stitch.direct(p1.x, p1.y);
      stitch.direct(p2.x, p2.y);
      line(p1.x, p1.y, p2.x, p2.y);
    }

    leftToRight = !leftToRight;
  }
}

PVector invRotate(float x, float y, float cosA, float sinA) {
  float rx = x * cosA - y * sinA;
  float ry = x * sinA + y * cosA;
  return new PVector(rx, ry);
}