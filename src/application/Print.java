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

	public void main(String[] args) throws PrintException, IOException {
		PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
		DocPrintJob job = ps.createPrintJob();
		settings();

		job.addPrintJobListener(new PrintJobAdapter() {
			public void printDataTransferCompleted(PrintJobEvent event) {
				System.out.println("data transfer complete");
			}

			public void printJobNoMoreEvents(PrintJobEvent event) {
				System.out.println("received no more events");
			}

			public void printJobCompleted(PrintJobEvent pje) {
				System.out.println("done");
			}
		});
		FileInputStream fis = new FileInputStream("C:/pbox/t01.jpg");

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

}
