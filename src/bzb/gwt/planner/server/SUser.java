package bzb.gwt.planner.server;

import java.io.Serializable;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import bzb.gwt.planner.server.PMF;


@PersistenceCapable
public class SUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String encodedUsername;

	@Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String userAuth;
	
	@Persistent
    private String username;
	
	@Persistent
	private String fullName;
	
	@Persistent
	private String homeCountry;

	@Persistent(serialized = "true")
	private GENDER gender;
	
	@Persistent
	private int age;
	
	public static enum GENDER { MALE, FEMALE }
	
	public SUser () {
		
	}
	
	public void save () {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(this);
        } finally {
            pm.close();
        }
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

	public void setEncodedUsername(String encodedUsername) {
		this.encodedUsername = encodedUsername;
	}

	public String getEncodedUsername() {
		return encodedUsername;
	};

}
