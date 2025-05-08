package application;

import java.io.IOException;
import dao.DBtoArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibrarianController {


    @FXML
    private Button profileButton;
    
	@FXML
	public void initialize() {
        profileButton.setText("▼");
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianHome.fxml"));
		AnchorPane anchorpane;
		try {
			anchorpane = loader.load();
	        Pane pane = new Pane();
	        pane.getChildren().setAll(anchorpane);
	        mainPane.setCenter(pane);
	        
		} catch (IOException e) {
			System.out.println("An error: " + e);
		}
	}
	
	@FXML
	VBox menuBox;
	
	
	@FXML
	public void profileButton(ActionEvent event) {
        boolean isVisible = menuBox.isVisible();
        
        menuBox.setVisible(!isVisible);  
        menuBox.setManaged(!isVisible); 
	}

	public void switchToScene(ActionEvent event, String fileName) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fileName));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			System.out.println("An error: " + e);
		}
	}
	
	
	public Scene addCss(Scene scene, String cssFileName) {
		scene.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());
		return scene;
	}
	
	
	@FXML
	public void switchToMain(ActionEvent event) {
		switchToScene(event, "/view/Main.fxml");
	}
	
	
	@FXML
	BorderPane mainPane;
	
	@FXML
	public void librarianHandleButtonHome(ActionEvent action) {
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianHome.fxml"));
		AnchorPane anchorpane;
		try {
			anchorpane = loader.load();
	        Pane pane = new Pane();
	        pane.getChildren().setAll(anchorpane);
	        mainPane.setCenter(pane);
	        
		} catch (IOException e) {
			System.out.println("An error: " + e);
		}
	}
	@FXML
	public void librarianHandleButtonAddBook(ActionEvent action) {
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianAddBook.fxml"));
		AnchorPane anchorpane;
		try {
			anchorpane = loader.load();
			Pane pane = new Pane();
			pane.getChildren().setAll(anchorpane);
			mainPane.setCenter(pane);
			
		} catch (IOException e) {
			System.out.println("An error: " + e);
		}
	}
	
	@FXML
	public void librarianHandleButtonStudentApprove(ActionEvent action) {
		String sql = "SELECT * FROM studentforapprove";
		try {
			ApproveTableController.studentApproveList = DBtoArrayList.StudentForApproveToArrayList(sql);
		} catch (Exception e) {
			System.out.println("An eKrror: " + e);
		}
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianStudentRegister.fxml"));
		AnchorPane anchorpane;
		try {
			anchorpane = loader.load();
			Pane pane = new Pane();
			pane.getChildren().setAll(anchorpane);
			mainPane.setCenter(pane);
			
		} catch (IOException e) {
			System.out.println("An error: " + e);
		}
	}
	
	@FXML
	public void librarianHandleButtonStudentsRegistered(ActionEvent action) {
		String sql = "SELECT * FROM students";
		try {
			ApproveTableController.studentApproveList = DBtoArrayList.StudentForApproveToArrayList(sql);
		} catch (Exception e) {
			System.out.println("An eKrror: " + e);
		}
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianStudents.fxml"));
		AnchorPane anchorpane;
		try {
			anchorpane = loader.load();
			Pane pane = new Pane();
			pane.getChildren().setAll(anchorpane);
			mainPane.setCenter(pane);
			
		} catch (IOException e) {
			System.out.println("An error: " + e);
		}
	}
	
	@FXML
	public void librarianHandleButtonAllBooks(ActionEvent action) {
		String sql = "SELECT * FROM Books";
		try {
			SearchBookTableController.bookList = DBtoArrayList.SearchedBookToArrayList(sql);
		} catch (Exception e) {
			System.out.println("An error: " + e);
		}
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianDisplayBooks.fxml"));
		AnchorPane anchorpane;
		try {
			anchorpane = loader.load();
			Pane pane = new Pane();
			pane.getChildren().setAll(anchorpane);
			mainPane.setCenter(pane);
			
		} catch (IOException e) {
			System.out.println("An erM K ror: " + e);
		}
	}
	
	@FXML
	public void librarianHandleButtonIssueBooks(ActionEvent action) {
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianIssueBooks.fxml"));
		AnchorPane anchorpane;
		try {
			anchorpane = loader.load();
	        Pane pane = new Pane();
	        pane.getChildren().setAll(anchorpane);
	        mainPane.setCenter(pane);
	        
		} catch (IOException e) {
			System.out.println("An error: " + e);
		}
	}

}
