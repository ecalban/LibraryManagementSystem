package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class StudentsRegisteredTableController {

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
				.observableArrayList(StudentsRegisteredTableController.studentApproveList);
		studentApproveTable.setItems(observableList);
	}

}
