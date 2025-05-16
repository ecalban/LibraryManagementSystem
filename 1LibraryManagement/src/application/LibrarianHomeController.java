package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class LibrarianHomeController {

	@FXML
	public void initialize() {
	}

	@FXML
	HBox menuBoxHome;
	@FXML
	BorderPane mainPaneHome;

	@FXML
	public void librarianFindStudent(javafx.event.Event e) {
	    FXMLLoader loader = new FXMLLoader(
	        LibrarianController.class.getResource("/view/LibrarianHomeFindStudent.fxml"));
	    AnchorPane anchorpane;
	    try {
	        anchorpane = loader.load();
	        Pane pane = new Pane();
	        pane.getChildren().setAll(anchorpane);
	        mainPaneHome.setCenter(pane);
	    } catch (IOException e1) {
	        System.out.println("An error: " + e1);
	    }
	}


	@FXML
	public void librarianFindBook(javafx.event.Event e) {
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianHomeFindBook.fxml"));
		AnchorPane anchorpane;
		try {
			anchorpane = loader.load();
			Pane pane = new Pane();
			pane.getChildren().setAll(anchorpane);
			mainPaneHome.setCenter(pane);
		} catch (IOException e1) {
			System.out.println("An error: " + e1);
		}
	}

}
