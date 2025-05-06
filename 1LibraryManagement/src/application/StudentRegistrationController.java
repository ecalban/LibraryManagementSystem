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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Country;

public class StudentRegistrationController {

	@FXML
	private ComboBox<String> countryComboBox;

	@FXML
	public void initialize() {
		countryComboBox.setPromptText("US +1");
		ArrayList<Country> countryList = DBtoArrayList.countryToArrayList();
		for (int i = 0; i < countryList.size(); i++) {
			countryComboBox.getItems()
					.add(countryList.get(i).getCountryCode() + " " + countryList.get(i).getPhoneCode());
		}

		Tooltip usernameTooltip = new Tooltip(
				"Please enter a username with at least one uppercase letter, one lowercase letter,\n"
						+ " and no spaces. The username can include numbers and special characters,\n"
						+ " and must be between 5 and 25 characters long.");
		username.setTooltip(usernameTooltip);

		Tooltip passwordTooltip = new Tooltip(
				"Please enter a password with at least one uppercase letter, one lowercase letter, one number,\n"
						+ "and one special character. The password must be between 8 and 20 characters long, \n"
						+ "and should not contain spaces.");
		password.setTooltip(passwordTooltip);

		successMessageLabel.setVisible(false);
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
	public void switchToMain(ActionEvent event) {
		switchToScene(event, "/view/Main.fxml");
	}

	@FXML
	TextField firstName;
	String enteredFirstName;
	@FXML
	TextField lastName;
	String enteredLastName;
	@FXML
	TextField studentID;
	String enteredID;
	@FXML
	TextField phoneNumber;
	String enteredPhoneNumber;
	@FXML
	TextField username;
	String enteredUsername;
	@FXML
	TextField password;
	String enteredPassword;
	String selectedItem;
    @FXML
    private Label successMessageLabel;
	
	
	@FXML
	private void handleComboBoxAction() {
		selectedItem = countryComboBox.getValue();
	}

	@FXML
	public void studentRegister(ActionEvent event) throws SQLException {
		enteredID = studentID.getText();
		if (!checkID(enteredID)) {
			studentID.setPromptText("The ID is already in use or invalid.");
			studentID.clear();
			return;
		}
		enteredFirstName = firstName.getText();
		if (!checkFirstName(enteredFirstName)) {
			firstName.setPromptText("Invalid first name.");
			firstName.clear();
			return;
		}
		enteredLastName = lastName.getText();
		if (!checkLastName(enteredLastName)) {
			lastName.setPromptText("Invalid last name.");
			lastName.clear();
			return;
		}
		enteredPhoneNumber = phoneNumber.getText();
		if (!checkPhoneNumber(enteredPhoneNumber)) {
			phoneNumber.setPromptText("Invalid phone number.");
			phoneNumber.clear();
			return;
		}
		enteredUsername = username.getText();
		if (!checkUsername(enteredUsername)) {
			username.setPromptText("The username is already in use or invalid.");
			username.clear();
			return;
		}
		enteredPassword = password.getText();
		if (!checkPassword(enteredPassword)) {
			password.setPromptText("Invalid password.");
			password.clear();
			return;
		}
		if(selectedItem == null) {
			selectedItem = "US +1";
		}
		String sql = "INSERT INTO studentForApprove (studentId, studentFirstName, studentLastName, studentPhoneNumber, studentUsername, studentPassword) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		addToDatabase(sql, enteredID, enteredFirstName, enteredLastName, selectedItem + enteredPhoneNumber, enteredUsername,
				enteredPassword);
		
        successMessageLabel.setVisible(true);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
        fadeOut.play();
        firstName.clear();
        lastName.clear();
        studentID.clear();
        phoneNumber.clear();
        username.clear();
        password.clear();
        
	}

	private void addToDatabase(String sql, String enteredID, String enteredFirstName, String enteredLastName,
			String enteredPhoneNumber, String enteredUsername, String enteredPassword) {
		try {
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
					"postgres", "eren20044");
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, Long.parseLong(enteredID));
			stmt.setString(2, enteredFirstName);
			stmt.setString(3, enteredLastName);
			stmt.setString(4, enteredPhoneNumber);
			stmt.setString(5, enteredUsername);
			stmt.setString(6, enteredPassword);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}

	}

	private boolean checkPassword(String enteredPassword) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,20}$";
        return enteredPassword.matches(regex);
	}

	private boolean checkUsername(String enteredUsername) throws SQLException {
		String sql = "SELECT * FROM studentforapprove WHERE studentusername = " + "'" + enteredUsername + "'";
		ResultSet rs = executeQuery(sql);
		if (rs.next()) {
			return false;
		}
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{5,25}$";
        return	enteredUsername.matches(regex);
        
	}

	private boolean checkPhoneNumber(String enteredPhoneNumber) {
		String regex = "^[0-9]{7,15}$";
		return enteredPhoneNumber.matches(regex);
	}

	private boolean checkLastName(String enteredLastName) {
        String regex = "^[a-zA-Z]{1,25}$";
        return enteredLastName.matches(regex);
	}

	private boolean checkFirstName(String enteredFirstName) {
        String regex = "^[a-zA-Z]{1,25}$";
        return enteredFirstName.matches(regex);
	}

	public boolean checkID(String enteredID) throws SQLException {
		if (enteredID.length() != 11) {
			return false;
		}
		String sql = "SELECT * FROM studentforapprove WHERE studentid = " + "'" + Long.parseLong(enteredID) + "'";
		ResultSet rs = executeQuery(sql);
		if (rs.next()) {
			return false;
		}
		return enteredID.chars().allMatch(Character::isDigit);
	}

	private ResultSet executeQuery(String sql) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres",
				"eren20044");
		PreparedStatement pst = con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		return rs;
	}

}
