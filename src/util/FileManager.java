package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
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
	
	public static String path(String first, String ...other) {
		return Path.of(first, other).toString();
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
	
	public static String getFilePath(String fileName, String path) {
		Path filePath = Path.of(path, fileName);
		if(new File(filePath.toString()).exists()){
			return filePath.toString();
		}
		return null;
	}
	
	public static String getFileUrl(String fileName) {
		Path filePath = Path.of(System.getProperty("user.dir"), dataFolderPath, fileName);
		if(new File(filePath.toString()).exists())
			return filePath.toUri().toString();
		return null;
	}
	
	public static String getFileUrl(String fileName, String path) {
		Path filePath = Path.of(System.getProperty("user.dir"), path, fileName);
		if(new File(filePath.toString()).exists())
			return filePath.toUri().toString();
		return null;
	}
	
	public static String getTextFileData(String fileName, String path) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Path.of(path, fileName))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            return null;
        }
		
		return sb.toString();
	}
	
	public static boolean newFile(String fileName, String path, String data) {
		Path filePath = Path.of(path, fileName);
		
		try {
			System.out.println(filePath);
			new File(filePath.toString()).createNewFile();
			Files.write(filePath, data.getBytes());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String[] listFile(String path) {
		Path filePath = Path.of(path);
		File folder = filePath.toFile();
		String[] pathFound;
		if(folder.isDirectory()) {
			pathFound = folder.list();
			return pathFound;
		} 
		return null;
		
	}
	
	public static String[] listFile(String path,String extension) {
		Path filePath = Path.of(path);
		File folder = filePath.toFile();
		String[] pathFound;
		if(folder.isDirectory()) {
			pathFound = folder.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.lastIndexOf('.')>0) {
						int lastIndex = name.lastIndexOf('.');
						String str = name.substring(lastIndex);
						if(str.equals("." + extension)) 
							return true;
					}
					return false;
				}
	         });
			return pathFound;
		} 
		return null;
		
	}
}
