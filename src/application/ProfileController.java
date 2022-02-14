package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ProfileController implements Initializable {
	
	@FXML
	Label lblProfileName, lblProfileLocation, lblProfileEmail, lblProfileCoins, lblProfileStatus, lblProfileType, lblProfileGender;
	
	ConnectionClass connectionClass;
	
	private User user;
	
	public ProfileController() {
		connectionClass = new ConnectionClass();
		user = Statics.user;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(user != null) {
			lblProfileName.setText(user.getName().toUpperCase());
			lblProfileEmail.setText(user.getUsername().toUpperCase());
			lblProfileLocation.setText(user.getLocation().toUpperCase());
			lblProfileCoins.setText("Current Value: " + user.getCoins() + " Coins");
			lblProfileGender.setText(user.getGender().toUpperCase());
		}	
	}

}
