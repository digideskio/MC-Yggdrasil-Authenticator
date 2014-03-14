package authentication;

/**
 * Class used for serialization by Gson to build up the json payloads for authenticating, signing out, refreshing,
 * validating, and invalidating a User.
 * 
 * @author wpenson
 *
 */
public class Payload {
	private String username;
	private String password;
	private String clientToken;
	private String accessToken;
	private Agent agent;
	private SelectedProfile profile;
	
	/**
	 * General constructor for creating a payload. A field is set to null to exclude it from the Gson builder.
	 * 
	 * @param username Username
	 * @param password Password
	 * @param clientToken clientToken
	 * @param accessToken accessToken
	 * @param agent Agent
	 * @param profile SelectedProfile
	 */
	public Payload(String username, String password, String clientToken, String accessToken, Agent agent, SelectedProfile profile) {
		this.username = username;
		this.password = password;
		this.clientToken = clientToken;
		this.accessToken = accessToken;
		this.agent = agent;
		this.profile = profile;
	}
	
	/**
	 * Required values necessary for the authentication payload.
	 * 
	 * @param username Username
	 * @param password Password
	 * @param clientToken clientToken
	 * @param agent Agent
	 */
	public Payload(String username, String password, String clientToken, Agent agent) {
		this(username, password, clientToken, null, agent, null);
	}
	
	/**
	 * Required values necessary for the signout and invalidate payloads.
	 * 
	 * @param username Username
	 * @param password Password
	 */
	public Payload(String username, String password) {
		this(username, password, null, null, null, null);
	}
	
	/**
	 * Required values necessary for the refresh payload.
	 * 
	 * @param accessToken accessToken
	 * @param clientToken clientToken
	 * @param profile SelectedProfile
	 */
	public Payload(String accessToken, String clientToken, SelectedProfile profile) {
		this(null, null, clientToken, accessToken, null, profile);
	}
	
	/**
	 * Required values necessary for the validate payload.
	 * 
	 * @param accessToken accessToken
	 */
	public Payload(String accessToken) {
		this(null, null, null, accessToken, null, null);
	}
	
	/**
	 * Returns SelectedProfile.
	 * 
	 * @return SelectedProfile
	 */
	public SelectedProfile getProfile() {
		return profile;
	}

	/**
	 * Returns accesstToken.
	 * 
	 * @return accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Returns Agent.
	 * 
	 * @return Agent
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * Returns username.
	 * 
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns password.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns clientToken.
	 * 
	 * @return clientToken
	 */
	public String getClientToken() {
		return clientToken;
	}
}
