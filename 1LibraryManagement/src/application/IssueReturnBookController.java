package application;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class IssueReturnBookController {

	@FXML
	public void initialize() {
		issueMessageLabel.setVisible(false);
		returnMessageLabel.setVisible(false);
	}
	
	@FXML
	private Label issueMessageLabel;
	@FXML
	private Label returnMessageLabel;
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
	TextField returnStudentID;
	String enteredReturnStudentID;

	@FXML
	public void issueBook(ActionEvent e) throws SQLException {
		enteredIssueStudentID = issueStudentID.getText();
		if (!checkIssueStudentID(enteredIssueStudentID)) {
			issueStudentID.setPromptText("The entered ID is not in use or is invalid.");
			issueStudentID.clear();
			displayAlert(issueMessageLabel ,"           Invalid input.",  "#D9534F");
			return;
		}
		enteredIssueBookID = issueBookID.getText();
		if (!checkIssueBookID(enteredIssueBookID)) {
			issueBookID.setPromptText("The entered ID is not in use or is invalid.");
			issueBookID.clear();
			displayAlert(issueMessageLabel, "           Invalid input.",  "#D9534F");
			return;
		}
		String sql = "SELECT * FROM books WHERE bookid = " + "" + Long.parseLong(enteredIssueBookID) + "";
		ResultSet rsBook = executeQuery(sql);
		String sql2 = "SELECT * FROM students WHERE studentid = " + "" + Long.parseLong(enteredIssueStudentID) + "";
		ResultSet rsStudent = executeQuery(sql2);
		if (rsBook.next() && rsStudent.next()) {
			Array studentBorrowedBooks = rsStudent.getArray("studentborrowedbooks");
			Array studentReturnDates = rsStudent.getArray("studentreturndate");
			Long[] tempBorrowedBooks = (Long[]) studentBorrowedBooks.getArray();
			Long[] tempForAddingBorrowed = new Long[tempBorrowedBooks.length + 1];
			String[] tempReturnDates = (String[]) studentReturnDates.getArray();
			String[] tempForAddingReturn = new String[tempBorrowedBooks.length + 1];
			Array bookWhoIssued = rsBook.getArray("bookswhoissued");
			Long[] tempWhoIssued = (Long[]) bookWhoIssued.getArray();
			Long[] tempForAddingWhoIssued = new Long[tempWhoIssued.length + 1];
			String bookStatus = rsBook.getString(6);
			int bookStock = rsBook.getInt(7);
			int bookStockIssued = rsBook.getInt(8);
			if (bookStockIssued >= bookStock) {
				bookStatus = "Unavailable";
				displayAlert(issueMessageLabel, "     Book is out of stock.", "#D9534F");
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
			for (int i = 0; i < tempWhoIssued.length; i++) {
				tempForAddingWhoIssued[i] = tempWhoIssued[i];
			}
			tempForAddingWhoIssued[tempWhoIssued.length] = rsStudent.getLong(1);
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
					"UPDATE books SET bookstatus = ?, stockissued = ?, bookswhoissued = ? WHERE bookid = ?");
			Array whoIssuedArray = con.createArrayOf("bigint", tempForAddingWhoIssued);
			stmt2.setString(1, bookStatus);
			stmt2.setInt(2, bookStockIssued);
			stmt2.setLong(4, rsBook.getLong(1));
			stmt2.setArray(3, whoIssuedArray);
			stmt2.executeUpdate();	
			con.close();
			displayAlert(issueMessageLabel, "  Issued until " + formattedDate , "#5F9EA0");
		}
	}
	
	private void displayAlert(Label label ,String str, String color) {
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), label);
		label.setText(str);
		label.setVisible(true);
		label.setStyle("-fx-background-color:" + color + ";");
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> label.setVisible(false));
		fadeOut.play();
	}
	
	@FXML
	public void returnBook(ActionEvent e) throws SQLException {
		enteredReturnStudentID = returnStudentID.getText();
		if (!checkIssueStudentID(enteredReturnStudentID)) {
			returnStudentID.setPromptText("The entered ID is not in use or is invalid.");
			returnStudentID.clear();
			displayAlert(returnMessageLabel, "                    Invalid input.",  "#D9534F");
			return;
		}
		enteredReturnBookID = returnBookID.getText();
		if (!checkIssueBookID(enteredReturnBookID)) {
			returnBookID.setPromptText("The entered ID is not in use or is invalid.");
			returnBookID.clear();
			displayAlert(returnMessageLabel, "                    Invalid input.",  "#D9534F");
			return;
		}
		String sql = "SELECT * FROM books WHERE bookid = " + "" + Long.parseLong(enteredReturnBookID) + "";
		ResultSet rsBook = executeQuery(sql);
		String sql2 = "SELECT * FROM students WHERE studentid = " + "" + Long.parseLong(enteredReturnStudentID) + "";
		ResultSet rsStudent = executeQuery(sql2);
		if (rsBook.next() && rsStudent.next()) {
			Array bookWhoIssued = rsBook.getArray("bookswhoissued");
			Long[] tempWhoIssued = (Long[]) bookWhoIssued.getArray();
			int c = 0;
			for(int i = 0;i<tempWhoIssued.length;i++) {
				if(tempWhoIssued[i] == rsStudent.getLong(1));
				c = 1;
			}
			if(c == 0) {
				displayAlert(returnMessageLabel, "  The student doesn't have the book.",  "#D9534F");
				return;
			}
			Array studentBorrowedBooks = rsStudent.getArray("studentborrowedbooks");
			Array studentReturnDates = rsStudent.getArray("studentreturndate");
			String bookStatus = rsBook.getString(6);
			int bookStock = rsBook.getInt(7);
			int bookStockIssued = rsBook.getInt(8);
			bookStockIssued -= 1;
			if (bookStockIssued < bookStock) {
				bookStatus = "Available";
			}
			int indexForDeletingDB = 0;
			for(int i = 0;i<tempWhoIssued.length;i++) {
				if(tempWhoIssued[i] == rsStudent.getLong(1)) {
					indexForDeletingDB = i;
					break;
				}
			}
			System.out.println("RAMR");
			int lateFee = calculateLateFee(studentReturnDates, indexForDeletingDB);
			System.out.println("SAMŞDL");
			studentBorrowedBooks = deleteIndexInArrayLong(studentBorrowedBooks, indexForDeletingDB);
			System.out.println("FSŞAFA");
			studentReturnDates = deleteIndexInArrayString(studentReturnDates, indexForDeletingDB);
			System.out.println("ŞA SŞFL");
			bookWhoIssued = deleteIndexInArrayLong(bookWhoIssued, indexForDeletingDB);
			System.out.println("FAŞLFŞLAS");
			System.out.println("ŞSADŞLSA ");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
					"postgres", "eren20044");
			PreparedStatement stmt = con.prepareStatement(
					"UPDATE students SET studentborrowedbooks = ?, studentreturndate = ?, studentlatefee = ? WHERE studentid = ?");
			System.out.println("FŞLASŞLA");
			stmt.setArray(1, studentBorrowedBooks);
			stmt.setArray(2, studentReturnDates);
			stmt.setLong(4, rsStudent.getLong(1));
			stmt.setInt(3, lateFee);
			stmt.executeUpdate();
			PreparedStatement stmt2 = con.prepareStatement(
					"UPDATE books SET bookstatus = ?, stockissued = ?, bookswhoissued = ? WHERE bookid = ?");
			stmt2.setString(1, bookStatus);
			stmt2.setInt(2, bookStockIssued);
			stmt2.setLong(4, rsBook.getLong(1));
			stmt2.setArray(3, bookWhoIssued);
			stmt2.executeUpdate();	
			con.close();
			displayAlert(returnMessageLabel, "                   Book returned.", "#5F9EA0");
		}
	}
	
	private int calculateLateFee(Array array, int index) throws SQLException {
	    String[] tempArray = (String[]) array.getArray();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate date = LocalDate.parse(tempArray[index], formatter);
	    LocalDate today = LocalDate.now();
	    int daysBetween = (int) ChronoUnit.DAYS.between(date, today);
		return daysBetween*2;
	}

	private Array deleteIndexInArrayString(Array array, int index) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
				"postgres", "eren20044");
	    String[] original = (String[]) array.getArray();
	    String[] newArray = new String[original.length - 1];
	    for (int i = 0, j = 0; i < original.length; i++) {
	        if (i != index) {
	            newArray[j++] = original[i];
	        }
	    }
	    Array array1 = con.createArrayOf("varchar", newArray);
	    con.close();
	    return array1;
	}

	private Array deleteIndexInArrayLong(Array array, int index) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
				"postgres", "eren20044");
		Long[] original = (Long[]) array.getArray();
		Long[] newArray = new Long[original.length - 1];
	    for (int i = 0, j = 0; i < original.length; i++) {
	        if (i != index) {
	            newArray[j++] = original[i];
	        }
	    }
	    Array array1 = con.createArrayOf("bigint", newArray);
	    con.close();
	    return array1;
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
		con.close();
		return rs;
	}

}
