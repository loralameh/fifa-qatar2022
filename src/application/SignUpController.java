package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class SignUpController implements Initializable {
	
	@FXML
	RadioButton rdbSignUpMale, rdbSignUpFemale;
	
	@FXML
	Button signUp, sign_up;
	
	@FXML
	Label lblSignUpError, lblLoginError;
	
	@FXML
	TextField txtSignUpName, txtSignUpEmail, txtSignUpLocation, txtLoginEmail, txtLoginPassword;
	@FXML
	PasswordField txtSignUpPassword;
	@FXML
	Hyperlink linkToLogin;
	
	@FXML
	BorderPane signUpBorderPane;
	
	ConnectionClass connectionClass;
	
	public SignUpController() {
		connectionClass = new ConnectionClass();
	}
	
	
	public void signUPButton() {
		
		ToggleGroup group1 = new ToggleGroup();
		rdbSignUpMale.setToggleGroup(group1);
		rdbSignUpFemale.setToggleGroup(group1);
		
		sign_up.setOnAction(e-> {
			
			lblSignUpError.setText("");
			lblSignUpError.setVisible(false);
			
			String username = txtSignUpEmail.getText();
			String password = txtSignUpPassword.getText();
			String name = txtSignUpName.getText();
			String location = txtSignUpLocation.getText(); 
			String gender = "";
			if(rdbSignUpMale.isSelected()) {
				gender = "Male";
			}else if(rdbSignUpFemale.isSelected()) {
				gender = "Female";
			}
			
			if(username == null || password == null || name == null || location == null
					|| gender == null || username.length() < 1 || password.length() < 1
					|| name.length() < 1 || location.length() < 1 || gender.length() < 1) 
			{
				lblSignUpError.setText("Enter all fields and select gender");
				lblSignUpError.setVisible(true);
				return;
			}
			
			ResultSet result = connectionClass.selectQuery("Call get_user('" + username + "')");
			if(result != null) {
				try {
					while(result.next()) {
						lblSignUpError.setText("This email already exists try to log in instead");
						lblSignUpError.setVisible(true);
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					return;
				}
			}
			
			String user_image = "", user_type = "user", user_status = "active";
			
			String sqlNonQuery = "Call insert_user('" + username + "','" + password + "','" + name + "','" + 
			user_image + "','" + user_type + "','" + user_status + "',50,'" + gender + "', '" + location + "')";
			
			int result2 = connectionClass.nonQuery(sqlNonQuery);
			System.out.println("Result = " + result2);					
			
			Alert a= new Alert(AlertType.CONFIRMATION);
            a.setContentText("Great! we have created an account for you :) \nHope your excited as much we are");
            a.setHeaderText("Login ");
            a.show();

			try {
				signUpBorderPane.setCenter(FXMLLoader.load(getClass().getResource("LogInPage.fxml")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});
	}
	
	public void getLoginPage() {		
		linkToLogin.setOnAction(e ->{
			try {
				signUpBorderPane.setCenter(FXMLLoader.load(getClass().getResource("LogInPage.fxml")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblSignUpError.setText("");
		lblSignUpError.setVisible(false);
	}

}
