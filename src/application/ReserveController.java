package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ReserveController implements Initializable {
	
	@FXML
	Button reserveButton;
	
	@FXML 
	CheckBox b1,b2,b3,b4,a1,a2,a3,a4,ckboxGiftFriends;
	
	@FXML
	TextField friendEmail;
	
	@FXML
	Label choosenSeats;
	
	@FXML
	ComboBox<ComboItem> lstChooseGame;
	
	@FXML
	ImageView imageb1, imageb2,imageb3,imageb4,imagea1,imagea2,imagea3,imagea4;
	
	ArrayList<String> allChoosenSeats = new ArrayList<String>();
	
	ArrayList<ComboItem> games = new ArrayList<ComboItem>();
	
	ConnectionClass connectionClass;
	
	private User user;
	
	public ReserveController() {
		connectionClass = new ConnectionClass();
		user = Statics.user;
	}

	public void ReserveSeat() {
		
		reserveButton.setOnAction(e->{
			
			ComboItem item = lstChooseGame.getValue();
			
			Alert alert;
			if(item == null) {
				alert = new Alert(AlertType.ERROR);            
				alert.setContentText("Please Select A Game");
				alert.show();
	            return;
			}
			
			if(allChoosenSeats.size() < 1) {
				alert = new Alert(AlertType.ERROR);            
				alert.setContentText("Please Select A Seat");
				alert.show();
	            return;
			}
			
			boolean ckboxSelected = ckboxGiftFriends.isSelected();
			
			int totalPrice = allChoosenSeats.size() * 10; 
			if(user.getCoins() < totalPrice) {
				alert = new Alert(AlertType.ERROR);            
				alert.setContentText("The selected seats price is more than your coins");
				alert.show();
	            return;
			}
			
			String message = "";
			if(!ckboxSelected) {
				for(String seat : allChoosenSeats) {
					int seatId = 0, number = 0;
					System.out.println("seat " + seat);
					if(seat.contains("B")) {
						seatId += 4;
						number = Integer.parseInt(seat.split("B")[1]);
					}else {
						number = Integer.parseInt(seat.split("A")[1]);
					}
					seatId += number;
					
					String query = "Call insert_stadium_seats('" + user.getUsername() + "', " + item.getKey() + ", " + seatId + ", 'Booked');";
					connectionClass.nonQuery(query);
				}
				message = "Happy Time! \nYou have reserved " + allChoosenSeats.size() + " seates\n" + totalPrice + " coins will be deducted from your balance";				
			}else {
				String email = friendEmail.getText();
				if(email == null || email.isEmpty() || email == "") {
					alert = new Alert(AlertType.ERROR);            
					alert.setContentText("Enter your friend email");
					alert.show();
		            return;
				}
				
				String query = "Call get_user('" + email + "')";
				ResultSet resultSet = connectionClass.selectQuery(query);
				String username = "";
				if(resultSet != null) {
					try {
						if(resultSet.next()) {
							username = resultSet.getString("username");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
						return;
					}
					
					if(username == "") {
						alert = new Alert(AlertType.ERROR);            
						alert.setContentText("Incorrect friend email, please make sure your friend has already signed up");
						alert.show();
			            return;
					}
					
					for(String seat : allChoosenSeats) {
						int seatId = 0, number = 0;
						if(seat.contains("B")) {
							seatId += 4;
							number = Integer.parseInt(seat.split("B")[1]);
						}else {
							number = Integer.parseInt(seat.split("A")[1]);
						}
						seatId += number;
						
						String query2 = "Call insert_stadium_seats('" + username + "', " + item.getKey() + ", " + seatId + ", 'Booked');";
						connectionClass.nonQuery(query2);
					}
					message = "Happy Time! \nYou will send " + allChoosenSeats.size() + " seates to " + email + "\n" + totalPrice + " coins will be deducted from your balance";
				}else {
					alert = new Alert(AlertType.ERROR);            
					alert.setContentText("Incorrect friend email, please make sure your friend has already signed up");
					alert.show();
		            return;
				}
			}
			
			user.setCoins(user.getCoins() - totalPrice);
			Statics.user.setCoins(user.getCoins() - totalPrice);
			//Update user coins
			String query = "Call subtract_coins_from_user('" + user.getUsername() + "', " + totalPrice + ")";
			connectionClass.nonQuery(query);	
			
			alert = new Alert(AlertType.CONFIRMATION);          	          
			alert.setContentText(message);
			alert.show();
			
			allChoosenSeats = new ArrayList<String>();
			getAllSeats();
		});
	}
	
	public void getReservedSeats() throws IOException {
		
		int depth = 80;
		DropShadow borderGlow1 = new DropShadow();
		DropShadow borderGlow2 = new DropShadow();
		DropShadow borderGlow3 = new DropShadow();
		DropShadow borderGlow4 = new DropShadow();
		DropShadow borderGlow5 = new DropShadow();
		DropShadow borderGlow6 = new DropShadow();
		DropShadow borderGlow7 = new DropShadow();
		DropShadow borderGlow8 = new DropShadow();
		
	

		String s;
		String message="Your selected seat(s) :\n";
		
		ArrayList<CheckBox> checkBoxs = new ArrayList<CheckBox>();
		checkBoxs.add(a1);
		checkBoxs.add(a2);
		checkBoxs.add(a3);
		checkBoxs.add(a4);
		checkBoxs.add(b1);
		checkBoxs.add(b2);
		checkBoxs.add(b3);
		checkBoxs.add(b4);
		
		for(int i = 0; i<checkBoxs.size(); i++) {
			
			if(!checkBoxs.get(i).isDisabled() && checkBoxs.get(i).isSelected()) {
				s= checkBoxs.get(i).getText()+"\n";
				choosenSeats.setText(message+=s);
				if(!allChoosenSeats.contains(checkBoxs.get(i).getText()))
				allChoosenSeats.add(checkBoxs.get(i).getText());
				
				
			}else if(!checkBoxs.get(i).isSelected()){
				 s= "";
					choosenSeats.setText(message+=s);
					if(allChoosenSeats.contains(checkBoxs.get(i).getText()))
						allChoosenSeats.remove(checkBoxs.get(i).getText());	
			}
		}
		
		if(checkBoxs.get(0).isSelected()) {
			borderGlow1.setColor(Color.GREEN);
			borderGlow1.setWidth(depth);
			borderGlow1.setHeight(depth);
			borderGlow1.setBlurType(BlurType.ONE_PASS_BOX);
			imagea1.setEffect(borderGlow1);	
			
		}else  {
			 borderGlow1.setColor(Color.TRANSPARENT);
			 imagea1.setEffect(borderGlow1);
			
		}
		if(checkBoxs.get(1).isSelected()) {
			borderGlow2.setColor(Color.GREEN);
			borderGlow2.setWidth(depth);
			borderGlow2.setHeight(depth);
			borderGlow2.setBlurType(BlurType.ONE_PASS_BOX);
			imagea2.setEffect(borderGlow2);
		
		}else {
			 borderGlow2.setColor(Color.TRANSPARENT);
			 imagea2.setEffect(borderGlow2);
			 
		}
		if(checkBoxs.get(2).isSelected()) {
			borderGlow3.setColor(Color.GREEN);
			borderGlow3.setWidth(depth);
			borderGlow3.setHeight(depth);
			borderGlow3.setBlurType(BlurType.ONE_PASS_BOX);
			imagea3.setEffect(borderGlow3);	
			
		}else if(!checkBoxs.get(2).isSelected()) {
			 borderGlow3.setColor(Color.TRANSPARENT);
			 imagea3.setEffect(borderGlow3);
			
		}
		if(checkBoxs.get(3).isSelected()) {
			borderGlow4.setColor(Color.GREEN);
			borderGlow4.setWidth(depth);
			borderGlow4.setHeight(depth);
			borderGlow4.setBlurType(BlurType.ONE_PASS_BOX);
			imagea4.setEffect(borderGlow4);
			
		}else if(!checkBoxs.get(3).isSelected()) {
			 borderGlow4.setColor(Color.TRANSPARENT);
			 imagea4.setEffect(borderGlow4);
			 
		}if(checkBoxs.get(4).isSelected()) {
			borderGlow5.setColor(Color.GREEN);
			borderGlow5.setWidth(depth);
			borderGlow5.setHeight(depth);
			borderGlow5.setBlurType(BlurType.ONE_PASS_BOX);
			imageb1.setEffect(borderGlow5);	
			
		}else if(!checkBoxs.get(4).isSelected()) {
			 borderGlow5.setColor(Color.TRANSPARENT);
			 imageb1.setEffect(borderGlow5);
			
		}
		if(checkBoxs.get(5).isSelected()) {
			borderGlow6.setColor(Color.GREEN);
			borderGlow6.setWidth(depth);
			borderGlow6.setHeight(depth);
			borderGlow6.setBlurType(BlurType.ONE_PASS_BOX);
			imageb2.setEffect(borderGlow6);
			
		}else if(!checkBoxs.get(5).isSelected()) {
			 borderGlow6.setColor(Color.TRANSPARENT);
			 imageb2.setEffect(borderGlow6);
			 
		}if(checkBoxs.get(6).isSelected()) {
			borderGlow7.setColor(Color.GREEN);
			borderGlow7.setWidth(depth);
			borderGlow7.setHeight(depth);
			borderGlow7.setBlurType(BlurType.ONE_PASS_BOX);
			imageb3.setEffect(borderGlow7);	
		
		}else if(!checkBoxs.get(6).isSelected()) {
			 borderGlow7.setColor(Color.TRANSPARENT);
			 imageb3.setEffect(borderGlow7);
			
		}if(checkBoxs.get(7).isSelected()) {
			borderGlow8.setColor(Color.GREEN);
			borderGlow8.setWidth(depth);
			borderGlow8.setHeight(depth);
			borderGlow8.setBlurType(BlurType.ONE_PASS_BOX);
			imageb4.setEffect(borderGlow8);	
		
		}else if(!checkBoxs.get(7).isSelected()) {
			 borderGlow8.setColor(Color.TRANSPARENT);
			 imageb4.setEffect(borderGlow8);		
		}
}
	
	public void getAllSeats() {
		
		unGlowAll();
		
		ArrayList<CheckBox> checkBoxs = new ArrayList<CheckBox>();
		checkBoxs.add(a1);
		checkBoxs.add(a2);
		checkBoxs.add(a3);
		checkBoxs.add(a4);
		checkBoxs.add(b1);
		checkBoxs.add(b2);
		checkBoxs.add(b3);
		checkBoxs.add(b4);
		
		ComboItem item = lstChooseGame.getValue();
		
		String query = "Call get_stadium_seats_by_game_id(" + item.getKey() +")";
		ResultSet result = connectionClass.selectQuery(query);
		ArrayList<Integer> allSeats = new ArrayList<Integer>();
		if(result != null) {
			try {
				while(result.next()) {
					allSeats.add(result.getInt("seat_number"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(allSeats.size() != 0) {
			for(int seatNumber : allSeats) {
				glow(seatNumber);
				checkBoxs.get(seatNumber - 1).setDisable(true);
				checkBoxs.get(seatNumber - 1).setSelected(true);
			}
		}
		
	}
	
	public void glow(int number) {
		int depth = 80;
		DropShadow borderGlow = new DropShadow();
		borderGlow.setColor(Color.GREEN);
		borderGlow.setWidth(depth);
		borderGlow.setHeight(depth);
		borderGlow.setBlurType(BlurType.ONE_PASS_BOX);
		
		if(number == 1) {			
			imagea1.setEffect(borderGlow);
		}else if(number == 2) {
			imagea2.setEffect(borderGlow);
		}else if(number == 3) {
			imagea3.setEffect(borderGlow);
		}else if(number == 4) {
			imagea4.setEffect(borderGlow);
		}else if(number == 5) {
			imageb1.setEffect(borderGlow);
		}else if(number == 6) {
			imageb2.setEffect(borderGlow);
		}else if(number == 7) {			
			imageb3.setEffect(borderGlow);
		}else if(number == 8) {
			imageb4.setEffect(borderGlow);
		}
	}

	public void unGlowAll() {
		DropShadow borderGlow1 = new DropShadow();
		
		borderGlow1.setColor(Color.TRANSPARENT);
		imagea1.setEffect(borderGlow1);	
		imagea2.setEffect(borderGlow1);	
		imagea3.setEffect(borderGlow1);	
		imagea4.setEffect(borderGlow1);	
		imageb1.setEffect(borderGlow1);	
		imageb2.setEffect(borderGlow1);	
		imageb3.setEffect(borderGlow1);	
		imageb4.setEffect(borderGlow1);	
		
		ArrayList<CheckBox> checkBoxs = new ArrayList<CheckBox>();
		checkBoxs.add(a1);
		checkBoxs.add(a2);
		checkBoxs.add(a3);
		checkBoxs.add(a4);
		checkBoxs.add(b1);
		checkBoxs.add(b2);
		checkBoxs.add(b3);
		checkBoxs.add(b4);
		
		for(CheckBox checkBox : checkBoxs) {
			checkBox.setDisable(false);
			checkBox.setSelected(false);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		LocalDateTime today = LocalDateTime.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		int day = today.getDayOfMonth();
		
		String monthString = "" + month;
		if(monthString.length() < 2) {
			monthString = "0" + monthString;
		}
		
		String dayString = "" + day;
		if(dayString.length() < 2) {
			dayString = "0" + dayString;
		}
		
		String date = year + "-" + monthString + "-" + dayString;
		System.out.println("date " + date);
		
		int game_id;
		String team1, team2;		
		
		String query = "Call get_game_after_date('" + date + "')";	
		ResultSet result = connectionClass.selectQuery(query);
		if(result != null) {
			try {
				while(result.next()) {
					game_id = result.getInt("game_id");
					team1 = result.getString("game_team1");
					team2 = result.getString("game_team2");
					
					ComboItem item = new ComboItem(game_id, team1 + " VS " + team2);
					games.add(item);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		lstChooseGame.getItems().addAll(games);
	}		

}
