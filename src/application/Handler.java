package application;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ArrayChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableArray;
import javafx.event.Event;
import javafx.event.EventHandler;

public class Handler  implements ChangeListener, ListChangeListener,ArrayChangeListener, EventHandler<Event> {
	
		@Override
		public void handle(Event arg0) {
			doAction(arg0);		
		}
		@Override
		public void onChanged(Change arg0) {
			System.out.println("Change: "+arg0);	
		}
		@Override
		public void changed(ObservableValue arg0, Object arg1, Object arg2) {
			System.out.println("ObservableValue: "+arg0+" : "+ arg1+" : "+arg2);
		}
		
		public void doAction(Event ev) {
			String inc = ev.toString();
			int start = inc.indexOf("id=");
			int end= inc.indexOf(",");
			int index=end;
			
			do{
				index--;
			}while (Character.isDigit(inc.charAt(index)));		
			int id = Integer.parseInt((String) inc.subSequence(index+1, end));
			inc= inc.substring(start+3,index+1);
			switch (inc) {
				case "Exit":		
					Platform.exit();	
					break;
				case "Log":
					Main.main.setLogsToMainframe();
					break;
				case "Folder Settings":
					Main.main.setPrimarySettingsToMainframe();
					//System.out.println("exec");
					break;
				case "Printer Settings":
					Main.main.setPrinterSettingsToMainframe();
					break;
				case "observedDir":
					Main.settings.chooseObservedDir();
					primRefresh();		
					break;
				case "extension":
					Main.settings.chooseExtension();
					primRefresh();		
					break;
				case "WMLocation":
					Main.settings.chooseWatermark();
					primRefresh();		
					break;
				case "WMSize":
					
					primRefresh();		
					break;
				case "WMPos":
					
					primRefresh();		
					break;
				case "WMOpacity":
					Main.settings.chooseWMOpacity();
					primRefresh();		
					break;
					
				default:
					Main.main.addToLog("Unregistered event occured -> STRING_ID: >"+inc+"< INT_id: >"+id+"<");
					System.out.println("Unregistered event occured -> STRING_ID: >"+inc+"< INT_id: >"+id+"<");
			}
		}
		
		public void primRefresh() {
			Main.deamon.reload();
			Main.main.removePrimarySettings();
			Main.main.setPrimarySettingsToMainframe();						
		}
		@Override
		public void onChanged(ObservableArray arg0, boolean arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			
		}
		
	}



