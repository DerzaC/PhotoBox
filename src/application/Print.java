package application;

import java.io.FileInputStream;
import java.io.IOException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.*;
import javax.print.attribute.standard.ColorSupported;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.event.*;

public class Print {
	Handler evHandler = Main.evHandler;
	private String targetFile;
	
	public Print() {
		
	}
	
	public boolean go(String targetFile){
		this.targetFile=targetFile;
		try {
			Main.deamon.setPrintInProgress();
			startPrint();			
			return true;
		} catch (PrintException | IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void startPrint() throws PrintException, IOException {
		PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
		DocPrintJob job = ps.createPrintJob();
		settings();
		job.addPrintJobListener(evHandler);
		FileInputStream fis = new FileInputStream(targetFile);

		// Doc doc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
		Doc doc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.JPEG, null);
		// Doc doc=new SimpleDoc(doc, null, null);
		PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();

		attrib.add(new Copies(1));
		job.print(doc, attrib);
	}

	static AttributeSet attribute;
	PrintService[] ps;

	public static void settings() {
		DocFlavor df = DocFlavor.URL.JPEG;
		attribute = new HashAttributeSet();
		attribute.add(OrientationRequested.LANDSCAPE);
		attribute.add(ColorSupported.SUPPORTED);
		PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, attribute);
		for (int i = 0; i < ps.length; i++) {
			if (ps[i].isDocFlavorSupported(df))
				System.out.println(ps[i].getName());
		}
	}

	/*
	public void fuckOFff() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		PageFormat pageformat = new PageFormat();
		pageformat = printJob.pageDialog(pageformat);  // Hier kommt der PageDialog
		printJob.setPrintable(this //(da Printable implementiert werden muss)//, pageformat);
		if (printJob.printDialog()){  // Dann ein Dialog in dem du deinen Drucker auswählen kannst
			try {
				printJob.print(); // Drucken
			}
			catch(PrinterException pe) {
				sErr = "Error printing: " + pe;
			}

		}
		}
	
	*/
	
	
	
}
