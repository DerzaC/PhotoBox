package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Settings {
	//-------------------------------------
	public String Temp= "";
	
	
	
	
	
	//-----------------------------------------Primary
	public String dir="-";
	public String extension="";
	public String watermarkLoc="";
	public String targetImage="";
	public double watermarkSizePercentage=1;
	public int[] watermarkPos= new int[] {0,0};
	public double watermarkOpacity;
	private UserInterface ui = Main.main.ui;
	
	public String[][] getPrimarySettings(){
		int entrys  =7;
		String[] A= getExample();
		String[][]tmp=new String[][]{ 
			getDirectory(),	
			getObservedExtension(),
			getWatermarkLocation(),
			getWatermarkSize(),
			getWatermarkPos(),
			getWatermarkOpacity()
		};
		return tmp;
		
	}

	public String[] getExample() {
		String infoText = "current dir:";
		String currentSettings= "c:\\temp_unso";
		String eventID= "ObservedDir";
		return new String[] {infoText,currentSettings,eventID};
	}
	
	private String[] getDirectory() {
		String infoText = "Observed Directory:";
		String currentSettings= dir;
		String eventID= "observedDir";
		return new String[] {infoText,currentSettings,eventID};
	}
	
	private String[] getObservedExtension() {
		String infoText = "Filextension ";
		String currentSettings= extension;
		String eventID= "extension";
		return new String[] {infoText,currentSettings,eventID};
	}
	
	private String[] getWatermarkLocation() {
		String infoText = "Watermark Location(requires: png with transparent background)";
		String currentSettings= watermarkLoc;
		String eventID= "WMLocation";
		return new String[] {infoText,currentSettings,eventID};
	}
	
	private String[] getWatermarkSize() {
		String infoText = "Watermark Size";
		String currentSettings= ""+(100*watermarkSizePercentage)+"%";
		String eventID= "WMSize";
		return new String[] {infoText,currentSettings,eventID};
	}

	private String[] getWatermarkPos() {
		String infoText = "Watermark position:";
		String currentSettings= "X: "+watermarkPos[0]+" Y: "+watermarkPos[1];
		String eventID= "WMPos";
		return new String[] {infoText,currentSettings,eventID};
	}
	
	private String[] getWatermarkOpacity() {
		String infoText = "watermark Opacity (0-100%)";
		String currentSettings= ""+((1-watermarkOpacity)*100)+"%";
		String eventID= "WMOpacity";
		return new String[] {infoText,currentSettings,eventID};
	}
	//-------------------------------------------------ui------------------
	private String genChooseDirectory() {                                                  //---------------dir
		Stage stage = new Stage();
		DirectoryChooser fileChooser=new DirectoryChooser();
		fileChooser.setTitle("Open Resource File");
		File tmp=fileChooser.showDialog(stage);
		return (tmp!=null)?tmp.toString():null;		
	}
	
	public void chooseObservedDir() {
		String old = this.dir;
		String tmp = genChooseDirectory();
		if(tmp != null) {
			this.dir=tmp;
			Main.main.addToLog("Observed Directory changed from "+old+" to: "+dir);
		}
	}
	
	private String genChooseFile() {                                                       //--------------file
		Stage stage = new Stage();
		FileChooser fileChooser=new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File tmp = fileChooser.showOpenDialog(stage);
		return (tmp!=null)?tmp.toString():null;	
	}
	
	public void chooseWatermark() {
		String old = this.watermarkLoc;
		String tmp =genChooseFile();
		if(tmp != null) {
			this.watermarkLoc=tmp;
			Main.main.addToLog("Watermark Location changed from "+old+" to: "+watermarkLoc);
		}
	}
	
	private String genDropdownPopup(String infotxt,String menueDescription,String[] options) {//-------------dropdown
		ChoiceDialog<String> dialog = new ChoiceDialog<>(options[0], options);
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText(infotxt);
		dialog.setContentText(menueDescription);
		Optional<String> result = dialog.showAndWait();
		String tmp = "null";
		if (result.isPresent()){
		    tmp = result.get();
		}else {
			Main.main.addToLog("Choice Dialog closed");
		}		
		return tmp;
	}
	
	public void chooseExtension() {
		String old = this.extension;
		String tmp = genDropdownPopup(
				"File Extension",
				"choose",
		new String[] {"jpg","png","bmp","gif"}	
		);
		if(!(tmp=="null")) {
			this.extension=tmp;
			Main.main.addToLog("File Extension changed from "+old+" to: "+watermarkLoc);
		}
	}
	
	private String genInputPopup(String infotxt,String menueDescription) { //  --------------inputDiag
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Input Dialog");
		dialog.setHeaderText(infotxt);
		dialog.setContentText(menueDescription);
		Optional<String> result = dialog.showAndWait();
		String tmp = "null";
		if (result.isPresent()){
		    tmp = result.get();
		}else {
			Main.main.addToLog("Input Dialog closed");
		}
		return tmp;
	}
	
	public void chooseWMOpacity() {
		double old = this.watermarkOpacity;
		boolean conditions=true;
		String failed = "";
		do {
			conditions=true;
			String tmp = genInputPopup(
					"Choose Watermark opacity. (range between 0-100%)\n"+failed,
					"enter Value");	
			if(!(tmp=="null")) {
				try {
					double temp =((100-Double.parseDouble(tmp))/100);
					if(temp>=0 && temp<=1) {
						this.watermarkOpacity=temp;
					}else {
						failed="ERROR: invalid Range";
						conditions=false;
					}
					Main.main.addToLog("WatermarkOpacity changed from "+((1-old)*100)+" to: "+((1-watermarkOpacity)*100));
				}catch(Exception e) {
					failed="ERROR: invalid Value";
					conditions=false;
				}
			}
		}while(!conditions);				
	}
	
	public void chooseWMSize() {
		double old = this.watermarkSizePercentage;
		boolean conditions=true;
		String failed = "";
		do {
			conditions=true;
			String tmp = genInputPopup(
					"Choose Watermark size.\n"+failed,
					"enter Value");	
			if(!(tmp=="null")) {
				try {
					double temp =(Double.parseDouble(tmp)/100);
					this.watermarkSizePercentage=temp;
					Main.main.addToLog("watermark size changed from "+(old*100)+" to: "+(watermarkSizePercentage*100));
				}catch(Exception e) {
					failed="ERROR: invalid Value";
					conditions=false;
				}
			}
		}while(!conditions);				
	}
	
	public void chooseWMlocation() {
		Preview perev = new Preview();
	}
	
	
	//---------------------------------------------------Printer --- placeholder
	private String dir1="-";
	private String extension1="";
	private String watermarkLoc1="";
	
	public String[][] getPrintSettings(){
		int entrys  =7;
		String[] A= getExample();
		String[][]tmp=new String[][]{ 
			getDirectory1(),	
			getObservedExtension1(),
			getWatermarkLocation1(),
		};
		return tmp;
		
	}

	private String[] getDirectory1() {
		String infoText = "Observed Directory:";
		String currentSettings= dir;
		String eventID= "observedDir";
		return new String[] {infoText,currentSettings,eventID};
	}
	
	private String[] getObservedExtension1() {
		String infoText = "Filextension ";
		String currentSettings= extension;
		String eventID= "extension";
		return new String[] {infoText,currentSettings,eventID};
	}
	
	private String[] getWatermarkLocation1() {
		String infoText = "Watermark Location";
		String currentSettings= watermarkLoc;
		String eventID= "WMLocation";
		return new String[] {infoText,currentSettings,eventID};
	}
	
	//------------------preview------------------//
	class  Preview{
		Stage stage = new Stage();
		AnchorPane prev;
		double width = 1000;
		double height = 600;
		double dinA4 = 210.0/297.0;
		static boolean panorama = true;
		double heightBorder = 100;
		 
				
		private Group paperFrame() {			
			Group paper = new Group();
			Rectangle background = new Rectangle(height-heightBorder,height-heightBorder);
			background.setFill(new Color(0,0,0,0.5));
			double bgx=width-height;
			background.setLayoutX(bgx);
			double bgy=heightBorder/2;
			background.setLayoutY(bgy);
			double pwidth= 	panorama?height-heightBorder:(height-heightBorder)*dinA4;
			double pheight=	panorama?(height-heightBorder)*dinA4:height-heightBorder;
			Rectangle paperV= new Rectangle(pwidth,pheight);
			double delta = (height-heightBorder)-((height-heightBorder)*dinA4);
			paperV.setLayoutX(panorama?bgx:bgx+delta/2);
			paperV.setLayoutY(panorama?(bgy/2)+heightBorder:bgy);
			paperV.setFill(new Color(1,0,0,0.2));
			FileInputStream input;
			try {
				input = new FileInputStream(watermarkLoc);
				Image image = new Image(input);
				ImageView imageView = new ImageView(image);
				paper.getChildren().addAll(background,paperV,imageView);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Image image1 = new Image(watermarkLoc);
			
	
			return paper;
		}
		
		public void builder() {
			prev = new AnchorPane();
			prev.getChildren().addAll(paperFrame());
			//AnchorPane.setTopAnchor(prev, heightBorder/2);
			//AnchorPane.setLeftAnchor(prev,width-height);
		}
		
		public Preview() {	
			builder();
			Scene scene = new Scene(prev,width,height);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();		
		}
		
		
		
	}
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
