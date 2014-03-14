package authentication;

/**
 * Stores the post authentication information of the User. SelectedProfile, accessTokens,
 * and AvailableProfiles are null unless the authenticate() method in AuthRequest is successfully called.
 * 
 * @author wpenson
 *
 */
public class User {
	private String accessToken;
	private SelectedProfile profile;
	private AvailableProfiles ap;
	
	/**
	 * Creates a user with null values.
	 */
	public User() {
		accessToken = null;
		profile = null;
		ap = null;
	}
	
	/**
	 * Returns accessToken.
	 * 
	 * @return accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets a new accessToken.
	 * 
	 * @param accessToken accessToken
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	/**
	 * Returns true if the user has an access code.
	 * 
	 * @return True or False
	 */
	public boolean hasAccessToken() {
		return accessToken != null;
	}
	
	/**
	 * Returns SelectedProfile.
	 * 
	 * @return SelectedProfile
	 */
	public SelectedProfile getSelectedProfile() {
		return profile;
	}

	/**
	 * Sets a new SelectedProfile.
	 * 
	 * @param profile SelectedProfile
	 */
	public void setSelectedProfile(SelectedProfile profile) {
		this.profile = profile;
	}
	
	/**
	 * Returns AvailableProfiles.
	 * 
	 * @return AvailableProfiles
	 */
	public AvailableProfiles getAvailableProfiles() {
		return ap;
	}

	/**
	 * Sets AvailableProfiles.
	 * 
	 * @param ap AvailableProfiles
	 */
	public void setAvailableProfiles(AvailableProfiles ap) {
		this.ap = ap;
	}
}
