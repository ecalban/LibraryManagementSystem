package application;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import model.StudentForApprove;

public class ApproveTableController {

	static ArrayList<StudentForApprove> studentApproveList;

	@FXML
	TableView<StudentForApprove> studentApproveTable;

	@FXML
	TableColumn<StudentForApprove, Long> studentIdColumn;
	@FXML
	TableColumn<StudentForApprove, String> studentFirstNameColumn;
	@FXML
	TableColumn<StudentForApprove, String> studentLastNameColumn;
	@FXML
	TableColumn<StudentForApprove, String> studentPhoneNumberColumn;
	@FXML
	TableColumn<StudentForApprove, String> studentUsernameColumn;
	@FXML
	TableColumn<StudentForApprove, String> studentPasswordColumn;

	@SuppressWarnings("deprecation")
	@FXML
	public void initialize() {
		
		studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		studentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentFirstName"));
		studentLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentLastName"));
		studentPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("studentPhoneNumber"));
		studentUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("studentUsername"));
		studentPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("studentPassword"));
		studentApproveTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		ObservableList<StudentForApprove> observableList = FXCollections
				.observableArrayList(ApproveTableController.studentApproveList);
		studentApproveTable.setItems(observableList);
		
		
	}

	@FXML
	public void rejectSelected(ActionEvent event) throws SQLException {
		if (studentApproveTable.getSelectionModel().isEmpty()) {
			return;
		}
		String sql = "DELETE FROM studentforapprove WHERE studentid = " + "'"
				+ studentApproveTable.getSelectionModel().getSelectedItem().getStudentId() + "'";
		executeUpdate(sql);
		studentApproveTable.getItems().removeAll(studentApproveTable.getSelectionModel().getSelectedItems());
	}

	@FXML
	public void acceptSelected(ActionEvent event) throws SQLException {
		if (studentApproveTable.getSelectionModel().isEmpty()) {
			return;
		}
		StudentForApprove studentForApprove = studentApproveTable.getSelectionModel().getSelectedItem();
		String[] emptyArray = new String[0];
		Student student = new Student(studentForApprove.getStudentId(), studentForApprove.getStudentFirstName(),
				studentForApprove.getStudentLastName(), studentForApprove.getStudentPhoneNumber(),
				studentForApprove.getStudentUsername(), studentForApprove.getStudentPassword(),
				LocalDate.now().toString(), emptyArray, emptyArray, 0);
		String sql = "INSERT INTO students (studentId, studentFirstName, studentLastName, studentPhoneNumber,"
				+ " studentUsername, studentPassword, studentmembershipdate, studentlatefee, studentborrowedbooks,"
				+ " studentreturndate)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		addToDatabase(sql, student.getStudentId(), student.getStudentFirstName(), student.getStudentLastName(),
				student.getStudentPhoneNumber(), student.getStudentUsername(), student.getStudentPassword(),
				student.getStudentMembershipDate(), student.getStudentBorrowedBooks(), student.getStudentReturnDate(),
				student.getStudentLateFee());
		String sql2 = "DELETE FROM studentforapprove WHERE studentid = " + "'"
				+ studentApproveTable.getSelectionModel().getSelectedItem().getStudentId() + "'";
		executeUpdate(sql2);
		studentApproveTable.getItems().removeAll(studentApproveTable.getSelectionModel().getSelectedItems());
	}

	private void addToDatabase(String sql, long studentId, String studentFirstName, String studentLastName,
			String studentPhoneNumber, String studentUsername, String studentPassword, String studentMembershipDate,
			String[] studentBorrowedBooks, String[] studentReturnDate, int studentLateFee) {
		try {
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB",
					"postgres", "eren20044");
			PreparedStatement stmt = con.prepareStatement(sql);
			Array borrowedBooksArray = con.createArrayOf("bigint", studentBorrowedBooks);
			Array returnDateArray = con.createArrayOf("varchar", studentReturnDate);
			Date studentMembershipSqlDate = Date.valueOf(studentMembershipDate);
			stmt.setLong(1, studentId);
			stmt.setString(2, studentFirstName);
			stmt.setString(3, studentLastName);
			stmt.setString(4, studentPhoneNumber);
			stmt.setString(5, studentUsername);
			stmt.setString(6, studentPassword);
			stmt.setDate(7, studentMembershipSqlDate);
			stmt.setInt(8, studentLateFee);
			stmt.setArray(9, borrowedBooksArray);
			stmt.setArray(10, returnDateArray);
			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
	}

	private void executeUpdate(String sql) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryManagementDB", "postgres",
				"eren20044");
		PreparedStatement pst = con.prepareStatement(sql);
		pst.executeUpdate();
		con.close();
	}

}
