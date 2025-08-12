package application;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FindStudentPopUpController {

	static ResultSet rs;

	@FXML
	Label idLabel;
	@FXML
	Label nameLabel;
	@FXML
	Label numberLabel;
	@FXML
	Label departmentLabel;
	@FXML
	Label emailLabel;
	@FXML
	Label membershipLabel;
	@FXML
	Label issuedIDLabel;
	@FXML
	Label issuedNameLabel;
	@FXML
	Label issuedReturnLabel;

	@FXML
	public void initialize() throws SQLException {
		if (rs.next()) {
			idLabel.setText(String.valueOf(rs.getLong(1)));
			nameLabel.setText(rs.getString(2) + rs.getString(3));
			numberLabel.setText(rs.getString(4));
			departmentLabel.setText(rs.getString(5));
			emailLabel.setText(rs.getString(6));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = sdf.format(rs.getDate(7));
			membershipLabel.setText(dateString);
			Array studentIssuedID = rs.getArray(8);
			Long[] tempBorrowedBooks = (Long[]) studentIssuedID.getArray();
			String issued = "";
			for (int i = 0; i < tempBorrowedBooks.length; i++) {
				if (i == tempBorrowedBooks.length - 1) {
					issued += String.valueOf(tempBorrowedBooks[i]) + ". ";

				} else {
					issued += String.valueOf(tempBorrowedBooks[i]) + ", ";
				}
			}
			issuedIDLabel.setText(issued);
			Array studentReturn = rs.getArray(9);
			String[] tempReturnDates = (String[]) studentReturn.getArray();
			String returnDates = "";
			for (int i = 0; i < tempReturnDates.length; i++) {
				if (i == tempReturnDates.length - 1) {
					returnDates += String.valueOf(tempReturnDates[i]) + ". ";

				} else {
					returnDates += String.valueOf(tempReturnDates[i]) + ", ";
				}
			}
			issuedReturnLabel.setText(returnDates);
			String issuedName = "";
			for (int i = 0; i < tempBorrowedBooks.length; i++) {
				String sql = "SELECT * FROM books WHERE bookid = ?";
				if (i == tempBorrowedBooks.length - 1) {
					issuedName += findNameOfBook(sql, tempBorrowedBooks[i]) + ". ";

				} else {
					issuedName += findNameOfBook(sql, tempBorrowedBooks[i]) + ", ";
				}
			}
			issuedNameLabel.setText(issuedName);

		}

	}

	private String findNameOfBook(String sql, Long id) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres",
				"eren20044");
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setLong(1, id);
		ResultSet rs = pst.executeQuery();
		con.close();
		if (rs.next()) {
			return rs.getString(2);

		}
		return null;
	}
}
