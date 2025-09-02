package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LibrarianProfilePopUpController {

	static ResultSet rs;

	@FXML
	Label idLabel;
	@FXML
	Label nameLabel;
	@FXML
	Label phoneNumberLabel;
	@FXML
	Label usernameLabel;
	@FXML
	Label passwordLabel;
	@FXML
	Label startdateLabel;
	@FXML
	Label emailLabel;
	

	@FXML
	public void initialize() throws SQLException {
		if (rs.next()) {
			idLabel.setText(String.valueOf(rs.getLong(1)));
			nameLabel.setText(rs.getString(2));
			phoneNumberLabel.setText(rs.getString(3));
			usernameLabel.setText(rs.getString(4));
			passwordLabel.setText(rs.getString(5));
			startdateLabel.setText(rs.getDate(6).toString());
			emailLabel.setText(rs.getString(7));
		}
	}

}
