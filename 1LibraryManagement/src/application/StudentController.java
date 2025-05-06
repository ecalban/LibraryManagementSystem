package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentController {


    @FXML
    private Button profileButton;
    
	@FXML
	public void initialize() {
        profileButton.setText("▼");
	}
	
	@FXML
	VBox menuBox;
	
	
	@FXML
	public void profileButton(ActionEvent event) {
        boolean isVisible = menuBox.isVisible();
        
        menuBox.setVisible(!isVisible);  
        menuBox.setManaged(!isVisible); 
       
    
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
}
