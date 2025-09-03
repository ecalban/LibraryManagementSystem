package application;

import java.io.IOException;
import dao.DBtoArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SearchBookController {

	@FXML
	Hyperlink githubLink;
	
	@FXML
	public void initialize() {
		githubLink.setOnAction(e -> {
		    try {
		        java.awt.Desktop.getDesktop().browse(new java.net.URI("https://github.com/ecalban"));
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		});
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

	@FXML
	TextField searchBookTextField;
	@FXML
	Label searchBookLabel;

	@FXML
	public void searchBook(ActionEvent event) throws IOException {
		String search = searchBookTextField.getText();
		try {
			if (search.length() > 256) {
				searchBookLabel.setText("Name or author of the book" + "\n(Enter less than" + "\n256 characters)");
				return;
			}
			if (search.length() == 0) {
				searchBookLabel.setText("Name or author of the book" + "\n(Input cannot be empty)");
				return;
			}
		} catch (Exception e) {
			System.out.println("An error : " + e);
		}
		String sql = "SELECT * FROM Books WHERE bookTitle ILIKE '%" + search + "%' OR bookAuthor ILIKE '%" + search
				+ "%'";
		try {
			SearchBookTableController.bookList = DBtoArrayList.SearchedBookToArrayList(sql);
		} catch (Exception e) {
			System.out.println("An error: " + e);
		}
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SearchBookTable.fxml"));
		Scene scene = new Scene(loader.load());
		popupStage.setScene(scene);	
		popupStage.setResizable(false);
		popupStage.showAndWait();

	}
}
