package bzb.gwt.planner.shared;

import java.io.Serializable;

public class User implements Serializable {
	
	private String username;
	private String userAuth;
	private String fullName;
	private String homeCountry;
	private GENDER gender;
	private int age;
	
	public static enum GENDER { MALE, FEMALE }
	
	public User () {
		
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUserAuth(String userAuth) {
		this.userAuth = userAuth;
	}

	public String getUserAuth() {
		return userAuth;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setHomeCountry(String homeCountry) {
		this.homeCountry = homeCountry;
	}

	public String getHomeCountry() {
		return homeCountry;
	}

	public void setGender(GENDER gender) {
		this.gender = gender;
	}

	public GENDER getGender() {
		return gender;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	};

}
