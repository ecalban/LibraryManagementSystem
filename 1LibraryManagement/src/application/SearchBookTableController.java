package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;

public class SearchBookTableController {

	static ArrayList<Book> bookList;
	
	@FXML
	TableView<Book> searchBookTable;
	
	@FXML
	TableColumn<Book, Integer> bookIdColumn;
	@FXML
	TableColumn<Book, String> bookTitleColumn;
	@FXML
	TableColumn<Book, String> bookAuthorColumn;
	@FXML
	TableColumn<Book, String> bookDescriptionColumn;
	@FXML
	TableColumn<Book, String> bookCategoryColumn;
	@FXML
	TableColumn<Book, String> bookStatusColumn;

	@SuppressWarnings("deprecation")
	@FXML
	public void initialize() {
		bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
		bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
		bookDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("bookDescription"));
		bookCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("bookCategory"));
		bookStatusColumn.setCellValueFactory(new PropertyValueFactory<>("bookStatus"));
		searchBookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


		ObservableList<Book> observableList = FXCollections.observableArrayList(SearchBookTableController.bookList);
        searchBookTable.setItems(observableList);
		searchBookTable.setSelectionModel(null);
	}

}

