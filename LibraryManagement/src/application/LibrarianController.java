package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBtoArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LibrarianController {

	@FXML
	private Button profileButton;
	@FXML
	Hyperlink githubLink;

	@FXML
	public void initialize() {
		githubLink.setOnAction(e -> {
		    try {
		        java.awt.Desktop.getDesktop().browse(new java.net.URI("https://github.com/ecalban"));
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		});
		profileButton.setText("▼");
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianHome2.fxml"));
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
	

	
	@FXML
	public void librarianShowProfile(ActionEvent event) throws IOException, SQLException {
		String sql = "SELECT * FROM librarians WHERE isactive = TRUE";
		LibrarianProfilePopUpController.rs = executeQuery(sql);
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LibrarianShowProfile.fxml"));
		Scene scene = new Scene(loader.load());
		popupStage.setScene(scene);
		popupStage.setResizable(false);
		popupStage.showAndWait();
	}
	
	
	@FXML
	public void librarianEditProfile(ActionEvent event) throws IOException, SQLException {
		String sql = "SELECT * FROM librarians WHERE isactive = TRUE";
		LibrarianEditProfilePopUpController.rs = executeQuery(sql);
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LibrarianEditProfile.fxml"));
		Scene scene = new Scene(loader.load());
		popupStage.setScene(scene);
		popupStage.setResizable(false);
		popupStage.showAndWait();
	}
	
	@FXML
	public void librarianChangePassword(ActionEvent event) throws IOException, SQLException {
		String sql = "SELECT * FROM librarians WHERE isactive = TRUE";
		LibrarianPasswordPopUpController.rs = executeQuery(sql);
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LibrarianChangePassword.fxml"));
		Scene scene = new Scene(loader.load());
		popupStage.setScene(scene);
		popupStage.setResizable(false);
		popupStage.showAndWait();
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
		FXMLLoader loader = new FXMLLoader(LibrarianController.class.getResource("/view/LibrarianHome2.fxml"));
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
	public void librarianHandleButtonDisplayIssued(ActionEvent action) {
		String sql = "SELECT * FROM books WHERE stockissued != 0";
		try {
			SearchBookTableController.bookList = DBtoArrayList.SearchedBookToArrayList(sql);
		} catch (Exception e) {
			System.out.println("An error: " + e);
		}
		FXMLLoader loader = new FXMLLoader(
				LibrarianController.class.getResource("/view/LibrarianDisplayBooksIssued.fxml"));
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
		FXMLLoader loader = new FXMLLoader(
				LibrarianController.class.getResource("/view/LibrarianStudentRegister.fxml"));
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
			StudentsRegisteredTableController.studentApproveList = DBtoArrayList.StudentForApproveToArrayList(sql);
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
	
	private ResultSet executeQuery(String sql) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres",
				"eren20044");
		PreparedStatement pst = con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		con.close();
		return rs;
	}


}
