package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public final class FileManager {
	public static String dataFolderPath;
	
	public static boolean init(String path){
		dataFolderPath = path;
		return  new File(dataFolderPath).mkdir();
	}
	
	public static boolean init() {
		dataFolderPath = "ressources";
		return new File(dataFolderPath).mkdir();
	}
	
	public static String path(String first, String ...other) {
		return Paths.get(first, other).toString();
	}
	
	public static BufferedReader createReader(String fileName) {
		Path path = Paths.get(dataFolderPath, fileName);
		BufferedReader br;
		try {
			br = Files.newBufferedReader(path);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            return null;
        }
		return br;
	}
	
	public static String getFolderPath() {
		return Paths.get(dataFolderPath).toString();
	}
	
	public static String getFolderUrl() {
		return Paths.get(dataFolderPath).toUri().toString();
	}
	
	public static String getFilePath(String fileName) {
		Path filePath = Paths.get(dataFolderPath, fileName);
		if(new File(filePath.toString()).exists())
			return filePath.toString();
		return null;
	}
	
	public static String getFilePath(String fileName, String path) {
		Path filePath = Paths.get(path, fileName);
		if(new File(filePath.toString()).exists()){
			return filePath.toString();
		}
		return null;
	}
	
	public static String getFileUrl(String fileName) {
		Path filePath = Paths.get(dataFolderPath, fileName);
		if(new File(filePath.toString()).exists())
			return filePath.toUri().toString();
		return null;
	}
	
	public static String getFileUrl(String fileName, String path) {
		Path filePath = Paths.get(path, fileName);
		if(new File(filePath.toString()).exists())
			return filePath.toUri().toString();
		return null;
	}
	
	public static String getTextFileData(String fileName, String path) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(path, fileName))) {

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
		Path filePath = Paths.get(path, fileName);
		try {
			new File(filePath.toString()).createNewFile();
			Files.write(filePath, data.getBytes());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String[] listFile(String path) {
		Path filePath = Paths.get(path);
		File folder = filePath.toFile();
		String[] pathFound;
		if(folder.isDirectory()) {
			pathFound = folder.list();
			return pathFound;
		} 
		return null;
		
	}
	
	public static String[] listFile(String path,String extension) {
		Path filePath = Paths.get(path);
		File folder = filePath.toFile();
		System.out.println("got path :"+ path);
		System.out.println("Searching for extension : " + extension);
		System.out.println(folder);
		String[] pathFound;
		if(folder.isDirectory()) {
			System.out.println("Turz");
			pathFound = folder.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					System.out.println(""+dir + " " + name);
					if(name.lastIndexOf('.')>0) {
						int lastIndex = name.lastIndexOf('.');
						String str = name.substring(lastIndex);
						if(str.equals("." + extension)) 
							return true;
					}
					System.out.println("false");
					return false;
				}
	         });
			return pathFound;
		}
		System.out.println("False 156165");
		return null;
		
	}
}
