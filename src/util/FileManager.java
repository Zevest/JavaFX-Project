package util;

import java.io.File;
import java.nio.file.Path;


public final class FileManager {
	public static String dataFolderPath;
	
	
	public static int init(String path){
		dataFolderPath = path;
		new File(dataFolderPath).mkdir();
		return 0;
	}
	
	public static int init() {
		dataFolderPath = "ressources";
		new File(dataFolderPath).mkdir();
		return 0;
	}
	
	public static String getFolderPath() {
		return Path.of(System.getProperty("user.dir"), dataFolderPath).toString();
	}
	
	public static String getFolderUrl() {
		return Path.of(System.getProperty("user.dir"), dataFolderPath).toUri().toString();
	}
	
	public static String getFilePath(String fileName) {
		Path filePath = Path.of(System.getProperty("user.dir"), dataFolderPath, fileName);
		if(new File(filePath.toString()).exists())
			return filePath.toString();
		return null;
	}
	
	public static String getFileUrl(String fileName) {
		Path filePath = Path.of(System.getProperty("user.dir"), dataFolderPath, fileName);
		if(new File(filePath.toString()).exists())
			return filePath.toUri().toString();
		return null;
	}
}
