package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController {

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

	@FXML
	public void switchToStudentRegistration(ActionEvent event) {
		switchToScene(event, "/view/StudentRegistration.fxml");
	}

	@FXML
	public void switchToSearchBook(ActionEvent event) {
		switchToScene(event, "/view/SearchBook.fxml");
	}

	@FXML
	public void switchToLibrarian(ActionEvent event) {
		switchToScene(event, "/view/Librarian.fxml");
	}

	@FXML
	TextField loginUserNameTextField;
	@FXML
	PasswordField loginPasswordPasswordField;

	@FXML
	Label loginUserNameLabel;
	@FXML
	Label loginPasswordLabel;

	String loginEnteredUserName;
	String loginEnteredPassword;

	@FXML
	public void loginButton(ActionEvent event) throws SQLException {
		loginEnteredUserName = loginUserNameTextField.getText();
		String sql = "SELECT * FROM librarians WHERE librarianusername = " + "'" + loginEnteredUserName + "'";
		ResultSet rs = executeQuery(sql);
		try {
			if (!rs.next()) {
				displayWrongInputLabel(loginUserNameLabel, "username");
				loginUserNameTextField.clear();
				return;
			} else {
				loginEnteredPassword = loginPasswordPasswordField.getText();
				if (rs.getString(6).equals(loginEnteredPassword)) {
					switchToLibrarian(event);
					return;
				} else {
					displayWrongInputLabel(loginPasswordLabel, "password");
					loginPasswordPasswordField.clear();
					return;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void displayWrongInputLabel(Label label, String inf) {
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), label);
		label.setText("Invalid " + inf);
		label.setVisible(true);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> label.setVisible(false));
		fadeOut.play();
	}
	
	private ResultSet executeQuery(String sql) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres",
				"eren20044");
		PreparedStatement pst = con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		return rs;
	}

}
