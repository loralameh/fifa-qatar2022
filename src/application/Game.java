package application;


public class Game {
	public int score1, score2,seat_price,stadium_id, game_id;
	public String time, team1, team2,date,stadium_name,stadium_location;
//	public LocalDateTime date;
	public void SetGame( int score1, int score2,String date, String time, String team1, String team2,int stadium_id) {
	this.date= date;
	this.time = time;
	this.team1 = team1;
	this.team2 = team2;
	this.score1 = score1;
	this.score2 = score2;
	this.stadium_id =stadium_id;

	}
	
	public void setGameID(int gameId) {
		game_id = gameId;
	}
	
	public void SetStaduimDetails( String  stadium_name,String stadium_location, int seat_price) {

	this.stadium_name=stadium_name;
	this.stadium_location=stadium_location;
	this.seat_price = seat_price;
	}
	
	public int getStadiumId() {
		return stadium_id;
	}
	
	
}
