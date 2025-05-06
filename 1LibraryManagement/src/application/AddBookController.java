package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import dao.DBtoArrayList;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class AddBookController {

	@FXML
	private ComboBox<String> categoryComboBox;
	@FXML
	private Label successMessageLabel;
	
	@FXML
	public void initialize() {
		categoryComboBox.setPromptText("Fiction");
		ArrayList<String> categoryList = DBtoArrayList.categoryToArrayList();
		for (int i = 0; i < categoryList.size(); i++) {
			categoryComboBox.getItems().add(categoryList.get(i));
		}
	successMessageLabel.setVisible(false);

	}

	@FXML
	private void handleComboBoxAction() {
		selectedItem = categoryComboBox.getValue();
	}

	int bookId;
	@FXML
	TextField bookName;
	String enteredBookName;
	@FXML
	TextField authorName;
	String enteredAuthorName;
	@FXML
	TextField description;
	String enteredDescription;
	@FXML
	TextField stock;
	String enteredStock;
	String selectedItem;

	@FXML
	public void addBook(ActionEvent event) throws SQLException {
		enteredBookName = bookName.getText();
		if (!checkBookName(enteredBookName)) {
			bookName.setPromptText("The ID is already in use or invalid.");
			bookName.clear();
			return;
		}
		enteredAuthorName = authorName.getText();
		if (!checkAuthorName(enteredAuthorName)) {
			authorName.setPromptText("Invalid first name.");
			authorName.clear();
			return;
		}
		enteredDescription = description.getText();
		if (!checkDescription(enteredDescription)) {
			description.setPromptText("Invalid last name.");
			description.clear();
			return;
		}
		enteredStock = stock.getText();
		if (!checkStock(enteredStock)) {
			stock.setPromptText("The username is already in use or invalid.");
			stock.clear();
			return;
		}
		if (selectedItem == null) {
			selectedItem = "Fiction";
		}
		randomIdForBook();
		String sql = "INSERT INTO books (bookid, booktitle, bookauthor, bookdescription, bookcategory, bookstatus, bookstock) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		addToDatabase(sql, bookId, enteredBookName, enteredAuthorName, enteredDescription, selectedItem, "Available",
				enteredStock);

		successMessageLabel.setVisible(true);
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), successMessageLabel);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> successMessageLabel.setVisible(false));
		fadeOut.play();
		bookName.clear();
		authorName.clear();
		description.clear();
		stock.clear();
	}

	private void randomIdForBook() throws SQLException {
		Random random = new Random();
		int randomId = random.nextInt(1000);
		String sql = "SELECT * FROM books WHERE bookid = " + "'" + randomId + "'";
		ResultSet rs = executeQuery(sql);
		while(rs.next()) {
			randomId = random.nextInt(1000);
			rs = executeQuery(sql);
		}
		bookId = randomId;
	}

	private void addToDatabase(String sql, int bookId, String enteredBookName, String enteredAuthorName,
			String enteredDescription, String selectedItem, String bookStatus, String enteredStock) {
		try {
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
					"postgres", "eren20044");
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, bookId);
			stmt.setString(2, enteredBookName);
			stmt.setString(3, enteredAuthorName);
			stmt.setString(4, enteredDescription);
			stmt.setString(5, selectedItem);
			stmt.setString(6, bookStatus);
			stmt.setInt(7, Integer.parseInt(enteredStock));
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
	}

	private boolean checkBookName(String enteredBookName) {
		if (enteredBookName == null || enteredBookName.trim().isEmpty()) {
			return false;
		}
		if (enteredBookName.length() > 100) {
			return false;
		}
		return enteredBookName.matches("[a-zA-Z0-9\\s]*");
	}

	private boolean checkAuthorName(String enteredAuthorName) throws SQLException {
		if (enteredAuthorName == null || enteredAuthorName.trim().isEmpty()) {
			return false;
		}
		if (enteredAuthorName.length() > 50) {
			return false;
		}
		return enteredAuthorName.matches("[a-zA-Z\\s]*");

	}

	private boolean checkDescription(String enteredDescription) {
		if (enteredDescription == null || enteredDescription.trim().isEmpty()) {
			return false;
		}
		if (enteredDescription.length() > 500) {
			return false;
		}
		return true;
	}

	private boolean checkStock(String stock) {
		if(Integer.parseInt(stock)<0 || Integer.parseInt(stock)>10) {
			return false;
		}
		return true;
	}

	private ResultSet executeQuery(String sql) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres",
				"eren20044");
		PreparedStatement pst = con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		return rs;
	}

}
