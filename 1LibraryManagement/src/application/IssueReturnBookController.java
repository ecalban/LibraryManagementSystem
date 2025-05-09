package application;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import dao.DBtoArrayList;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IssueReturnBookController {

	@FXML
	public void initialize() {
	}

	@FXML
	TextField issueStudentID;
	String enteredIssueStudentID;
	@FXML
	TextField issueBookID;
	String enteredIssueBookID;
	@FXML
	TextField returnBookID;
	String enteredReturnBookID;

	@FXML
	public void issueBook(ActionEvent e) throws SQLException {
		enteredIssueStudentID = issueStudentID.getText();
		if (!checkIssueStudentID(enteredIssueStudentID)) {
			issueStudentID.setPromptText("The entered ID is not in use or is invalid.");
			issueStudentID.clear();
			return;
		}
		enteredIssueBookID = issueBookID.getText();
		if (!checkIssueBookID(enteredIssueBookID)) {
			issueBookID.setPromptText("The entered ID is not in use or is invalid.");
			issueBookID.clear();
			return;
		}
		String sql = "SELECT * FROM books WHERE bookid = " + "" + Long.parseLong(enteredIssueBookID) + "";
		ResultSet rsBook = executeQuery(sql);
		String sql2 = "SELECT * FROM students WHERE studentid = " + "" + Long.parseLong(enteredIssueStudentID) + "";
		ResultSet rsStudent = executeQuery(sql2);
		if (rsBook.next() && rsStudent.next()) {
			System.out.println("asmfa");
			Array studentBorrowedBooks = rsStudent.getArray("studentborrowedbooks");
			Array studentReturnDates = rsStudent.getArray("studentreturndate");
			System.out.println();
			Long[] tempBorrowedBooks = (Long[]) studentBorrowedBooks.getArray();
			Long[] tempForAddingBorrowed = new Long[tempBorrowedBooks.length + 1];
			String[] tempReturnDates = (String[]) studentReturnDates.getArray();
			String[] tempForAddingReturn = new String[tempBorrowedBooks.length + 1];
			String bookStatus = rsBook.getString(6);
			int bookStock = rsBook.getInt(7);
			int bookStockIssued = rsBook.getInt(8);
			if (bookStockIssued >= bookStock) {
				bookStatus = "Unavailable";
				System.out.println("NO STOCK");
				return;
			}
			bookStockIssued += 1;
			for (int i = 0; i < tempBorrowedBooks.length; i++) {
				tempForAddingBorrowed[i] = tempBorrowedBooks[i];
			}
			tempForAddingBorrowed[tempBorrowedBooks.length] = rsBook.getLong(1);
			LocalDate today = LocalDate.now();
			LocalDate after15Days = today.plusDays(15);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = after15Days.format(formatter);
			for (int i = 0; i < tempReturnDates.length; i++) {
				tempForAddingReturn[i] = tempReturnDates[i];
			}
			tempForAddingReturn[tempReturnDates.length] = formattedDate;
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
					"postgres", "eren20044");
			PreparedStatement stmt = con.prepareStatement(
					"UPDATE students SET studentborrowedbooks = ?, studentreturndate = ? WHERE studentid = ?");
			Array borrowedArray = con.createArrayOf("bigint", tempForAddingBorrowed);
			Array returnArray = con.createArrayOf("varchar", tempForAddingReturn);
			stmt.setArray(1, borrowedArray);
			stmt.setArray(2, returnArray);
			stmt.setLong(3, rsStudent.getLong(1));
			stmt.executeUpdate();
			PreparedStatement stmt2 = con.prepareStatement(
					"UPDATE books SET bookstatus = ?, stockissued = ? WHERE bookid = ?");
			stmt2.setString(1, bookStatus);
			stmt2.setInt(2, bookStockIssued);
			stmt2.setLong(3, rsBook.getLong(1));
			stmt2.executeUpdate();
		}
	}

	private boolean checkIssueBookID(String enteredIssueBookID) throws SQLException {
		try {
			Long.parseLong(enteredIssueBookID);
		} catch (Exception e) {
			return false;
		}
		String sql = "SELECT * FROM books WHERE bookid = " + "" + Long.parseLong(enteredIssueBookID) + "";
		ResultSet rs = executeQuery(sql);
		if (!rs.next()) {
			return false;
		}
		return true;
	}

	private boolean checkIssueStudentID(String IssueStudentID) throws SQLException {
		try {
			Long.parseLong(IssueStudentID);
		} catch (Exception e) {
			return false;
		}
		String sql = "SELECT * FROM students WHERE studentid = " + "" + Long.parseLong(IssueStudentID) + "";
		ResultSet rs = executeQuery(sql);
		if (!rs.next()) {
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
