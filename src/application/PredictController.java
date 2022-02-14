package application;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PredictController implements Initializable {
	
	@FXML
	RadioButton v1,v2,v3,v4;
	
    @FXML
	ImageView flag1,flag2,flag3,flag4;
    
    @FXML
    Button btnPredict1, btnPredict2;
    
    @FXML
    Text gameLabel1, gameLabel2, gameDate1, gameDate2, gameStadium1, gameStadium2, predictionPercentage1, predictionPercentage2, predictionPercentage3, predictionPercentage4;
    
    @FXML
    TextField txtTeam1Game1, txtTeam2Game1, txtTeam1Game2, txtTeam2Game2;
    
    ConnectionClass connectionClass;
    User user;
    
    ArrayList<Game> games = new ArrayList<Game>();
    
    public PredictController() {
    	connectionClass = new ConnectionClass();
    	user = Statics.user;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getGames();
		if(games.size() != 0) {
			File file = new File("assets/" + games.get(0).team1 + "Flag.png");
			
			flag1.setImage(new Image(file.toURI().toString())); 
			file = new File("assets/" + games.get(0).team2 + "Flag.png");
			flag2.setImage(new Image(file.toURI().toString())); 
			file = new File("assets/" + games.get(1).team1 + "Flag.png");
			flag3.setImage(new Image(file.toURI().toString())); 
			file = new File("assets/" + games.get(1).team2 + "Flag.png");
			flag4.setImage(new Image(file.toURI().toString())); 
			
			String label1 = games.get(0).team1.toUpperCase() + " VS " + games.get(0).team2.toUpperCase();
			String label2 = games.get(1).team1.toUpperCase() + " VS " + games.get(1).team2.toUpperCase();
			
			gameLabel1.setText(label1);
			gameLabel2.setText(label2);
			
			v1.setText("VIVA " + games.get(0).team1.toUpperCase());
			v2.setText("VIVA " + games.get(0).team2.toUpperCase());
			v3.setText("VIVA " + games.get(1).team1.toUpperCase());
			v4.setText("VIVA " + games.get(1).team2.toUpperCase());
			
			gameDate1.setText(games.get(0).date);
			gameDate2.setText(games.get(1).date);
			
			gameStadium1.setText(games.get(0).stadium_name);
			gameStadium2.setText(games.get(1).stadium_name);
			
			issuePercentage();
			
			checkUserPrediction();
			
		}
	}
	
	public void checkUserPrediction() {
		String query = "Call get_game_predection_by_game_id_and_username(" + games.get(0).game_id + ", '" + user.getUsername() + "');";
		ResultSet set = connectionClass.selectQuery(query);
		int team1Score = 0, team2Score = 0;
		if(set != null) {
			try {
				if(set.next()) {
					team1Score = set.getInt("game_team1_score");
					team2Score = set.getInt("game_team2_score");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(team1Score != 0 || team2Score != 0) {
			txtTeam1Game1.setText("" + team1Score);
			txtTeam1Game1.setDisable(true);
			txtTeam2Game1.setText("" + team2Score);
			txtTeam2Game1.setDisable(true);
			
			DropShadow borderGlowv1 = new DropShadow();
			int depth = 180;
			
			if(team1Score > team2Score) {
				v1.setSelected(true);
				v1.setDisable(true);
				
				v2.setSelected(false);
				v2.setDisable(true);
				
				borderGlowv1.setColor(Color.GREEN);
				borderGlowv1.setWidth(depth);
				borderGlowv1.setHeight(depth);
				borderGlowv1.setBlurType(BlurType.ONE_PASS_BOX);
				flag1.setEffect(borderGlowv1);
			}else {
				v1.setSelected(false);
				v1.setDisable(true);
				
				v2.setSelected(true);
				v2.setDisable(true);
				
				borderGlowv1.setColor(Color.GREEN);
				borderGlowv1.setWidth(depth);
				borderGlowv1.setHeight(depth);
				borderGlowv1.setBlurType(BlurType.ONE_PASS_BOX);
				flag2.setEffect(borderGlowv1);
			}
			
			btnPredict1.setDisable(true);
		}
		
		query = "Call get_game_predection_by_game_id_and_username(" + games.get(1).game_id + ", '" + user.getUsername() + "');";
		set = connectionClass.selectQuery(query);
		team1Score = 0;
		team2Score = 0;
		if(set != null) {
			try {
				if(set.next()) {
					team1Score = set.getInt("game_team1_score");
					team2Score = set.getInt("game_team2_score");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(team1Score != 0 || team2Score != 0) {
			txtTeam1Game2.setText("" + team1Score);
			txtTeam1Game2.setDisable(true);
			txtTeam2Game2.setText("" + team2Score);
			txtTeam2Game2.setDisable(true);
			
			DropShadow borderGlowv1 = new DropShadow();
			int depth = 180;
			
			if(team1Score > team2Score) {
				v3.setSelected(true);
				v3.setDisable(true);
				
				v4.setSelected(false);
				v4.setDisable(true);
				
				borderGlowv1.setColor(Color.GREEN);
				borderGlowv1.setWidth(depth);
				borderGlowv1.setHeight(depth);
				borderGlowv1.setBlurType(BlurType.ONE_PASS_BOX);
				flag3.setEffect(borderGlowv1);
			}else {
				v3.setSelected(false);
				v3.setDisable(true);
				
				v4.setSelected(true);
				v4.setDisable(true);
				
				borderGlowv1.setColor(Color.GREEN);
				borderGlowv1.setWidth(depth);
				borderGlowv1.setHeight(depth);
				borderGlowv1.setBlurType(BlurType.ONE_PASS_BOX);
				flag4.setEffect(borderGlowv1);
			}
			
			btnPredict2.setDisable(true);
		}
		
	}
	
	public void issuePercentage() {
		int team1Percentage1 = getTeam1Predection(games.get(0).game_id);			
		int team2Percentage1 = 100 - team1Percentage1;			
		predictionPercentage1.setText(team1Percentage1 + "%");
		predictionPercentage2.setText(team2Percentage1 + "%");
		
		int team1Percentage2 = getTeam1Predection(games.get(1).game_id);			
		int team2Percentage2 = 100 - team1Percentage2;			
		predictionPercentage3.setText(team1Percentage2 + "%");
		predictionPercentage4.setText(team2Percentage2 + "%");
	}
	
	public int getTeam1Predection(int id) {
				
		String query = "Call get_game_predection_by_game_id(" + id +")";
		ResultSet result = connectionClass.selectQuery(query);
		int team1Score, team2Score, team1Predect = 0, team2Predect = 0;
		if(result != null) {
			try {
				
				while(result.next()) {
					team1Score = result.getInt("game_team1_score");
					team2Score = result.getInt("game_team2_score");
					
					if(team1Score > team2Score) {
						team1Predect++;
					}else if(team1Score < team2Score) {
						team2Predect++;
					}else {
						team1Predect++;
						team2Predect++;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		int total;
		int team1Percentage = 0;
		if(team1Predect != 0 || team2Predect != 0) {
			total = team1Predect + team2Predect;
			team1Percentage = team1Predect * 100 / total;
		}
		
		return team1Percentage;
	}
	
	public void getGames() {
		
		int stadium_id,score1,score2,stadium_price;
		String time, team1, team2,game_date, stadium_name,stadium_location;
		
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");	
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
		
		
		ResultSet result= connectionClass.selectQuery("Call get_game_after_date('" + date + "')");
		ResultSet Staduim_result;
		
		if(result != null) {
			try {
				int index = 0;
				while(result.next()) {
					if(index < 2) {
						Game game = new Game();
						game.setGameID(result.getInt("game_id"));
						team1 = result.getString("game_team1");
						team2 = result.getString("game_team2");
						time = result.getString("game_time");
						game_date =result.getString("game_date");
						stadium_id = result.getInt("statium_id");
						score1 = result.getInt("game_team1_score");
						score2 = result.getInt("game_team2_score");
																	
						//set game details in an object
						game.SetGame(score1, score2, game_date, time, team1, team2,stadium_id);
						games.add(game);
						index++;
					}else {
						break;
					}
				}
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
		for(int i=0;i<games.size();i++) {			
			int id = games.get(i).getStadiumId();
			Staduim_result= connectionClass.selectQuery2("Call get_statium(" + id + ")");
			
			if(Staduim_result != null) {
				try {
					if(Staduim_result.next()) {
						stadium_name = Staduim_result.getString("statium_name");
						stadium_location = Staduim_result.getString("statium_location");
						stadium_price = Staduim_result.getInt("seat_price");
	
						games.get(i).SetStaduimDetails(stadium_name, stadium_location, stadium_price);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 } 
		}
		
	} 
	
	
	public void onPredictGame1() {
		btnPredict1.setOnAction(e -> {
			Alert alert;
			if(!v1.isSelected() && !v2.isSelected()) {			
				alert = new Alert(AlertType.ERROR);            
				alert.setContentText("Please Select A Game");
				alert.show();
	            return;				
			}
			
			if(txtTeam1Game1.getText() == null || txtTeam1Game1.getText().isEmpty() || txtTeam2Game1.getText() == null || txtTeam2Game1.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);            
				alert.setContentText("Please Enter Teams Scores");
				alert.show();
	            return;		
			}
			
			int team1Score = Integer.parseInt(txtTeam1Game1.getText().toString());
			int team2Score = Integer.parseInt(txtTeam2Game1.getText().toString());
			
			String query = "Call insert_game_predection(" + games.get(0).game_id + ", '" + user.getUsername() + "', " + team1Score + ", " + team2Score + ");";
			int result = connectionClass.nonQuery(query);
			
			issuePercentage();
			checkUserPrediction();
		});
	}
		
	public void onPredictGame2() {
		btnPredict2.setOnAction(e -> {
			Alert alert;
			if(!v1.isSelected() && !v2.isSelected()) {			
				alert = new Alert(AlertType.ERROR);            
				alert.setContentText("Please Select A Game");
				alert.show();
	            return;				
			}
			
			if(txtTeam1Game2.getText() == null || txtTeam1Game2.getText().isEmpty() || txtTeam2Game2.getText() == null || txtTeam2Game2.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);            
				alert.setContentText("Please Enter Teams Scores");
				alert.show();
	            return;		
			}
			
			int team1Score = Integer.parseInt(txtTeam1Game2.getText().toString());
			int team2Score = Integer.parseInt(txtTeam2Game2.getText().toString());
			
			String query = "Call insert_game_predection(" + games.get(1).game_id + ", '" + user.getUsername() + "', " + team1Score + ", " + team2Score + ");";
			int result = connectionClass.nonQuery(query);
			
			issuePercentage();
			checkUserPrediction();
		});
	}

	
	public void predictGame() {
		int depth = 180;
		ToggleGroup group1 = new ToggleGroup();
		ToggleGroup group2 = new ToggleGroup();
		DropShadow borderGlowv1 = new DropShadow();
		DropShadow borderGlowv2 = new DropShadow();
		DropShadow borderGlowv3 = new DropShadow();
		DropShadow borderGlowv4 = new DropShadow();	
		
		v1.setToggleGroup(group1);
		v2.setToggleGroup(group1);
		v3.setToggleGroup(group2);
		v4.setToggleGroup(group2);
		
		if(v1.isSelected()) {
			borderGlowv1.setColor(Color.GREEN);
			borderGlowv1.setWidth(depth);
			borderGlowv1.setHeight(depth);
			borderGlowv1.setBlurType(BlurType.ONE_PASS_BOX);
			flag1.setEffect(borderGlowv1);
		}else {
			borderGlowv1.setColor(Color.GREEN);
			flag1.setEffect(borderGlowv1);
		}
		if(v2.isSelected()) {
			borderGlowv2.setColor(Color.GREEN);
			borderGlowv2.setWidth(depth);
			borderGlowv2.setHeight(depth);
			borderGlowv2.setBlurType(BlurType.ONE_PASS_BOX);
			flag2.setEffect(borderGlowv2);
		}else {
			borderGlowv1.setColor(Color.GREEN);
			flag2.setEffect(borderGlowv2);
		}
		if(v3.isSelected()) {
			borderGlowv3.setColor(Color.GREEN);
			borderGlowv3.setWidth(depth);
			borderGlowv3.setHeight(depth);
			borderGlowv3.setBlurType(BlurType.ONE_PASS_BOX);
			flag3.setEffect(borderGlowv3);
		}else {
			borderGlowv1.setColor(Color.GREEN);
			flag3.setEffect(borderGlowv3);
		}
		if(v4.isSelected()) {
			borderGlowv4.setColor(Color.GREEN);
			borderGlowv4.setWidth(depth);
			borderGlowv4.setHeight(depth);
			borderGlowv4.setBlurType(BlurType.ONE_PASS_BOX);
			flag4.setEffect(borderGlowv4);
		}else {
			borderGlowv4.setColor(Color.GREEN);
			flag4.setEffect(borderGlowv4);
		}
		
	}
		
}
