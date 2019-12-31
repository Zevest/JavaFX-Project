package sketch.template;

import util.FileManager;
public class SketchTemplate{
	String pakageName = "package sketch.template";
	String[] importation = {
			"import constant.CURSOR",
			"import constant.SETTINGS",
			"import util.PVector",
			"import util.color",
			"import engine.Sketch"
	};
	String className = "__UserDefault";
	String sketchFileName;
	String classScope = "public class";
	String classExtension = "extends Sketch";
	
	String fileContent = "";
	String directory = "";
	static final String srcDir = FileManager.path(System.getProperty("user.dir"),"src");
	static final String buildDir = FileManager.path(srcDir,"sketch", "template");
	static final String fileExtension = ".pjfx" ;
	
	public SketchTemplate(){
		sketchFileName = "Sketch" + (int) (Math.random() * 1000000);
		buildHeader();
		closeFile();
		saveFile();
		System.out.println(fileContent);
	}
	
	public SketchTemplate(String[] sketchNames, String dir){
		
		buildHeader();
		getSaveDirectory(dir);
		for(String sketchName: sketchNames) {
			sketchFileName = sketchName;
			if(!getFileContent(sketchFileName)){
				System.err.println("Error: File " + sketchFileName + " not Found");
				System.exit(-1);
			}
		}
		closeFile();
		saveFile();
	}
	
	public SketchTemplate(String sketchName){
		sketchFileName = sketchName;
		buildHeader();
		if(!getFileContent(sketchFileName + fileExtension)){
			System.err.println("Error: File " + sketchFileName + fileExtension + " not Found");
			System.exit(-1);
		}
		closeFile();
		saveFile();
	}
	
	public SketchTemplate(String sketchName, String dir){
		sketchFileName = sketchName;
		buildHeader();
		getSaveDirectory(dir);
		if(!getFileContent(sketchFileName + fileExtension)){
			System.err.println("Error: File " + sketchFileName + fileExtension + " not Found in " + directory );
			//System.out.println("End2");
			System.exit(-1);
		}		
		closeFile();
		saveFile();
	}
	
	private void getSaveDirectory(String dir) {
		// TODO Auto-generated method stub
		directory = FileManager.path(dir);
		System.out.println(directory);
	}

	private boolean getFileContent(String fileName) {
		System.out.println(FileManager.path(directory, fileName));
		String t = FileManager.getTextFileData(fileName, directory);
		if(t == null)
			return false;
		fileContent += t;
		return true;
	}
	private void closeFile() {
		fileContent += "}";
		
	}
	
	public void buildHeader() {
		fileContent += pakageName +";\n";
		 for (String importLine: importation) {
			 fileContent += importLine + ";\n";
		 }
		 fileContent += classScope + " " + className + " " + classExtension + " {\n";
	}
	
	public void saveFile() {
		String srcFileName = className + ".java";
		//String fullPath  =  FileManager.path(srcDir, buildDir)
		System.out.println("output >> " + FileManager.path(buildDir, srcFileName) + "\n" + fileContent);
		FileManager.newFile(srcFileName, buildDir, fileContent);
	}
}
