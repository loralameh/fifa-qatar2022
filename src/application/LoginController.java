package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LoginController implements Initializable {
	
	@FXML
	Label lblLoginError;
	
	@FXML
	Button login, btnLogin;
	
	@FXML
	TextField txtLoginEmail;
	
	@FXML
	PasswordField txtLoginPassword;
	
	@FXML
	BorderPane loginBorderPane;
	
	@FXML
	Hyperlink linkToSignUp;
	
	User user;
	ConnectionClass connectionClass;
	
	public LoginController() {
		connectionClass = new ConnectionClass();
	}

	public void loginClicked() {	
		
		lblLoginError.setText("");
		lblLoginError.setVisible(false);
		
		String username = txtLoginEmail.getText();
		String password = txtLoginPassword.getText();
		
		if(username == null || username.length() < 1 || password == null || password.length() < 1) {
			lblLoginError.setText("Please Enter All Fields");
			lblLoginError.setVisible(true);
			return;
		}
		
		ResultSet result = connectionClass.selectQuery("Call get_user('" + username + "')");
		if(result != null) {
			try {				
				if(result.next()) {
					String pass = result.getString("password");
					if(!pass.equals(password)) {
						lblLoginError.setText("Incorrect password");
						lblLoginError.setVisible(true);
						return;		
					}
					String name = result.getString("name");
					String user_type = result.getString("user_type");
					String user_status = result.getString("user_status");
					int user_coins = result.getInt("user_coins");
					String gender = result.getString("gender");
					String location = result.getString("location");					
					
					user = new User(username, password, name, gender, user_type, user_status, location, user_coins);					
				}else {
					lblLoginError.setText("Invalid username or password");
					lblLoginError.setVisible(true);
					return;		
				}
				
				if(user != null) {
					try {						
						Statics.user = user;
						loginBorderPane.setCenter(FXMLLoader.load(getClass().getResource("baseTemplate.fxml")));
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.print(user.getName());				
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				return;
			}
		}else {
			lblLoginError.setText("Invalid username or password");
			lblLoginError.setVisible(true);
			return;			
		}
	}
	
	public void getSignUpPage() {
		
		linkToSignUp.setOnAction(e ->{
			try {
				loginBorderPane.setCenter(FXMLLoader.load(getClass().getResource("SignUpPage.fxml")));

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblLoginError.setText("");
		lblLoginError.setVisible(false);		
	}
	
}
