module Photobox {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.media;
	requires javafx.swing;
	requires javafx.web;
	requires javafx.swt;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
}