package application;

public class User {
	
	private String username, password, name, gender, type, status, location;
	private int coins;
	
	public User(String username, String password, String name, String gender, String type, String status, String location, int coins) {
		this.coins = coins;
		this.gender = gender;
		this.location = location;
		this.name = name;
		this.password = password;
		this.status = status;
		this.type = type;
		this.username = username;
	}
	
	public User() {}

	public int getCoins() {
		return coins;
	}
	
	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	

}
