package engine;

public class Test {
	static String run = "C:\\dev\\openglRelated\\jdk-13.0.1\\bin\\javaw.exe --module-path \"C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib\" --add-modules javafx.controls -Djavafx.animation.fullspeed=true -Dfile.encoding=Cp1252 -classpath \"C:\\dev\\eclipse_workspace\\ProcessJFX\\bin;C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib\\javafx.base.jar;C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib\\javafx.controls.jar;C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib\\javafx.fxml.jar;C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib\\javafx.graphics.jar;C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib\\javafx.media.jar;C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib\\javafx.swing.jar;C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib\\javafx.web.jar;C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib\\javafx-swt.jar\" engine.Core";
	public static void main(String[] args) {
		System.out.println("This is a subProgram");
		System.out.println(run);
	}
}
