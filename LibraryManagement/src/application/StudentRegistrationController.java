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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Country;
import model.Department;

public class StudentRegistrationController {

	@FXML
	private ComboBox<String> countryComboBox;

	@FXML
	private ComboBox<String> departmentComboBox;
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
		countryComboBox.setPromptText("US +1");
		ArrayList<Country> countryList = DBtoArrayList.countryToArrayList();
		for (int i = 0; i < countryList.size(); i++) {
			countryComboBox.getItems()
					.add(countryList.get(i).getCountryCode() + " " + countryList.get(i).getPhoneCode());
		}

		departmentComboBox.setPromptText("Accounting");
		ArrayList<Department> departmentList = DBtoArrayList.departmentToArrayList();
		for (int i = 0; i < departmentList.size(); i++) {
			departmentComboBox.getItems().add(departmentList.get(i).getDepartmentName());
		}

		Tooltip emailTooltip = new Tooltip(
				"Please enter a valid email address in the format username@domain.extension.\n"
						+ "Only letters, numbers, and the characters . _ % + - are allowed before the @,\n"
						+ "and the domain must contain only letters, numbers, dots, and hyphens.");
		email.setTooltip(emailTooltip);
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
	TextField email;
	String enteredEmail;
	String selectedCountry;
	String selectedDepartment;
	@FXML
	private Label successMessageLabel;

	@FXML
	private void handleCountryAction() {
		selectedCountry = countryComboBox.getValue();
	}

	@FXML
	private void handleDepartmentAction() {
		selectedDepartment = departmentComboBox.getValue();
	}

	@FXML
	public void studentRegister(ActionEvent event) throws SQLException {
		enteredFirstName = firstName.getText();
		if (!checkFirstName(enteredFirstName)) {
			firstName.setPromptText("Invalid first name.");
			firstName.clear();
			displayWrongInputLabel();
			return;
		}
		enteredLastName = lastName.getText();
		if (!checkLastName(enteredLastName)) {
			lastName.setPromptText("Invalid last name.");
			lastName.clear();
			displayWrongInputLabel();
			return;
		}
		enteredID = studentID.getText();
		if (!checkID(enteredID)) {
			studentID.setPromptText("The ID is already in use or invalid.");
			studentID.clear();
			displayWrongInputLabel();
			return;

		}
		enteredPhoneNumber = phoneNumber.getText();
		if (!checkPhoneNumber(enteredPhoneNumber)) {
			phoneNumber.setPromptText("Invalid phone number.");
			phoneNumber.clear();
			displayWrongInputLabel();
			return;
		}
		enteredEmail = email.getText();
		if (!checkEmail(enteredEmail)) {
			email.setPromptText("The email is already in use or invalid.");
			email.clear();
			displayWrongInputLabel();
			return;
		}
		if (selectedCountry == null) {
			selectedCountry = "US +1";
		}
		if (selectedDepartment == null) {
			selectedDepartment = "Accounting";
		}
		String sql = "INSERT INTO studentForApprove (studentId, studentFirstName, studentLastName, studentPhoneNumber, studentDepartment, studentEmail) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		addToDatabase(sql, enteredID, enteredFirstName, enteredLastName, selectedCountry + enteredPhoneNumber,
				selectedDepartment, enteredEmail);
		successMessageLabel.setText(" Registration successful. Await librarian approval.");
		successMessageLabel.setVisible(true);
		successMessageLabel.setStyle("-fx-background-color: #5F9EA0;");
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
		fadeOut.play();
		firstName.clear();
		lastName.clear();
		studentID.clear();
		phoneNumber.clear();
		email.clear();
	}

	private void displayWrongInputLabel() {
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
		successMessageLabel.setText("            Invalid input. Hover to see details.  ");
		successMessageLabel.setVisible(true);
		successMessageLabel.setStyle("-fx-background-color: #D9534F;");
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
		fadeOut.play();
	}

	private void addToDatabase(String sql, String enteredID, String enteredFirstName, String enteredLastName,
			String enteredPhoneNumber, String selectedDepartment, String enteredEmail) {
		try {
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
					"postgres", "eren20044");
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, Long.parseLong(enteredID));
			stmt.setString(2, enteredFirstName);
			stmt.setString(3, enteredLastName);
			stmt.setString(4, enteredPhoneNumber);
			stmt.setString(5, selectedDepartment);
			stmt.setString(6, enteredEmail);
			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}

	}

	private boolean checkEmail(String enteredEmail) throws SQLException {
		String sql = "SELECT * FROM studentforapprove WHERE studentemail = " + "'" + enteredEmail + "'";
		ResultSet rs = executeQuery(sql);
		if (rs.next()) {
			return false;
		}
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		return enteredEmail.matches(regex);

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
		con.close();
		return rs;
	}

}
