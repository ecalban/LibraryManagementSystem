package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LibrarianHomeController {

	@FXML
	public void initialize() {
	}

	@FXML
	HBox menuBoxHome;
	@FXML
	BorderPane mainPaneHome;

	@FXML
	public void librarianFindBook(javafx.event.Event e) {
		FXMLLoader loader = new FXMLLoader(
				LibrarianController.class.getResource("/view/LibrarianHomeFindBook.fxml"));
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
	public void librarianFindBookIssue(javafx.event.Event e) {
		FXMLLoader loader = new FXMLLoader(
				LibrarianController.class.getResource("/view/LibrarianHomeFindBookIssue.fxml"));
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
	TextField findStudentTextField;
	@FXML
	Label successMessageLabel;

	@FXML
	public void findStudent(javafx.event.Event e) throws SQLException, IOException {
		String search = findStudentTextField.getText();
		String sql = "SELECT * FROM students WHERE studentid =" + search + "";
		if (!search.matches("^\\d+$")) {
			displayAlert("           No student found. Please check the ID and try again.", "#D9534F",
					successMessageLabel);
			return;
		}
		if (!executeQuery(sql).next()) {
			displayAlert("           No student found. Please check the ID and try again.", "#D9534F",
					successMessageLabel);
			return;
		}
		FindStudentPopUpController.rs = executeQuery(sql);
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FindStudent.fxml"));
		Scene scene = new Scene(loader.load());
		popupStage.setScene(scene);
		popupStage.setResizable(false);
		popupStage.showAndWait();
	}

	@FXML
	TextField findBookIssuedTextField;
	@FXML
	Label successMessageLabel2;

	@FXML
	public void findBookIssued(javafx.event.Event e) throws SQLException, IOException {
		String search = findBookIssuedTextField.getText();
		String sql = "SELECT * FROM books WHERE bookid =" + search + "";
		if (!search.matches("^\\d+$")) {
			displayAlert("                No book found. Please check the ID and try again.", "#D9534F",
					successMessageLabel2);
			return;
		}
		ResultSet rs = executeQuery(sql);
		if (!rs.next()) {
			displayAlert("                No book found. Please check the ID and try again.", "#D9534F",
					successMessageLabel2);
			return;
		} else {			
			if (rs.getInt(8) == 0) {
				displayAlert("  The book is never borrowed, has "+ rs.getInt(7)+" copies in stock, and is available.", "#D9534F", successMessageLabel2);
				return;
			}
		}
		FindIssuedBookPopUpController.rs = executeQuery(sql);
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FindBookIssued.fxml"));
		Scene scene = new Scene(loader.load());
		popupStage.setScene(scene);
		popupStage.setResizable(false);
		popupStage.showAndWait();
	}
	
	@FXML
	TextField findBookTextField;
	@FXML
	Label successMessageLabel3;

	@FXML
	public void findBook(javafx.event.Event e) throws SQLException, IOException {
		String search = findBookTextField.getText();
		String sql = "SELECT * FROM books WHERE bookid =" + search + "";
		if (!search.matches("^\\d+$")) {
			displayAlert("                No book found. Please check the ID and try again.", "#D9534F",
					successMessageLabel3);
			return;
		}
		ResultSet rs = executeQuery(sql);
		if (!rs.next()) {
			displayAlert("                No book found. Please check the ID and try again.", "#D9534F",
					successMessageLabel3);
			return;
		} 
		
		FindBookPopUpController.rs = executeQuery(sql);
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FindBook.fxml"));
		Scene scene = new Scene(loader.load());
		popupStage.setScene(scene);
		popupStage.setResizable(false);
		popupStage.showAndWait();
	}
	

	private ResultSet executeQuery(String sql) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres",
				"eren20044");
		PreparedStatement pst = con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		con.close();
		return rs;
	}

	private void displayAlert(String str, String color, Label successMessageLabel) {
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
		successMessageLabel.setText(str);
		successMessageLabel.setVisible(true);
		successMessageLabel.setStyle("-fx-background-color:" + color + ";");
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
		fadeOut.play();
	}
}
