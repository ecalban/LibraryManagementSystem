package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FindBookPopUpController {

	static ResultSet rs;

	@FXML
	Label idLabel;
	@FXML
	Label nameLabel;
	@FXML
	Label statusLabel;
	@FXML
	Label stockLabel;
	@FXML
	Label authorLabel;
	@FXML
	Label descriptionLabel;
	@FXML
	Label categoryLabel;
	

	@FXML
	public void initialize() throws SQLException {
		descriptionLabel.setWrapText(true);
		if (rs.next()) {
			idLabel.setText(String.valueOf(rs.getLong(1)));
			nameLabel.setText(rs.getString(2));
			statusLabel.setText(rs.getString(6));
			stockLabel.setText(String.valueOf(rs.getInt(7)));
			authorLabel.setText(rs.getString(3));
			descriptionLabel.setText(rs.getString(4));
			categoryLabel.setText(rs.getString(5));
		}
	}

}
