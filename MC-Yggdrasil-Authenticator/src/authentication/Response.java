package authentication;

/**
 * Class used for serialization by Gson to build up the json response received from a payload from the authentication servers.
 * 
 * @author wpenson
 *
 */
public class Response {
	private String accessToken;
	private String clientToken;
	private SelectedProfile profile;
	
	/**
	 * Required values necessary for the response.
	 * 
	 * @param accessToken accessToken
	 * @param clientToken clientToken
	 * @param profile SelectedProfile
	 */
	public Response(String accessToken, String clientToken, SelectedProfile profile) {
		this.accessToken = accessToken;
		this.clientToken = clientToken;
		this.profile = profile;
	}

	/**
	 * Returns the SeletectedProfile.
	 * 
	 * @return SelectedProfile
	 */
	public SelectedProfile getSelectedProfile() {
		return profile;
	}

	/**
	 * Returns the accessToken.
	 * 
	 * @return accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Returns the clientToken.
	 * 
	 * @return clientToken
	 */
	public String getClientToken() {
		return clientToken;
	}
}
