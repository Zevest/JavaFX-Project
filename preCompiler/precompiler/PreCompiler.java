package precompiler;

import java.nio.file.Paths;
import precompiler.TinyFileManager;

public class PreCompiler {
	String pakageName = "package sketch.template";
	String[] importation = { "import constant.CURSOR", "import constant.SETTINGS", "import util.PVector",
			"import util.color", "import engine.Sketch", "import java.util.ArrayList",
			"import javafx.scene.image.Image",
			"import java.util.HashMap",
			"import javafx.scene.input.KeyCode"
	};
	String className = "__UserDefault";
	String sketchFileName;
	String classScope = "public class";
	String classExtension = "extends Sketch";

	String fileContent = "";
	String directory = "";
	static final String srcDir = Paths.get(System.getProperty("user.dir")).toString();
	static final String buildDir = TinyFileManager.path(srcDir, "sketch", "template");
	static final String fileExtension = ".pjfx";
	static String projectName = null;
	static String dirPath = TinyFileManager.path(srcDir, "sketchBooks");
	static String fullPath;

	//static  final String[] flags = { "--project-path", "--project-name" };

	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			System.out.println(args[0]);
			for (int i = 0; i < args.length; ++i) {
				switch (args[i]) {
				case "--project-path":
					dirPath = args[++i];
					break;
				case "--project-name":
					projectName = args[++i];
					break;
				default:
					System.out.println("argument " + args[i] + " ignored");
					break;
				}
			}

		}
		if (projectName == null)
			projectName = "project1";
		fullPath = TinyFileManager.path(dirPath, projectName);

		// System.out.println(dirPath + "\n" + buildDir + "\n" + srcDir + "\n" +
		// projectName +"\n" + fullPath + "\n");
		new PreCompiler(TinyFileManager.listFile(fullPath, "pjfx"), fullPath);
	}

	public PreCompiler() {
		sketchFileName = "Sketch" + (int) (Math.random() * 1000000);
		buildHeader();
		closeFile();
		saveFile();
		// System.out.println(fileContent);
	}

	public PreCompiler(String[] sketchNames, String dir) {
		if (sketchNames == null || sketchNames.length == 0)
			return;
		buildHeader();
		getSaveDirectory(dir);
		// System.out.println(sketchNames);

		for (String sketchName : sketchNames) {
			System.out.println(sketchName);
			sketchFileName = sketchName;
			if (!getFileContent(sketchFileName)) {
				System.err.println("Error: File " + sketchFileName + " not Found");
				System.exit(-1);
			}
		}
		closeFile();
		saveFile();
	}

	public PreCompiler(String sketchName) {
		sketchFileName = sketchName;
		buildHeader();
		if (!getFileContent(sketchFileName + fileExtension)) {
			System.err.println("Error: File " + sketchFileName + fileExtension + " not Found");
			System.exit(-1);
		}
		closeFile();
		saveFile();
	}

	public PreCompiler(String sketchName, String dir) {
		sketchFileName = sketchName;
		buildHeader();
		getSaveDirectory(dir);
		if (!getFileContent(sketchFileName + fileExtension)) {
			System.err.println("Error: File " + sketchFileName + fileExtension + " not Found in " + directory);
			// System.out.println("End2");
			System.exit(-1);
		}
		closeFile();
		saveFile();
	}

	private void getSaveDirectory(String dir) {
		// TODO Auto-generated method stub
		directory = TinyFileManager.path(dir);
		// System.out.println(directory);
	}

	private boolean getFileContent(String fileName) {
		System.out.println(TinyFileManager.path(directory, fileName));
		String t = TinyFileManager.getTextFileData(fileName, directory);
		if (t == null)
			return false;
		fileContent += t;
		return true;
	}

	private void closeFile() {
		fileContent += "}";

	}

	public void buildHeader() {
		fileContent += pakageName + ";\n";
		for (String importLine : importation) {
			fileContent += importLine + ";\n";
		}
		fileContent += classScope + " " + className + " " + classExtension + " {\n";
	}

	public void saveFile() {
		String srcFileName = className + ".java";
		// String fullPath = TinyFileManager.path(srcDir, buildDir)
		System.out.println("output >> " + TinyFileManager.path(buildDir, srcFileName) + "\n" + fileContent);
		TinyFileManager.newFile(srcFileName, buildDir, fileContent);
	}
}
