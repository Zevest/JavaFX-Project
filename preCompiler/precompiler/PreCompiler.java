package precompiler;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreCompiler {
	String pakageName = "package sketch.template";
	String[] importList = { 
			"import constant.CURSOR",
			"import constant.SETTINGS",
			"import util.PVector",
			"import util.color",
			"import util.PShape",
			"import engine.Sketch",
			"import java.util.ArrayList",
			"import javafx.scene.image.Image",
			"import java.util.HashMap",
			"import javafx.scene.input.KeyCode",
			"import java.io.BufferedReader",
			"import engine.JSONObject",
			"import engine.JSONArray",
			"import engine.PImage"
	};
	
	String className = "__UserDefault";
	String sketchFileName;
	String classScope = "public class";
	String classExtension = "extends Sketch";
	
	StringBuilder importation,fileContent,output;
	String directory = "";
	static final String srcDir = Paths.get(System.getProperty("user.dir")).toString();
	static final String buildDir = TinyFileManager.path(srcDir,"sketch", "template");
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
			projectName = "Clock";
		fullPath = TinyFileManager.path(dirPath, projectName);

		// System.out.println(dirPath + "\n" + buildDir + "\n" + srcDir + "\n" +
		// projectName +"\n" + fullPath + "\n");
		//System.out.println(fullPath);
		new PreCompiler(TinyFileManager.listFile(fullPath, "pjfx"), fullPath);
		
	}

	public PreCompiler() {
		sketchFileName = "Sketch" + (int) (Math.random() * 1000000);
		importation = new StringBuilder();
		fileContent = new StringBuilder();
		output = new StringBuilder();
		buildHeader();
		startFile();
		closeFile();
		buildFile();
		saveFile();
	}

	public PreCompiler(String[] sketchNames, String dir) {
		System.out.println("Test3");
		if (sketchNames == null || sketchNames.length == 0)
			return;
		importation = new StringBuilder();
		fileContent = new StringBuilder();
		output = new StringBuilder();
		
		buildHeader();
		startFile();
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
		buildFile();
		saveFile();
	}

	public PreCompiler(String sketchName) {
		sketchFileName = sketchName;
		importation = new StringBuilder();
		fileContent = new StringBuilder();
		output = new StringBuilder();
		buildHeader();
		startFile();
		if (!getFileContent(sketchFileName + fileExtension)) {
			System.err.println("Error: File " + sketchFileName + fileExtension + " not Found");
			System.exit(-1);
		}
		closeFile();
		buildFile();
		saveFile();
	}

	public PreCompiler(String sketchName, String dir) {
		sketchFileName = sketchName;
		importation = new StringBuilder();
		fileContent = new StringBuilder();
		output = new StringBuilder();
		buildHeader();
		startFile();
		getSaveDirectory(dir);
		if (!getFileContent(sketchFileName + fileExtension)) {
			System.err.println("Error: File " + sketchFileName + fileExtension + " not Found in " + directory);
			// System.out.println("End2");
			System.exit(-1);
		}
		closeFile();
		buildFile();
		saveFile();
	}

	private void buildFile() {
		output.append(importation);
		output.append(fileContent);
	}

	private void getSaveDirectory(String dir) {
		directory = TinyFileManager.path(dir);
		// System.out.println(directory);
	}

	private boolean getFileContent(String fileName) {
		System.out.println(TinyFileManager.path(directory, fileName));
		String t = TinyFileManager.getTextFileData(fileName, directory);
		if (t == null)
			return false;
		for(String line: t.split("\\r?\\n")) {	
			if(line.indexOf("import")>=0) {
				for(String instruction: line.split(";")){
					//System.out.println(instruction);
					importation.append(instruction);
					importation.append(";\n");
				}
			} else {
				//System.out.println(line);
				
				
				//String nLine = line.replaceAll("((?:[-+]?)(?:[0-9]+)(?:.{1})(?:[0-9]+)(?:[eE]?[+-]?[0-9]+)?)","(?!f)");
				//String nLine;
				//nLine = regInsertAll("([-]?([0-9]*)\\.([0-9]+)([^A-Za-z]))", line, "f", false);
				//nLine = regInsertAll("[^A-Za-z0-9](int\\()", line, "_", true);
				//nLine = regInsertAll("[^A-Za-z0-9](float\\()", nLine, "_", true);
				
				//System.out.println(line);
				//System.out.println(nLine);
				
				fileContent.append(line);
				fileContent.append('\n');
			}
			
		}
		return true;
	}

	private void closeFile() {
		fileContent.append('}');

	}

	public void buildHeader() {
		importation.append(pakageName);
		importation.append(";\n");
		for (String importLine : importList) {
			importation.append(importLine);
			importation.append(";\n");
		}
	}
	
	public void startFile() {
		fileContent.append(classScope);
		fileContent.append(' ');
		fileContent.append(className);
		fileContent.append(' ');
		fileContent.append(classExtension);
		fileContent.append("{\n");
	}
	
	public void saveFile() {
		String srcFileName = className + ".java";
		// String fullPath = TinyFileManager.path(srcDir, buildDir)
		System.out.println("output >> " + TinyFileManager.path(buildDir, srcFileName) + "\n");// + output.toString());
		TinyFileManager.newFile(srcFileName, buildDir, output.toString());
	}
	/*
	public static int indexOf(String Regex, String s) {
	    Matcher matcher = Pattern.compile(Regex).matcher(s);
	    return matcher.find() ? matcher.start() : -1;
	}
	
	public static int indexOf(String Regex, String s, boolean end) {
	    Matcher matcher = Pattern.compile(Regex).matcher(s);
	    return matcher.find() ? (end ? matcher.end() :matcher.start()) : -1;
	}
	
	public static String SubString(String regex, String s) {
		int start = indexOf(regex, s, false);
		int end = indexOf(regex, s, true);
		if(start >= 0 && end >= 0)
			return s.substring(start, end);
		return null;
		
	}
	
	public static String regInsert(String regex, String s,  String ext, boolean before) {
		int index = indexOf(regex, s, !before);
		System.out.println(index);
		if(index < 0)
			return null;
		StringBuilder sb = new StringBuilder();
		String start = s.substring(0, index+1);
		String end = s.substring(index+1);

		System.out.println("this " + start + " " + ext + " " + end);
		sb.append(start);
		sb.append(ext);
		sb.append(end);
		return sb.toString();
	}
	
	public static String regInsertAll(String regex, String s,  String ext, boolean before) {
		String res = s;
		while(res != null) {
			s = res;
			res = regInsert(regex, res, ext, before);
		}
		return s;
	}
*/
}
