# Processing Library: Direct Embroidery Control
This is a library that allows users to communicate directly to a Brother embroidery machine.
Using this library you can create .DST files directly in processing and send them to an embroidery machine to execute.

## Getting started:
You can download processing here: https://processing.org/download
Download the library, take the file called "DirectEmbroideryControl.zip" and extract it.
The resulting file has to be added to your processing libraries.
In processing, go to File > Preferences, here you can find where you have saved processing.
Go over there and add the "DirectEmbroideryControl" folder we just extracted into the Processing/Libraries folder.
After this you should be good to go.
There are example documents, such as explanations and templates that can be found in File>Examples

## Commands:
Here is the list of all commands present in the library:
### Stitch:
The stitch command is used for most communication with the machine

- **stitch.initialize(PApplet p)<br>**
  This initializes the connection between my library and the engine that draws things onto the screen in processing<br/>
- **stitch.direct(float x, float y)<br>**
  This tells the machine a location where it will stitch. it uses coordinates so if you input stitch.direct(20,20) it will stitch at the location (x=20, y=20)
- **stitch relative(float dx, float dy) <br>**
  This also makes the machine stitch at a certain location, but unlike direct, it stitches a certain distance from where the needle currently is. stitch.relative(20,20) moves 20 to the right and 20 down
- **stitch.changeColour() / stitch.changeColor<br>**
  This makes the machine cut the thread followed by changing the colour, you have to select what colour it changes into manually on the machine
- **stitch.cut()<br>**
  This makes the machine cut the current thread
- **stitch.frame(int size)<br>**
  This creates a frame around the entire canvas. This is a useful trick in cases where you want to overlap multiple files as it counteracts the "centering" the machine normally does. You can turn off the machine just before it actually makes the frame if needed
- **stitch.setSize(int size)<br>**
  This denotes how big you want the the stitches to show in processing, it doesnt do anything to the file or the machine
- **int stitch.multiplier<br>**
  This allows you to change how many times bigger you want your file to be on the machine, a multiplier of 1 makes one pixel 0.1 mm whereas a multiplier of 230 makes one pixel 2.3 cm
- **int stitch.stitchCount<br>**
  This variable stores how many stitches have been done in the session

### Move:
Moving is quite similar to stitching, however unlike stitch, it does not stab the fabric.
- **move.direct(float x, float y)**<br>
  This tells the machine a location where it will move to. it uses coordinates so if you input stitch.direct(20,20) it will stitch at the location (x=20, y=20)
- **move.relative(float dx, float dy)**<br>
  This also makes the machine stitch at a certain location, but unlike direct, it moves a certain distance from where the needle currently is. stitch.relative(20,20) moves 20 to the right and 20 down

### File:
The file commands are used to set up various parts of the .DST file:
- **file.makeHeader()**<br>
  This creates the header of the file, every file needs one at the very start else the machine cannot read your file
- **file.makeFooter()**<br>
  This creates the footer of the file, every file needs one at the very start else the machine cannot read your file
- **file.send(String filename) / file. save(String filename)**<br>
  This saves the current file to a selected location. if you just pass a name such as "name.dst" it will save the file in the project folder, but you can select a filepath: "D:/name.dst" to save a specific location (including the embroidery machine if you are connceted!) ".DST" or ".dst" must be added to the end of every filename.
- **file.reset()**<br>
  This clears all commands up until this point (Including the header) and sets all coordinates to (0,0)
- **file.finish(String filename)**<br>
  This is a combination of makeFooter(), save() and reset(). can be a useful shortcut.

## Contributors:
Rutger Pijnenburg