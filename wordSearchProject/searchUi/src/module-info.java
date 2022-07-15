module searchUi {
	requires javafx.controls;
	requires org.jsoup;
	requires javafx.base;
	requires java.desktop;
	requires org.junit.jupiter.api;
	requires junit;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
}
