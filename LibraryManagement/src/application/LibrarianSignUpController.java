package application;

import java.io.IOException;
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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Country;
import javafx.stage.FileChooser;
import java.io.File;

public class LibrarianSignUpController {

	@FXML
	private ComboBox<String> cvComboBox;
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
		cvComboBox.addEventFilter(MouseEvent.ANY, Event::consume);
		cvComboBox.addEventFilter(KeyEvent.ANY, Event::consume);
		Tooltip usernameTooltip = new Tooltip(
				"Please enter a username with at least one uppercase letter, one lowercase letter,\n"
						+ "and no spaces. The username can include numbers and special characters,\n"
						+ "and must be between 5 and 25 characters long.");
		userName.setTooltip(usernameTooltip);
		Tooltip emailTooltip = new Tooltip(
				"Please enter a valid email address in the format username@domain.extension.\n"
						+ "Only letters, numbers, and the characters . _ % + - are allowed before the @,\n"
						+ "and the domain must contain only letters, numbers, dots, and hyphens.");
		Tooltip passwordTooltip = new Tooltip("Password must be between 8 and 20 characters long,\n"
				+ "include at least one uppercase letter, one lowercase letter,\n"
				+ "one digit, and one special character.");
		password.setTooltip(passwordTooltip);
		email.setTooltip(emailTooltip);
		userName.setTooltip(usernameTooltip);
		successMessageLabel.setVisible(false);
	}

	String selectedCountry;

	@FXML
	private void handleCountryAction() {
		selectedCountry = countryComboBox.getValue();
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

	private File selectedCVFile;

	@FXML
	private Button btnSelectCV;

	@FXML
	private void selectCV() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("CV Dosyası Seç");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

		Stage stage = (Stage) btnSelectCV.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			selectedCVFile = file;
			cvComboBox.setPromptText(file.getAbsolutePath());
		}
	}

	public File getSelectedCVFile() {
		return selectedCVFile;
	}

	@FXML
	TextField fullName;
	String enteredFullName;
	@FXML
	TextField userName;
	String enteredUserName;
	@FXML
	PasswordField password;
	String enteredPassword;
	@FXML
	TextField phoneNumber;
	String enteredPhoneNumber;
	@FXML
	TextField email;
	String enteredEmail;

	@FXML
	private Label successMessageLabel;

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

	@FXML
	public void signUp(ActionEvent event) throws SQLException {
		enteredFullName = fullName.getText();
		if (!checkFullName(enteredFullName)) {
			fullName.setPromptText("Invalid name.");
			fullName.clear();
			displayWrongInputLabel();
			return;
		}
		enteredUserName = userName.getText();
		if (!checkUsername(enteredUserName)) {
			userName.setPromptText("Invalid username.");
			userName.clear();
			displayWrongInputLabel();
			return;
		}

		enteredPassword = password.getText();
		if (!checkPassword(enteredPassword)) {
			password.setPromptText("Invalid password.");
			password.clear();
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
		Random random = new Random();

        int randomDigit = random.nextInt(100);
		String sql2 = "SELECT * FROM librarians WHERE librarianid =" + randomDigit + "";
		while(executeQuery(sql2).next()) {
	        randomDigit = random.nextInt(100);
			sql2 = "SELECT * FROM librarians WHERE librarianid =" + randomDigit + "";
		}
		
		String sql = "INSERT INTO librarians (librarianid, librarianfullname, librarianphonenumber, librarianusername, librarianpassword, librarianstartdate, librarianemail) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		LocalDate localDate = LocalDate.now();
		Date sqlDate = Date.valueOf(localDate); 
		addToDatabase(sql, String.valueOf(randomDigit), enteredFullName, selectedCountry + enteredPhoneNumber, enteredUserName, enteredPassword,
				sqlDate, enteredEmail);
		successMessageLabel.setText("          Your application has been submitted.\r\n"
				+ "       Please log in to your account to check your application status.");
		successMessageLabel.setVisible(true);
		successMessageLabel.setStyle("-fx-background-color: #5F9EA0;");
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
		fadeOut.play();
		fullName.clear();
		userName.clear();
		password.clear();
		phoneNumber.clear();
		email.clear();
	}
	
	private void addToDatabase(String sql, String enteredID, String enteredFullName, String enteredNumber,
			String enteredUsername, String enteredPassword, Date date, String enteredEmail) {
		try {
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
					"postgres", "eren20044");
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, Long.parseLong(enteredID));
			stmt.setString(2, enteredFullName);
			stmt.setString(3, enteredNumber);
			stmt.setString(4, enteredUsername);
			stmt.setString(5, enteredPassword);
			stmt.setDate(6, date);
			stmt.setString(7, enteredEmail);
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
		String sql = "SELECT * FROM librarians WHERE librarianemail = " + "'" + enteredEmail + "'";
		ResultSet rs = executeQuery(sql);
		if (rs.next()) {
			return false;
		}
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		return enteredEmail.matches(regex);
	}

	private boolean checkPassword(String password) {
		String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
		return password.matches(regex);
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
