package bzb.gwt.planner.client;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String encodedUsername;
	private String username;
	private String userAuth;
	private String fullName;
	private String homeCountry;
	private boolean male;
	private int age;

	public CUser() {

	}
	
	public void save() {
		((SaveServiceAsync)GWT.create(SaveService.class)).saveUser(this, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				caught.printStackTrace();
				System.out.println("Remote Procedure Call - Failure");
			}

			public void onSuccess(String encodedUsername) {
				setEncodedUsername(encodedUsername);
				
			}
		});
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

	public void setMale(boolean male) {
		this.male = male;
	}

	public boolean getMale() {
		return male;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setEncodedUsername(String encodedUsername) {
		this.encodedUsername = encodedUsername;
	}

	public String getEncodedUsername() {
		return encodedUsername;
	}

}
