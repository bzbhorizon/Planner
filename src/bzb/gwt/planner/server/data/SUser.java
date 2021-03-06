package bzb.gwt.planner.server.data;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import bzb.gwt.planner.client.data.CUser;

@PersistenceCapable
public class SUser {

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

	@Persistent
	private boolean male;
	
	@Persistent
	private int age;
	
	@Persistent
	private long creationTime = System.currentTimeMillis();
	
	public SUser () {
		
	}
	
	public SUser (String userAuth, String username, String fullName, String homeCountry, boolean male, int age) {
		setUserAuth(userAuth);
		setUsername(username);
		setFullName(fullName);
		setHomeCountry(homeCountry);
		setMale(male);
		setAge(age);
	}
	
	public SUser (CUser user) {
		this(user.getUserAuth(), user.getUsername(), user.getFullName(), user.getHomeCountry(), user.getMale(), user.getAge());
		setEncodedUsername(user.getEncodedUsername());
	}
	
	public void save () {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(this);
        } finally {
            pm.close();
        }
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
	};
	
	public CUser getCUser () {
		CUser user = new CUser();
		user.setEncodedUsername(getEncodedUsername());
		user.setAge(getAge());
		user.setFullName(getFullName());
		user.setHomeCountry(getHomeCountry());
		user.setMale(getMale());
		user.setUserAuth(getUserAuth());
		user.setUsername(getUsername());
		user.setCreationTime(getCreationTime());
		return user;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getCreationTime() {
		return creationTime;
	}

}
