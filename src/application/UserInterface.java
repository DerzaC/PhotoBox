package application;

import javafx.application.Application;
import javafx.collections.SetChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class UserInterface extends Application {
	private AnchorPane root=new AnchorPane(); 
	private Button[][] bttnz=new Button[2][];
	private double h = Main.h;
	private double w = Main.w;
	public SettingFrame[][] whatever;//= new SettingFrame[2][];
	 class space{
		static double top =30;
		static double bottom =50;
		static double left =100;
		static double right =100;
	}
	
	public Effect setShadow() {
		DropShadow shadow = new DropShadow(30, 10, 10, new Color(0.0, 0.0, 0.0, 0.5));
		return shadow;
	}
		
	public void bottomButtonBar() {
		double spaceBetween=50;
		String[] customButtons = new String[]	 	{"Edit","Delete"}; 
		String[] buttons = new String[] 			{"Folder Settings","Printer Settings","Log","Exit"};
		//bttnz[0]=new Button[customButtons.length];
		bttnz[1]=new Button[buttons.length];
		//bttnBarBuilder(customButtons,0,50);
		bttnBarBuilder(buttons,1,50);			
	}
	
	public void bttnBarBuilder(String buttons[],int row,double spaceBetween) {
		double ySpace = 40-row*40;
		double maxWidth=(w-(space.left+space.right))/buttons.length;
		for(int i=0;i < buttons.length;i++) {
			bttnz[row][i]=new Button(buttons[i]);
			bttnz[row][i].setId(buttons[i]+i);
			bttnz[row][i].setTranslateY(h-(space.bottom+40+ySpace));
			bttnz[row][i].setTranslateX(space.left+(i*maxWidth)+(spaceBetween/2));
			bttnz[row][i].setPrefWidth(maxWidth-spaceBetween);
			bttnz[row][i].addEventHandler(ActionEvent.ACTION, Main.evHandler);
			bttnz[row][i].setEffect(setShadow());
		root.getChildren().add(bttnz[row][i]);
		}
	}
	
	public AnchorPane getContent() {
		bottomButtonBar();	
		return root;
	}
		


	public SettingFrame[] getSettingFrame(int kind) {
		SettingFrame.counter=0;
		whatever= new SettingFrame[2][];
		//int kind=0;
		//while(kind<2) {
			String[][] tmp= kind==0?Main.settings.getPrimarySettings():Main.settings.getPrintSettings();
			whatever[kind]=new SettingFrame[tmp.length];
			for(int i =0;i<tmp.length;i++) {
				whatever[kind][i]= new SettingFrame(
					tmp[i][0],
					tmp[i][1],
					tmp[i][2]);
			}
			//kind++;
		//}
		return whatever[kind];
	}
	
	class SettingFrame{
		//public Group view = new Group();
		public AnchorPane anchor = new AnchorPane();
		private double maxWidth = Main.w-space.left-space.right;
		public String infoText="null";
		public String currentSettings="null";
		private Text tf = new Text();
		private Text tf_Info = new Text();
		private double buttonwidth = 80;
		public double itemHeight = 25;
		Rectangle bg = new Rectangle(maxWidth-buttonwidth-10,itemHeight);
		Button bttn = new Button();
		private String eventID="null0";
		public int posNr;
		public static int counter=0;
		
		
	 
		
		SettingFrame(String infoText, String currentSettings,String eventID){
			posNr=counter;
			counter++;
			this.infoText=infoText;
			this.currentSettings=currentSettings;
			this.eventID=eventID;
			TextField();
			background();
			buildButtons();		
			anchor.getChildren().addAll(bg,tf,bttn,tf_Info);
			anchor.setEffect(new Reflection());
			
		}
				
		public void reload() {
			TextField();
			background();
			buildButtons();	
			anchor.getChildren().clear();
			anchor.getChildren().addAll(bg,tf,bttn,tf_Info);
			anchor.setEffect(new Reflection());
		}
		
		public void TextField() {
			//tf.setFill(active?new Color(0,0,0,1):new Color (0,0,0,0.3));
			tf.setText(currentSettings);
			tf_Info.setText("\t"+infoText);
			tf.prefWidth(maxWidth);
			tf_Info.prefWidth(maxWidth);
			tf.prefHeight(itemHeight);
			tf.setStyle("-fx-font: 18 arial;");
			tf_Info.setStyle("-fx-font: 12 arial;");
			tf.setFill(new Color(0,0,0,0.7));
			tf_Info.setFill(new Color(0,0,0,0.8));
			//tf.setEffect(new Reflection());
			//tf.setWrappingWidth(width-space);
			AnchorPane.setTopAnchor(tf, 2.0);
			AnchorPane.setLeftAnchor(tf, buttonwidth+15);
			AnchorPane.setTopAnchor(tf_Info, -12.0);
			AnchorPane.setLeftAnchor(tf_Info, buttonwidth+15);
		}
		
		private void background() {
			bg.setFill(new Color(0.5,0.5,1,0.1));
			bg.setStroke(new Color (0,0,0,0.3));
			//bg.setStrokeWidth(selectedID==id?3:1);
			//bg.setEffect(new Reflection());
			bg.setArcWidth(10);
			bg.setArcHeight(10);
			bg.setVisible(true);
			//AnchorPane.setTopAnchor(bg, 15.0);
			AnchorPane.setLeftAnchor(bg, buttonwidth+10);
		}
		
		private void buildButtons() {
			bttn.setText("Edit");
			bttn.setId(eventID+0);
			bttn.setPrefWidth(buttonwidth);
			bttn.setPrefHeight(itemHeight);
			//AnchorPane.setTopAnchor(bttn, 15.0);
			AnchorPane.setLeftAnchor(bttn, 0.0);
			//bttn.setEffect(new Reflection());
			bttn.addEventHandler(ActionEvent.ACTION, Main.evHandler); 
		}		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
