package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import dao.DBtoArrayList;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import model.Country;

public class LibrarianEditProfilePopUpController {

	static ResultSet rs;

	@FXML
	TextField id;
	@FXML
	TextField fullName;
	@FXML
	TextField phoneNumber;
	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	TextField email;

	@FXML
	private ComboBox<String> countryComboBox;

	@FXML
	public void initialize() throws SQLException {
		if (rs.next()) {
			countryComboBox.setPromptText("");
			ArrayList<Country> countryList = DBtoArrayList.countryToArrayList();
			for (int i = 0; i < countryList.size(); i++) {
				countryComboBox.getItems()
						.add(countryList.get(i).getCountryCode() + " " + countryList.get(i).getPhoneCode());
			}
			id.setText(String.valueOf(rs.getLong(1)));
			id.setEditable(false);
			fullName.setText(rs.getString(2));
			phoneNumber.setText(rs.getString(3));
			username.setText(rs.getString(4));
			password.setText(rs.getString(5));
			password.setEditable(false);
			email.setText(rs.getString(7));
			Tooltip usernameTooltip = new Tooltip(
					"Please enter a username with at least one uppercase letter, one lowercase letter,\n"
							+ "and no spaces. The username can include numbers and special characters,\n"
							+ "and must be between 5 and 25 characters long.");
			username.setTooltip(usernameTooltip);
			Tooltip emailTooltip = new Tooltip(
					"Please enter a valid email address in the format username@domain.extension.\n"
							+ "Only letters, numbers, and the characters . _ % + - are allowed before the @,\n"
							+ "and the domain must contain only letters, numbers, dots, and hyphens.");
			email.setTooltip(emailTooltip);
			username.setTooltip(usernameTooltip);
			successMessageLabel.setVisible(false);
		}
	}

	String selectedCountry;

	@FXML
	private void handleCountryAction() {
		selectedCountry = countryComboBox.getValue();
	}

	String enteredFullName;
	String enteredUserName;
	String enteredPhoneNumber;
	String enteredEmail;
	@FXML
	Label successMessageLabel;

	@FXML
	public void save(ActionEvent event) throws SQLException {
		enteredFullName = fullName.getText();
		if (!checkFullName(enteredFullName)) {
			fullName.setPromptText("Invalid name.");
			fullName.clear();
			displayWrongInputLabel();
			return;
		}
		enteredUserName = username.getText();
		if (!checkUsername(enteredUserName)) {
			username.setPromptText("Invalid username.");
			username.clear();
			displayWrongInputLabel();
			return;
		}
		handleCountryAction();
		enteredPhoneNumber = phoneNumber.getText();
		if(selectedCountry!=null) {
			if (!checkPhoneNumber(enteredPhoneNumber)) {
				enteredPhoneNumber = selectedCountry + enteredPhoneNumber;
				phoneNumber.setPromptText("Invalid phone number.");
				phoneNumber.clear();
				displayWrongInputLabel();
				return;
		}
	
		}
		
		enteredEmail = email.getText();
		if (!checkEmail(enteredEmail)) {
			email.setPromptText("The email is already in use or invalid.");
			email.clear();
			displayWrongInputLabel();
			return;
		}
		

		String sql = "UPDATE librarians " + "SET librarianfullname = ?, " + "librarianphonenumber = ?, "
				+ "librarianusername = ?, " + "librarianemail = ? " + "WHERE librarianid = ?";
		addToDatabase(sql, enteredFullName, enteredPhoneNumber, enteredUserName, enteredEmail,
				Integer.parseInt(id.getText()));
		if (!rs.getString(4).equals(enteredUserName)) {
			String sql2 = "DELETE FROM remembereduser WHERE username = '" + rs.getString(4) + "'";
			executeUpdate(sql2);
	}
		successMessageLabel.setText("                         Your profile has been updated successfully.");
		successMessageLabel.setVisible(true);
		successMessageLabel.setStyle("-fx-background-color: #5F9EA0;");
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
		fadeOut.play();
		username.clear();
		fullName.clear();
		phoneNumber.clear();
		email.clear();
	}

	private void addToDatabase(String sql, String enteredFullName, String enteredNumber, String enteredUsername,
			String enteredEmail, int enteredID) {
		try {
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
					"postgres", "eren20044");
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, enteredFullName);
			stmt.setString(2, enteredNumber);
			stmt.setString(3, enteredUsername);
			stmt.setString(4, enteredEmail);
			stmt.setInt(5, enteredID);
			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		
	}

	private boolean checkFullName(String enteredFullName) {
		String regex = "^[A-Za-z]{1,25}(?: [A-Za-z]{1,25})*$";
		return enteredFullName.matches(regex);
	}

	private boolean checkUsername(String username) {
		String regex = "^(?=.*[A-Z])(?=.*[a-z])[^\\s]{5,25}$";
		return username.matches(regex);
	}

	private boolean checkPhoneNumber(String enteredPhoneNumber) {
		String regex = "^[0-9]{7,15}$";
		return enteredPhoneNumber.matches(regex);
	}

	private boolean checkEmail(String enteredEmail) throws SQLException {
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		return enteredEmail.matches(regex);
	}

	private void displayWrongInputLabel() {
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
		successMessageLabel.setText("                                Invalid input. Hover to see details.");
		successMessageLabel.setVisible(true);
		successMessageLabel.setStyle("-fx-background-color: #D9534F;");
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
		fadeOut.play();
	}

	private void executeUpdate(String sql) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres",
				"eren20044");
		PreparedStatement pst = con.prepareStatement(sql);
		pst.executeUpdate();
		con.close();
	}
}
