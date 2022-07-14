package application;

import java.io.File;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Settings {
	//-----------------------------------------Primary
	public String dir="-";
	public String extension="";
	public String watermarkLoc="";
	public String targetImage="";
	public double watermarkSizePercentage=0;
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
		String infoText = "watermark Opacity (0-1)";
		String currentSettings= ""+(watermarkOpacity*100-100)+"%";
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
		    //System.out.println("Your choice: " + result.get());
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
		new String[] {"jpg","png","bmp","ect"}	
		);
		if(!(tmp=="null")) {
			this.extension=tmp;
			Main.main.addToLog("File Extension changed from "+old+" to: "+watermarkLoc);
		}
	}
	
	
	//---------------------------------------------------Printer
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
	
	
	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
