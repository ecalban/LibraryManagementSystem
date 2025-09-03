module LibraryManagement {
	
	requires javafx.fxml;
	requires javafx.controls;
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	requires javafx.base;
	requires javafx.graphics;
	opens model to javafx.base;
	requires java.sql;
	requires org.postgresql.jdbc;
	requires java.desktop;
}
