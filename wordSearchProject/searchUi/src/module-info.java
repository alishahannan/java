module searchUi {
	requires javafx.controls;
	requires org.jsoup;
	requires javafx.base;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
}
