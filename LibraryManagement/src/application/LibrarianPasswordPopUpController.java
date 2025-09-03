package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class LibrarianPasswordPopUpController {

	static ResultSet rs;

	@FXML
	PasswordField oldPassword;
	@FXML
	PasswordField newPassword;
	@FXML
	PasswordField newPasswordA;

	@FXML
	public void initialize() throws SQLException {
		if (rs.next()) {
			Tooltip passwordTooltip = new Tooltip("Password must be between 8 and 20 characters long,\n"
					+ "include at least one uppercase letter, one lowercase letter,\n"
					+ "one digit, and one special character.");
			newPassword.setTooltip(passwordTooltip);
			successMessageLabel.setVisible(false);
		}
	}


	String enteredOldPassword;
	String enteredNewPassword;
	String enteredNewPasswordA;
	@FXML
	Label successMessageLabel;

	@FXML
	public void save(ActionEvent event) throws SQLException {
		enteredOldPassword = oldPassword.getText();
		if(!enteredOldPassword.equals(rs.getString(5))) {
			oldPassword.setPromptText("Incorrect current password.");
			oldPassword.clear();
			displayWrongInputLabel();
			return;
		}
		
		enteredNewPassword = newPassword.getText();
		if(!checkPassword(enteredNewPassword)) {
			newPassword.setPromptText("Invalid new password.");
			newPassword.clear();
			newPasswordA.clear();
			return;
		}
		enteredNewPasswordA = newPasswordA.getText();
		if(!enteredNewPassword.equals(enteredNewPasswordA)) {
			newPasswordA.setPromptText("New password and confirmation do not match.");
			newPasswordA.clear();
			return;
		}
		
		String sql = "UPDATE librarians " + "SET librarianpassword = ? " + "WHERE librarianid = ?";
		addToDatabase(sql, enteredNewPassword, rs.getInt(1));
		successMessageLabel.setText("              Password updated successfully.");
		successMessageLabel.setVisible(true);
		successMessageLabel.setStyle("-fx-background-color: #5F9EA0;");
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
		fadeOut.play();
		oldPassword.clear();
		newPassword.clear();
		newPasswordA.clear();
	}

	private void addToDatabase(String sql, String enteredOldPassword, int id) {
		try {
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
					"postgres", "eren20044");
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, enteredOldPassword);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		
	}
	
	private boolean checkPassword(String password) {
		String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
		return password.matches(regex);
	}


	private void displayWrongInputLabel() {
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
		successMessageLabel.setText("                Invalid input. Hover to see details.");
		successMessageLabel.setVisible(true);
		successMessageLabel.setStyle("-fx-background-color: #D9534F;");
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
		fadeOut.play();
	}

}
