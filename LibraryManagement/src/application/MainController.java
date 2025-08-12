package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DBtoArrayList;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.RememberedUser;

public class MainController {
	

	@FXML
	ComboBox<String> loginUserName;	
	
	@FXML
	public void initialize() {
		ArrayList<RememberedUser> userList = DBtoArrayList.rememberedToArrayList();
		for (int i = 0; i < userList.size(); i++) {
			loginUserName.getItems().add(userList.get(i).getUserName());
		}
		loginUserName.setEditable(true);

		loginUserName.getEditor().setOnMouseClicked(event -> {
		    if (!loginUserName.isShowing()) {
		    	loginUserName.show();
		    }
		});

		loginUserName.setOnMouseClicked(event -> {
		    if (!loginUserName.isShowing()) {
		    	loginUserName.show();
		    }
		});
		
	    loginUserName.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
	        if (newVal != null) {
	            String password = fetchPasswordForUser(newVal);
	            loginPasswordPasswordField.setText(password);
	        } else {
	            loginPasswordPasswordField.clear();
	        }
	    });
	}
	
	private String fetchPasswordForUser(String username) {
	    String password = "";
	    String sql = "SELECT librarianpassword FROM librarians WHERE librarianusername = ?";
	    try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres", "eren20044");
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setString(1, username);
	        ResultSet rs = pst.executeQuery();

	        if (rs.next()) {
	            password = rs.getString("librarianpassword");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return password;
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

	@FXML
	public void switchToLibrarianSignUp(ActionEvent event) {
		switchToScene(event, "/view/LibrarianSignUp.fxml");
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
	PasswordField loginPasswordPasswordField;

	@FXML
	Label loginUserNameLabel;
	@FXML
	Label loginPasswordLabel;
	@FXML
	CheckBox rememberme;
	
	String loginEnteredUserName;
	String loginEnteredPassword;

	@FXML
	public void loginButton(ActionEvent event) throws SQLException {
		loginEnteredUserName = loginUserName.getEditor().getText();
		String sql = "SELECT * FROM librarians WHERE librarianusername = " + "'" + loginEnteredUserName + "'";
		ResultSet rs = executeQuery(sql);
		try {
			if (!rs.next()) {
				displayWrongInputLabel(loginUserNameLabel, "username");
				loginUserName.getEditor().clear();
				return;
			} else {
				loginEnteredPassword = loginPasswordPasswordField.getText();
				if (rs.getString(6).equals(loginEnteredPassword)) {
					if(rememberme.isSelected()) {
						addToRemembered(loginEnteredUserName);
					}
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



	private void addToRemembered(String username) {
	    String sql = "INSERT INTO remembereduser (username) VALUES (?)";
	    try (Connection con = DriverManager.getConnection(
	            "jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres", "eren20044");
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, username);
	        stmt.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("An error: " + e);
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
