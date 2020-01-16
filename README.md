# ProcessJFX

This is a school OOP project using **_javafx_** made with **_eclipse_**, I'm trying to remake processing's functionment with javafx.
The auto (compile/run) only works on windows for now
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
  
## Compile and run Manually

_Move to the correct directory_
```bash
cd ProcessJFX
```
**Only once**

*Compile the ProssecFX with the following lines.*

_(windows)_
```powershell
javac -cp ".\bin\;libs\com\json.jar:libs\window\javafx\lib" --module-path .\libs\window\javafx\lib -d bin --add-modules javafx.controls, src/engine/Core.java
javac -cp ".\bin\;libs\com\json.jar:libs\window\javafx\lib" --module-path .\libs\window\javafx\lib --add-modules javafx.controls, preCompiler/precompiler/PreCompiler.java
javac -cp ".\bin\;libs\com\json.jar:libs\window\javafx\lib" --module-path .\libs\window\javafx\lib -d bin --add-modules javafx.controls, preCompiler/precompiler/PreCompiler.java
```
_(linux)_
```bash
javac -cp "bin/:libs/com/json.jar:libs/linux/javafx/lib" --module-path libs/linux/javafx/lib -d bin --add-modules javafx.controls, src/engine/Core.java
javac -cp "bin/:libs/com/json.jar:libs/linux/javafx/lib" --module-path libs/linux/javafx/lib --add-modules javafx.controls, preCompiler/precompiler/PreCompiler.java
javac -cp "bin/:libs/com/json.jar:libs/linux/javafx/lib" --module-path libs/linux/javafx/lib -d bin --add-modules javafx.controls, preCompiler/precompiler/PreCompiler.java
```
**To Run your code you need to follow these 3 step:**

1.
* __For a project called MySketch inside the default sketchBook directory.__
  * _MySketch is the Name of the directory where all your .pjfx files for are._
  
  _(windows)_
```powershell
java -cp ".\bin\;libs\com\json.jar:libs\window\javafx\lib" --module-path .\libs\window\javafx\lib --add-modules javafx.controls, precompiler.PreCompiler --project-name MySketch
```
  _(linux)_
```bash
java -cp "bin/:libs/com/json.jar:libs/linux/javafx/lib" --module-path libs/linux/javafx/lib --add-modules javafx.controls, precompiler.PreCompiler --project-name MySketch
```
* __For a project called MySketch not in the default sketchBook directory.__
  * _A sketchBook is the parent directory of your sketch folder._
  
  _(windows)_
```powershell
java -cp ".\bin\;libs\com\json.jar:libs\window\javafx\lib" --module-path .\libs\window\javafx\lib --add-modules javafx.controls, precompiler.PreCompiler --project-name MySketch -- project-path \path\to\the\other\sketchBook
```
  _(linux)_
```bash
java -cp "bin/:libs/com/json.jar:libs/linux/javafx/lib" --module-path libs/linux/javafx/lib --add-modules javafx.controls, precompiler.PreCompiler --project-name MySketch -- project-path /path/to/the/other/sketchBook
```
2.
* __Compile your Sketch with__

 _(windows)_
```powershell
javac -cp ".\bin\;libs\com\json.jar:libs\window\javafx\lib" --module-path .\libs\window\javafx\lib -d bin --add-modules javafx.controls, .\src\sketch\template\__UserDefault.java
```
 _(linux)_
```bash
javac -cp "bin/:libs/com/json.jar:libs/linux/javafx/lib" --module-path libs/linux/javafx/lib -d bin --add-modules javafx.controls, src/sketch/template/__UserDefault.java
```
3.
* __Run your Sketch with__

 _(windows)_
```powershell
java -cp ".\bin\;libs\com\json.jar:libs\window\javafx\lib" --module-path .\libs\window\javafx\lib --add-modules javafx.controls, engine.Core
```
 _(linux)_
```bash
java -cp "bin/:libs/com/json.jar:libs/linux/javafx/lib" --module-path libs/linux/javafx/lib --add-modules javafx.controls, engine.Core
```



## Auto Compile/Run your sketch _**(windows only)**_

 1. Move to project directory
 2. `java -cp .\bin compiler.Projfx SketchFolderName` 

 To specify the directory in wich your sketch forlder is located add the absolute path from the parent directory of the sketch as second argument.
  
* `java -cp .\bin compiler.Projfx SketchFolderName AbsolutePathToSketchParentFolder` 
