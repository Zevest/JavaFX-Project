package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import util.color;

public class PImage {
	WritableImage im;
	private PixelWriter pw;
	private PixelReader pr;
	boolean isLoaded;
	public int width, height;
	public int pixels[];
	
	PImage(String url) {
		Image i = new Image(url);
		im = new WritableImage((int) i.getWidth(),(int)  i.getHeight());
		width = (int) im.getWidth();
		height = (int) im.getHeight();
		pr = i.getPixelReader();
		int[] tmp = new int[width*height];
		pr.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), tmp, 0, width);
		pw = im.getPixelWriter();
		pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), tmp, 0, width);
	}
	
	PImage(int w, int h, int[] buffer){
		im = new WritableImage(w,h);
		pw = im.getPixelWriter();
		pw.setPixels(0, 0, w, h, PixelFormat.getIntArgbInstance(), buffer, 0, w);
		width = (int) im.getWidth();
		height = (int) im.getHeight();
		pr = im.getPixelReader();
	}
	
	public void loadPixels() {
		if(!isLoaded) {
			pixels = new int[width*height];
			pr = im.getPixelReader();
		}
		pr.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
		isLoaded = true;
	}
	
	public void updatePixels() {
		if(isLoaded) {
			pw = im.getPixelWriter();
			pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
		}
		isLoaded = false;
	}
	
	public void set(int x, int y, int col) {
		if(!isLoaded) {
			loadPixels();//pw = im.getPixelWriter();
		}
		if(x < width && x >= 0 && y < height && y >= 0)
			pixels[y*width+x] = col;//pw.setArgb(x, y, col);
	}
	
	public void set(int x, int y, color col) {
		if(!isLoaded) {
			loadPixels();//pw = im.getPixelWriter();
		}
		if(x < width && x >= 0 && y < height && y >= 0)
			pixels[y*width+x] = col.getArgb();//pw.setArgb(x, y, col);
	}
	
	public void set(int x, int y, PImage pimg) {
		if(!isLoaded) {
			pw = im.getPixelWriter();
		}
		
		int[] tmp = new int[pimg.width * pimg.height];
		pimg.pr.getPixels(0, 0, pimg.width, pimg.height, PixelFormat.getIntArgbInstance(), tmp, 0, pimg.width);
		pw.setPixels(x, y, width, height, PixelFormat.getIntArgbInstance(), tmp, 0, pimg.width);
	}
	
	public int get(int x, int y) {
		if(x < width && x >= 0 && y < height && y >= 0)
			//return pr.getArgb(x, y);
			return pixels[y*width+x];
		return 0xff000000;
		
	}
	
	
	public PImage get(int x, int y, int w, int h) {
		int[] tmp = new int[w*h];
		pr.getPixels(x, y, (int)Math.min(w,im.getWidth()-x), (int)Math.min(h,im.getHeight()-y), PixelFormat.getIntArgbInstance(), tmp, 0, w);
		return new PImage(w,h, tmp);
		
	}
	
	public void save(String path) {
		File f;
		String name;
		if (path.indexOf(File.pathSeparator) >= 0){
			f = new File(path);
			name = Paths.get(path).getFileName().toString();
		}else {
			Path p = Paths.get(Core.dirPath, Core.projectName, "data", path);
			name = p.getFileName().toString();
			f = new File(p.toString());
			f.mkdirs();
		}
		BufferedImage bf = SwingFXUtils.fromFXImage(im, null);
		String[] word =name.split("."); 
		String ext = word[word.length-1];
		try {
			ImageIO.write(bf, ext, f);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
