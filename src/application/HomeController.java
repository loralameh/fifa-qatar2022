package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class HomeController implements Initializable{

	@FXML
	Button home,profile,reserveSeat,predict,go_home;
		
	@FXML
	BorderPane mainPane;
	
	@FXML
	VBox homeVBox;

	ArrayList<Game> PlayedGames =new ArrayList<Game>();
	ArrayList<Game> CommingGames =new ArrayList<Game>();
	
	private ConnectionClass connectionClass;
	
	public HomeController() {
		connectionClass = new ConnectionClass();
	}
	
	public static User user;
	
	public void getProfilePage() {	
		profile.setOnAction(e->{
			try {
					mainPane.setCenter(FXMLLoader.load(getClass().getResource("Profile.fxml")));	
				} catch (IOException e1) {
					e1.printStackTrace();
			}
		});
	}

	
	public void getSeatPage() {
		reserveSeat.setOnAction(e->{
			try {
				mainPane.setCenter(FXMLLoader.load(getClass().getResource("ReserveSeat.fxml")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
			
	}
	
	public void getHomePage() {
		home.setOnAction(e->{
			try {				
				mainPane.setCenter(FXMLLoader.load(getClass().getResource("Home.fxml")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
				
	}
	
	public void getHomePage2() {
		go_home.setOnAction(e->{
			try {				
				mainPane.setCenter(FXMLLoader.load(getClass().getResource("Home.fxml")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
				
	}
	public void getPredictPage() {
		predict.setOnAction(e->{
			try {				
				mainPane.getChildren().removeAll();
				mainPane.setCenter(FXMLLoader.load(getClass().getResource("Predict.fxml")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
				
	}	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("url " + arg0 + " ResourceBundle " + arg1);
		
		if(arg0.toString().contains("Home")) {
			getGames();
			System.out.println("CommingGames size :"+CommingGames.size());
			System.out.println("PlayedGames size :"+PlayedGames.size());
			
			// create a title for CommingGames
			Text CommingGamesTitle =new Text(120,100,"Up Comming Games");
			CommingGamesTitle.setFill(Color.rgb(71, 4, 41));
			CommingGamesTitle.setFont(Font.font("Berlin Sans FB", FontWeight.BOLD, FontPosture.ITALIC, 35));
			homeVBox.getChildren().add(CommingGamesTitle);
			
			// create a UI for CommingGames
			for(int i=0;i<CommingGames.size();i++) {
				
				
				String PathToTeam1Image="file:assets\\" + CommingGames.get(i).team1 +"Map.png";
				String PathToTeam2Image="file:assets\\" + CommingGames.get(i).team2 +"Map.png";

				ImageView Team1pic = new ImageView(new Image(PathToTeam1Image));
				ImageView Team2pic = new ImageView(new Image(PathToTeam2Image));
				
				Team1pic.setRotate(-18);
				Team1pic.setFitHeight(130);
				Team1pic.setFitWidth(164);
				Team1pic.setLayoutX(12);
				Team1pic.setLayoutY(25);

				
				Team2pic.setRotate(18);
				Team2pic.setFitHeight(130);
				Team2pic.setFitWidth(164);
				Team2pic.setLayoutX(150);
				Team2pic.setLayoutY(25);
				
				Text vs =new Text(120,100,"VS");
				vs.setFill(Color.rgb(71, 4, 41));
				vs.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 61));
				
				Pane imagePart =new Pane(Team1pic,Team2pic,vs);
				imagePart.setMinHeight(216);
				
				
				
				Label txtTeamNames=new Label();
				txtTeamNames.setText(CommingGames.get(i).team1.toUpperCase() + " VS " + CommingGames.get(i).team2.toUpperCase());
				txtTeamNames.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD, FontPosture.ITALIC, 18));
				txtTeamNames.setTextFill(Color.rgb(71, 4, 41));
				
				Label txtGameDate=new Label();
				txtGameDate.setText(CommingGames.get(i).date+"   |   "+CommingGames.get(i).time);
				txtGameDate.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD,14));
				ImageView callenderpic = new ImageView(new Image("file:assets\\PngItem_1854206.png"));
				callenderpic.setFitHeight(27);
				callenderpic.setFitWidth(27);
				txtGameDate.setGraphic(callenderpic);
				
				Label txtGameStaduim=new Label();
				txtGameStaduim.setText(CommingGames.get(i).stadium_name+", "+ CommingGames.get(i).stadium_location);
				txtGameStaduim.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD,14));
				ImageView locationpic = new ImageView(new Image("file:assets\\locationIcon.png"));
				locationpic.setFitHeight(27);
				locationpic.setFitWidth(27);
				txtGameStaduim.setGraphic(locationpic);
				
				Label txtSeatPrice=new Label();
				txtSeatPrice.setText(CommingGames.get(i).seat_price+" coins/seat");
				txtSeatPrice.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD,14));
				ImageView pricepic = new ImageView(new Image("file:assets\\dollar.png"));
				pricepic.setFitHeight(27);
				pricepic.setFitWidth(27);
				txtSeatPrice.setGraphic(pricepic);
				
				VBox infoPart =new VBox(txtTeamNames , txtGameDate,txtGameStaduim,txtSeatPrice);
				infoPart.setSpacing(25);
				
				HBox displayAGamePane=new HBox(imagePart,infoPart);
				displayAGamePane.setPrefHeight(630);
				displayAGamePane.setSpacing(40);
				
				homeVBox.getChildren().add(displayAGamePane);

			}
			
			// create a title for PlayedGames
			Text PlayedGamesTitle =new Text(120,100,"Recent Game Results");
			PlayedGamesTitle.setFill(Color.rgb(71, 4, 41));
			PlayedGamesTitle.setFont(Font.font("Berlin Sans FB", FontWeight.BOLD, FontPosture.ITALIC, 35));
			homeVBox.getChildren().add(PlayedGamesTitle);
			
			// GridPane that will contain all played games
			GridPane AllPlayedGames = new GridPane();
			
			Text teams=new Text("Teams");
			teams.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD,14));
			teams.setFill(Color.rgb(71, 4, 41));
			
			Text GameTiming=new Text("Game Timing");
			GameTiming.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD,14));
			GameTiming.setFill(Color.rgb(71, 4, 41));
			
			Text Score=new Text("Score");
			Score.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD,14));
			Score.setFill(Color.rgb(71, 4, 41));
			
			AllPlayedGames.add(teams, 0, 0);
			AllPlayedGames.add(GameTiming, 1,0);
			AllPlayedGames.add(Score, 2,0);
			AllPlayedGames.setHgap(30);
			AllPlayedGames.setVgap(7);
			//create a UI for PlayedGames
			
			for(int i=0;i<PlayedGames.size();i++) {
					if(PlayedGames != null && PlayedGames.size() > i) {
						Text txtTeamNames=new Text();
						txtTeamNames.setText(PlayedGames.get(i).team1 + " VS " + PlayedGames.get(i).team2);
						txtTeamNames.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD,14));

						Label txtGameDate=new Label();
						txtGameDate.setText(PlayedGames.get(i).date+"   |   " + PlayedGames.get(i).time);
						txtGameDate.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD,14));
						
						Label txtScore=new Label();
						txtScore.setText(PlayedGames.get(i).score1+" - " + PlayedGames.get(i).score2);
						txtScore.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD,14));


						AllPlayedGames.add(txtTeamNames,0,i+1);
						AllPlayedGames.add(txtGameDate,1,i+1);
						AllPlayedGames.add(txtScore,2,i+1);
					}
					
			}
			homeVBox.getChildren().add(AllPlayedGames);
				
		}
	
	}
	
	public void getGames() {
		
		int stadium_id,score1,score2,stadium_price;
		String time, team1, team2,game_date, stadium_name,stadium_location;
		LocalDateTime game_date_comp;
		LocalDateTime Today = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");			
		ResultSet result= connectionClass.selectQuery("Call get_all_game()");
		ResultSet Staduim_result;
		
		if(result != null) {
			try {
				while(result.next()) {
					Game game =new Game();

					team1 = result.getString("game_team1");
					team2 = result.getString("game_team2");
					time = result.getString("game_time");
					game_date =result.getString("game_date");
					stadium_id = result.getInt("statium_id");
					score1 = result.getInt("game_team1_score");
					score2 = result.getInt("game_team2_score");
					//change the date format to compare it with today date
					game_date_comp = LocalDateTime.parse(game_date + " " + time, formatter);
					//set game details in an object
					game.SetGame(score1, score2, game_date, time, team1, team2,stadium_id);

					if(game_date_comp.isAfter(Today))
						CommingGames.add(game);
					else
						PlayedGames.add(game);



			}//end	result.next()	
		  }//end try
			
			catch (SQLException e1) {
				e1.printStackTrace();}
		}// end result != null
		
		
		for(int i=0;i<CommingGames.size();i++) {
			Game this_game= CommingGames.get(i);
			int id=this_game.getStadiumId();
		// Get the stadium details using its ID 
		Staduim_result= connectionClass.selectQuery2("Call get_statium("+id+")");
		
		if(Staduim_result != null) {
			try {
				if(Staduim_result.next()) {
					stadium_name = Staduim_result.getString("statium_name");
					stadium_location = Staduim_result.getString("statium_location");
					stadium_price = Staduim_result.getInt("seat_price");

					this_game.SetStaduimDetails(stadium_name, stadium_location, stadium_price);

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 } 
		}
		
	} //end getGames()

	
}