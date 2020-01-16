package precompiler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public final class TinyFileManager {

	public static String path(String first, String ...other) {
		return Paths.get(first, other).toString();
	}
	
	public static String getFilePath(String fileName, String path) {
		Path filePath = Paths.get(path, fileName);
		if(new File(filePath.toString()).exists()){
			return filePath.toString();
		}
		return null;
	}
	
	public static String getTextFileData(String fileName, String path) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(path, fileName), Charset.forName("ISO-8859-1"))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            e.printStackTrace();
            return null;
        }
		
		return sb.toString();
	}
	
	public static boolean newFile(String fileName, String path, String data) {
		System.out.println("making new File at "+ path + " Called " + fileName);		
		Path filePath = Paths.get(path, fileName);
		try {
			
			//System.out.println(filePath);
			new File(filePath.toString()).createNewFile();
			Files.write(filePath, data.getBytes());
			return true;
		} catch (IOException e) {
			System.err.println(filePath);
			e.printStackTrace();
			
			return false;
		}
	}
	
	public static String[] listFile(String path,String extension) {
		Path filePath = Paths.get(path);
		File folder = filePath.toFile();
		/*System.out.println("got path :"+ path);
		System.out.println("Searching for extension : " + extension);
		System.out.println(folder);*/
		String[] pathFound;
		if(folder.isDirectory()) {
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
					return false;
				}
	         });
			return pathFound;
		}
		return null;
		
	}
}
