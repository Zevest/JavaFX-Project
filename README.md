# ProcessJFX

This is a school OOP project using **_javafx_** made with **_eclipse_**, I'm trying to remake processing's functionment with javafx.
It can currently run really simple 2D processing sketch without much work.
For this to work, you need to copy your processing's Sketch folder and then change the extension to `.pjfx`.
Finally you have to change the following:

* `int(x)` -> `_int(x)`
* `float(x)` -> `_float(x)`
* `float g = 12.6` -> `float g = 12.6f`
* Add public keyword to the following function
  * __setup()__
  * __draw()__
  * __mousePressed()__
  * __mouseReleased()__
  * __mouseDragged()__
  * __mouseMoved()__
  * __keyPressed()__
  * __keyReleased()__
  * __keyTyped()__

## How to Run your sketch

 1. Move to project directory
 2. `java -cp ./bin compiler.Projfx SketchFolderName`

 To specify the directory in wich your sketch forlder is located add the absolute path from the parent directory of the sketch as second argument.
  
* `java -cp ./bin compiler.Projfx SketchFolderName AbsolutePathToSketchParentFolder`
