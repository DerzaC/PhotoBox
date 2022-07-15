package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import application.Deamon.Task;
import javafx.application.Platform;


public class Deamon {

	String[] lastScan;
	File observedDir;
	ArrayList<String> addedElements = new ArrayList<String>();
	ArrayList<String> removedElements = new ArrayList<String>();
	private Settings settings=Main.settings;
	private boolean settingsChanged=false;
	public static ArrayList<Task> tasks= new ArrayList<>();


	public String getAdded(int index) {
		return addedElements.toArray(new String[addedElements.size()])[index];
	}

	public String[] getAdded() {
		String[] add = new String[addedElements.size()];
		addedElements.toArray(add);
		return add;
	}

	public String getRemoved(int index) {
		return removedElements.toArray(new String[removedElements.size()])[index];
	}

	public String[] getRemoved() {
		return removedElements.toArray(new String[removedElements.size()]);
	}

	public void clearAdd() {
		addedElements.clear();
	}

	public void clearRemoved() {
		removedElements.clear();
	}
	


	public boolean reFresh() {
		clearAdd();
		clearRemoved();
		String[] newScan = observedDir.list();
		Arrays.sort(newScan);
		if (equal(newScan)) {
			return true;
		} else {
			listChanges(newScan);
			lastScan = newScan;
			compareExtensions();
		}
		return false;
	}

	private boolean equal(String[] temp) {
		if (temp.length != lastScan.length) {
			return false;
		}
		for (int i = 0; i < temp.length; i++) {
			if (!(temp[i].equals(lastScan[i]))) {

				return false;
			}
		}
		return true;
	}

	public void listChanges(String[] temp) {
		if(settingsChanged) {
			lastScan=temp;
			settingsChanged=false;
			}else {
				for (int n = 0; n < 2; n++) {
					String[] arr1 = n == 0 ? temp : lastScan;
					String[] arr2 = n == 0 ? lastScan : temp;
					for (int i = 0; i < arr1.length; i++) {
						boolean changed = true;
						for (int j = 0; j < arr2.length; j++) {
							if (arr1[i].equals(arr2[j])) {
								j = arr2.length;
								changed = false;
							}
						}
						if (changed) {
							if (n == 0) {
								addedElements.add(arr1[i]);
							} else {
								removedElements.add(arr1[i]);
							}
						}
					}
				}
			}
	}
	
	
	public void reload() {
		settingsChanged=true;
		observedDir = new File(settings.dir);
		addedElements.clear();
		removedElements.clear();
	}

	public Deamon() {
		observedDir = new File(settings.dir);
		lastScan = observedDir.list();
		Arrays.sort(lastScan);
		ImageBuilder img = new ImageBuilder();
		nestedLoop();
	}
	
	public void print() {
		for (int i = 0; i < addedElements.size(); i++) {
			Main.main.addToLog("added: " + addedElements.get(i));
			//System.out.println("added: " + addedElements.get(i));
		}
		for (int i = 0; i < removedElements.size(); i++) {
			
			Main.main.addToLog("removed: " + removedElements.get(i));
			//System.out.println("removed: " + removedElements.get(i));
		}
	}
	
	public void nestedLoop() {
		Thread t1 = new Thread(new Nested());	
		t1.setDaemon(true);	
		t1.start();
	}
	
	class Nested implements Runnable{
		public void run() {
			while(true) {
				Platform.runLater(new RunLater());
				try {
					Thread.sleep(500);				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		}	
		
		class RunLater implements Runnable{
			@Override
			public void run() {
				reFresh();
				print();
				
			}	
		}		
	}
				// not failsafe if filename contains extension results in false-true
	public void compareExtensions() {
		String chosenExtrension = Main.settings.extension;
		for (int i =0 ; i < addedElements.size(); i++) {
			if(addedElements.get(i).indexOf(chosenExtrension) >= 0) {
				tasks.add(new Task(addedElements.get(i)));
			}else {
				Main.main.addToLog(addedElements.get(i)+" dropped, wrong Extension");
			}
			
		}
	}
	
	
	
	class Task{
		public String filename;
		private boolean isBackuped;
		private boolean isMarked;
		private boolean isSaved;
		
		public void setBackuped(boolean isBackuped) {
			this.isBackuped=isBackuped;
		}
		
		public void setMarked(boolean isMarked) {
			this.isMarked=isMarked;
		}
		
		public void setSaved(boolean isSaved) {
			this.isSaved=isSaved;
		}
		
		Task(String filename){
			this.filename=filename;
			Main.main.addToLog("Task "+filename+" has been created");
		}
		
		public boolean isDone() {
			return isBackuped && isMarked && isSaved;
		}	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
