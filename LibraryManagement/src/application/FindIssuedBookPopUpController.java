package application;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FindIssuedBookPopUpController {

	static ResultSet rs;

	@FXML
	Label idLabel;
	@FXML
	Label nameLabel;
	@FXML
	Label statusLabel;
	@FXML
	Label stockLabel;
	@FXML
	Label borrowedCopiesLabel;
	@FXML
	Label currentBorrowersIDLabel;
	@FXML
	Label currentBorrowersnNameLabel;
	@FXML
	Label returnDateLabel;

	@FXML
	public void initialize() throws SQLException {
		if (rs.next()) {
			idLabel.setText(String.valueOf(rs.getLong(1)));
			nameLabel.setText(rs.getString(2));
			statusLabel.setText(rs.getString(6));
			stockLabel.setText(String.valueOf(rs.getInt(7)));
			borrowedCopiesLabel.setText(String.valueOf(rs.getInt(8)));
			Array currentBorrowerID = rs.getArray(9);
			Long[] tempBorrowers = (Long[]) currentBorrowerID.getArray();
			String borrowers = "";
			for (int i = 0; i < tempBorrowers.length; i++) {
				if (i == tempBorrowers.length - 1) {
					borrowers += String.valueOf(tempBorrowers[i]) + ". ";

				} else {
					borrowers += String.valueOf(tempBorrowers[i]) + ", ";
				}
			}
			currentBorrowersIDLabel.setText(borrowers);
			String borrowersName = "";
			String returnDates = "";
			ArrayList<Long> list = new ArrayList<>();
			for (int i = 0; i < tempBorrowers.length; i++) {
				String sql = "SELECT * FROM students WHERE studentid = ?";
				if (i == tempBorrowers.length - 1) {
					borrowersName += findStudentInfo(sql, tempBorrowers[i], rs.getLong(1))[0] + ". ";
					if (!list.contains(tempBorrowers[i])) {
						returnDates += findStudentInfo(sql, tempBorrowers[i], rs.getLong(1))[1] + ". ";
						list.add(tempBorrowers[i]);
					}
				} else {
					borrowersName += findStudentInfo(sql, tempBorrowers[i], rs.getLong(1))[0] + ", ";
					if (!list.contains(tempBorrowers[i])) {
						returnDates += findStudentInfo(sql, tempBorrowers[i], rs.getLong(1))[1] + ", ";
						list.add(tempBorrowers[i]);
					}
				}
			}
			returnDates = returnDates.substring(0, returnDates.length() - 4);
			returnDates += ".";
			currentBorrowersnNameLabel.setText(borrowersName);
			returnDateLabel.setText(returnDates);
		}
	}

	private String[] findStudentInfo(String sql, Long studentID, Long bookID) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres",
				"eren20044");
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setLong(1, studentID);
		ResultSet rs = pst.executeQuery();
		con.close();
		String[] studentInfo = { "", "" };
		if (rs.next()) {
			studentInfo[0] = rs.getString(2);
			String returnDates = "";
			Array studentReturnDates = rs.getArray(9);
			String[] sreturnDates = (String[]) studentReturnDates.getArray();
			Array studentBorrowedBooks = rs.getArray(8);
			Long[] borrowedBooks = (Long[]) studentBorrowedBooks.getArray();
			for (int i = 0; i < borrowedBooks.length; i++) {

				if (borrowedBooks[i].equals(bookID)) {
					returnDates += sreturnDates[i] + ", ";
				}
			}
			studentInfo[1] = returnDates;

			return studentInfo;
		}
		return null;
	}
}
