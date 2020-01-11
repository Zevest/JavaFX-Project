package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class Main {
	
	static Process[] processList = new Process[10];
	static int pid = 0;
	
	public static String folderName;
	/*
	public static String getAllJars(){
		//String str = Paths.get(System.getProperty("user.dir")).toString();
		String str = Paths.get("C:", "dev", "libs","java",  "openjfx-13.0.1_windows-x64_bin-sdk", "javafx-sdk-13.0.1", "lib").toString();
		//System.out.println(str);
		File file = new File(str);
		
		
		StringBuilder sb = new StringBuilder();
	 
		File[] arr = file.listFiles();
		for(File f: arr){
			if(f.getName().endsWith(".jar")){
				sb.append(f.getAbsolutePath() + ":");
			}
		}
	 
		String s = sb.toString();
		s = s.substring(0, s.length()-1);
	 
		//System.out.println(s);
		return s;
	}
	*/
	public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
            	System.out.println("Killing all Processus");
                Main.destroyAllProcess();	
                
            }
        });
		
        String projectPath = Paths.get(System.getProperty("user.dir"),"sketchBooks").toString();//"C:\\dev\\pjfx";//
        String projectName = "project3";//"Test";
        
		String file = Paths.get("sketch", "template", "__UserDefault.java").toString();
		String engine = "";//Paths.get("engine", "COre.java").toString();
//		String file = Paths.get("src","sketch", "template", "__UserDefault.java").toString();
//	    TODO: Change to a dynamic or platform independant path
		String modulePath = "C:\\dev\\libs\\java\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib"; 
		String buildPath = Paths.get(System.getProperty("user.dir")).toString();
		
		String srcPath = Paths.get(System.getProperty("user.dir"), "src").toString();
//		String srcPath = Paths.get(System.getProperty("user.dir")).toString();
		
		String preCompileLine = "java -cp \"" + buildPath + "\" precompiler.PreCompiler";
//		String preCompileLine = "java precompiler.PreCompiler";
		String preCompileArgs = "--project-path \"" + projectPath + "\" --project-name " + projectName;
		String compile = "javac --module-path \""+modulePath+ "\" --add-modules javafx.controls " + file + " " + engine;
		String runLine = "java -XX:+UseParallelGC -Xms1536m -Xmx1536m -XX:NewRatio=2 --module-path \""+modulePath+"\" --add-modules javafx.controls -Djavafx.animation.fullspeed=true -Dfile.encoding=Cp1252 engine.Core";
		
		 
		
		System.out.println(preCompileArgs);
		try {
			
			/*if(runProcess("pwd", "CMD.exe /C %cd%", srcPath) != 0) {
				destroyAllProcess();
				return;
			}*/
			
			if(runProcess("Pre-compiler", new String[] {preCompileLine, preCompileArgs}, srcPath) != 0) {
				destroyAllProcess();
				return;
			}
			if(runProcess("Compiler", compile, srcPath) != 0) {
				destroyAllProcess();
				return;
			}
			if(runProcess("Running", runLine, srcPath) != 0) {
				destroyAllProcess();
				return;
			}

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private static void destroyAllProcess() {
		for(int i = 0; i  < pid; ++i) {
			processList[i].destroy();
		}
	}
	
	private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
            new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
    }
	
	private static void printErrors(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(	
            new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.err.println(cmd + " " + line);
        }
    } 
	
	public static int runProcess(String command) throws Exception {
		Process pro = Runtime.getRuntime().exec(command);
		processList[pid++] = pro;
        printLines(command + " stdout:", pro.getInputStream());
        printErrors(command + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(command + " exited with " + pro.exitValue());
        else
        	System.out.println(command + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String[] command) throws Exception {
		Process pro = Runtime.getRuntime().exec(command);
		processList[pid++] = pro;
        printLines(command + " stdout:", pro.getInputStream());
        printErrors(command + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(command + " exited with " + pro.exitValue());
        else
        	System.out.println(command + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String command, String dir) throws Exception {
		Process pro = Runtime.getRuntime().exec(command, null, new File(dir));
		processList[pid++] = pro;
        printLines(command + " stdout:", pro.getInputStream());
        printErrors(command + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(command + " exited with " + pro.exitValue());
        else
        	System.out.println(command + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String name, String command, String dir) throws Exception {
		Process pro = Runtime.getRuntime().exec(command, null, new File(dir));
		processList[pid++] = pro;
        printLines(name + " stdout:", pro.getInputStream());
        printErrors(name + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(name + " exited with " + pro.exitValue());
        else
        	System.out.println(name + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String name, String[] command, String dir) throws Exception {
		String finalCommand = "";
		for(int i = 0; i < command.length; ++i) {
			finalCommand += command[i] + " ";
		}	
		
		Process pro = Runtime.getRuntime().exec(finalCommand, null, new File(dir));
		processList[pid++] = pro;
        printLines(name + " stdout:", pro.getInputStream());
        printErrors(name + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)
        	System.err.println(name + " exited with " + pro.exitValue());
        else
        	System.out.println(name + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
	
	public static int runProcess(String[] command, String dir) throws Exception {
		String finalCommand = "";
		for(int i = 0; i < command.length; ++i) {
			finalCommand += command[i] + " ";
		}
		
		Process pro = Runtime.getRuntime().exec(finalCommand, null, new File(dir));
		processList[pid++] = pro;
        printLines(finalCommand + " stdout:", pro.getInputStream());
        printErrors(finalCommand + " stderr", pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0)	
        	System.err.println(finalCommand + " exited with " + pro.exitValue());
        else
        	System.out.println(finalCommand + " exited with " + pro.exitValue());
        return pro.exitValue();
	}
}
