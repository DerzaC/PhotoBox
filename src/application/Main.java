package application;
	
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import application.UserInterface.SettingFrame;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class Main extends Application {
	public static Main main; 
	public static Handler evHandler = new Handler();
	public static Deamon deamon;
	public static Settings settings = new Settings();
	//private Button[][] bttnz;
	public static double h=600;
	public static double w=1000;
	private AnchorPane root;
	public static UserInterface ui = new UserInterface();
	private Group mainframe=new Group();
	private ScrollPane tmp = new ScrollPane();
	private Text logs = new Text(); 
	private boolean buildSettingsOnce = false;
	private boolean buildPrinterSettingsOnce = false;
	private boolean buildLogsOnce = false;
	AnchorPane primarySettings= new AnchorPane();
	AnchorPane printerSettings= new AnchorPane();
	SettingFrame[] primaryElements = ui.getSettingFrame(0);
	SettingFrame[] printElements = ui.getSettingFrame(1);
	
	
	public void removePrimarySettings() {
		mainframe.getChildren().remove(primarySettings);
		primarySettings= new AnchorPane();
		buildSettingsOnce = false;
	}
	
	public void clearMainframe() {
		primaryElements = ui.getSettingFrame(0);
		printElements = ui.getSettingFrame(1);
		primarySettings.setVisible(false);
		tmp.setVisible(false);
		printerSettings.setVisible(false);
	}
	
	public void setPrimarySettingsToMainframe() {
		int spaceBetween = 35;
		clearMainframe();
		if(!buildSettingsOnce) {
			for (int i=0;i<primaryElements.length;i++) {
				SettingFrame tmp = primaryElements[i];
				AnchorPane anc = tmp.anchor;
				AnchorPane.setTopAnchor(anc, UserInterface.space.top+(tmp.itemHeight*tmp.posNr)+(spaceBetween*tmp.posNr));
				primarySettings.getChildren().add(anc);	
			}
			mainframe.getChildren().add(primarySettings);
			buildSettingsOnce=true;
		}
		primarySettings.setVisible(true);			
	}
	
	public void setPrinterSettingsToMainframe() {
		int spaceBetween = 35;
		clearMainframe();
		if(!buildPrinterSettingsOnce) {
			for (int i=0;i<printElements.length;i++) {
				SettingFrame tmp = printElements[i];
				AnchorPane ac = tmp.anchor;
				AnchorPane.setTopAnchor(ac, UserInterface.space.top+(tmp.itemHeight*(tmp.posNr))+(spaceBetween*(tmp.posNr)));
				printerSettings.getChildren().add(ac);	
			}
			mainframe.getChildren().add(printerSettings);
			buildPrinterSettingsOnce=true;
		}
		printerSettings.setVisible(true);		
	}
	
	public void setLogsToMainframe() {
		clearMainframe();
		if(!buildLogsOnce) {
			double[] size =(calcMainFrame());
			tmp.setPrefSize(size[0], size[1]-30);			
			tmp.setContent(logs);	
			mainframe.getChildren().addAll(tmp);
			buildLogsOnce=true;
		}
		tmp.setVisible(true);		
	}

	public double[] calcMainFrame() {
		double width = w-UserInterface.space.left-UserInterface.space.right;
		double height = h-UserInterface.space.top-UserInterface.space.bottom-50;
		mainframe.prefWidth(width);
		mainframe.prefHeight(height);
		AnchorPane.setTopAnchor(mainframe, UserInterface.space.top);
		AnchorPane.setLeftAnchor(mainframe, UserInterface.space.left);
		return new double[] {width,height};		
	}
	
	
	public void addToLog(String entry) {
		LocalDateTime dateTime = LocalDateTime.now();
	    String timeStamp = DateTimeFormatter.ofPattern("HH:mm:ss").format(dateTime);
		logs.setText(logs.getText()+"\n   "+timeStamp+"\t"+entry);
	}
	
	private void genLogTxt() {
		InnerShadow shadow = new InnerShadow(30, 10, 10, new Color(0.0, 0.0, 0.0, 0.5));
		tmp.setEffect(shadow);
		double[] size =(calcMainFrame());
		logs.setFill(new Color(0,1,0,0.9));
		logs.setWrappingWidth(size[0]-15);
	}
	//-------------------crap-----------------------------
	public void popup() {
	AnchorPane prev = new AnchorPane();
	Stage stage = new Stage();
	Scene scene = new Scene(prev,800,800);
	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	stage.setScene(scene);
	stage.show();
	}
	//----------------------------------------------------
	public void entry() {	
		tmpFeed();		
		deamon= new Deamon();	
		//popup();
	}
	
	private void tmpFeed(){
		settings.dir="C:/tmp";
		settings.extension="jpg";
		settings.watermarkLoc="C:/tmp/wmark.png";
		settings.targetImage="wallpaper.jpg";
		settings.watermarkSizePercentage=1;
		settings.watermarkPos= new int[] {10,10};
		settings.watermarkOpacity=1;
	}
	
	//private boolean init = false;
	@Override
	public void start(Stage primaryStage) {
		main=this;
		
		calcMainFrame();
		genLogTxt();

		this.root = ui.getContent();
		root.getChildren().add(mainframe);
		try {			
			Scene scene = new Scene(root,w,h);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			if(Window.getWindows().size()>1) {
				Window.getWindows().get(0).hide();
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}		
		entry();
	}
	
	public static void main(String[] args) {
		launch(args);		
	}
	
	
//-------------------------finalize---------------------	
	 class finTest {
		public String name="";
		public finTest fin = this;
				
		finTest(String name){
			this.name=name;
			System.out.println("object "+name+" has been created");
		}
		
		public void finalize() {
			System.out.println("object "+name+" has been collected");
		}
//------------------------------------------------		
		
		
	}
}
