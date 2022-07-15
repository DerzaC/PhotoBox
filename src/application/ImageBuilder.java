package application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.awt.AlphaComposite;
import java.awt.image.*;
import javax.imageio.ImageIO;

import application.Deamon.Task;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageBuilder {
	private Settings settings = Main.settings;
	private String fileName;
	private int filenum;
	
	public ImageBuilder(String fileName,int filenum) {
		this.fileName=fileName;
		this.filenum=filenum;
		source = new File(settings.dir+"\\"+fileName);
		watermark = new File(settings.watermarkLoc);
		destination = new File(settings.backupWatermarkedDir+"\\"+filenum+"Marked"+"."+settings.extension);
	}
	

	
	public boolean mark(){
		try {
			addImageWatermark();
			return true;
		} catch (IOException e) {
			System.out.println(settings.dir+"\\"+fileName);
			System.out.println(source);
			Main.main.addToLog("ERROR: exec method addImageWatermark() failed");
			e.printStackTrace();
			return false;
		}	
	}
	
	

	  private File source;// = new File(settings.dir+"\\"+fileName);
      private File watermark;// = new File(settings.watermarkLoc);
      private File destination;// = new File(settings.backupWatermarkedDir+fileName+"Marked");
	
	
      public boolean backup() { 	
    	  boolean ret = false;
    	  Path copied = Paths.get(settings.backupDir+"\\"+filenum+"v"+"."+settings.extension);
    	  Path originalPath = Paths.get(settings.dir+"\\"+fileName);
    	  try {
    		  Files.copy(originalPath, copied, StandardCopyOption.COPY_ATTRIBUTES);
    		  ret=true;
    	  } catch (IOException e) {
    		  Main.main.addToLog("Backup "+fileName+" failed");
    		  e.printStackTrace();
    	  }  
    	  return ret;
      }
      
      
	private void addImageWatermark() throws IOException {
        String type = "png";
		BufferedImage image = ImageIO.read(source);
	
        
        BufferedImage overlay = resize(ImageIO.read(watermark),ImageIO.read(watermark).getHeight(),ImageIO.read(watermark).getWidth());

        // determine image type and handle correct transparency
        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

        // initializes necessary graphic properties
        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(image, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) settings.watermarkOpacity);
       
        w.setComposite(alphaChannel);

        // calculates the coordinate where the String is painted
        int centerX = settings.watermarkPos[0];
        int centerY = settings.watermarkPos[1];

        // add watermark to the image
        w.drawImage(overlay, centerX, centerY, null);
        ImageIO.write(watermarked, type, destination);
        w.dispose();
    }

    private BufferedImage resize(BufferedImage img, int height, int width) {
    	height *= settings.watermarkSizePercentage;
    	width *= settings.watermarkSizePercentage;
        Image tmp = img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }


}
