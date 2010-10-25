package bzb.gwt.planner.client;

import java.io.Serializable;

public class CUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String username;
	private String userAuth;
	private String fullName;
	private String homeCountry;
	private GENDER gender;
	private int age;
	public static enum GENDER { MALE, FEMALE }
	
	public CUser () {
		
	}
	
	public void save () {
		System.out.println("Implement");
	}
	
	public boolean isRegistered () {
		System.out.println("Implement");
		return false;
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
	}

}
