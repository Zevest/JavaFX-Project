# ProcessJFX

This is a school OOP project using **_javafx_** made with **_eclipse_**, I'm trying to remake processing's functionment with javafx.
It can currently run really simple 2D processing sketch without much work.
For this to work, you need to copy your processing's Sketch folder and then change the extension to `.pjfx`.
Finally you have to change the following:

* `int(x)` -> `_int(x)`
* `float(x)` -> `_float(x)`
* `float g = 12.6` -> `float g = 12.6f`

## How to Run sketch

 1. Move to project directory
 2. `java -cp compiler.Projfx SketchFolderName`

 To specify the directory in wich your sketch forlder is located add the absolute path from the parent directory of the sketch as second argument.
  
* `java -cp compiler.Projfx SketchFolderName AbsolutePathToSketchParentFolder`
