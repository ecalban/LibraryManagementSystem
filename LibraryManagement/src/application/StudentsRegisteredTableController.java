package application;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	TableColumn<StudentForApprove, String> studentEmailColumn;
	@FXML
	TableColumn<StudentForApprove, String> studentDepartmentColumn;

	@SuppressWarnings("deprecation")
	@FXML
	public void initialize() {
		studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		studentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentFirstName"));
		studentLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentLastName"));
		studentPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("studentPhoneNumber"));
		studentEmailColumn.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));
		studentDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("studentDepartment"));
		studentApproveTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		ObservableList<StudentForApprove> observableList = FXCollections
				.observableArrayList(StudentsRegisteredTableController.studentApproveList);
		studentApproveTable.setItems(observableList);
		studentApproveTable.setSelectionModel(null);

	}

}
