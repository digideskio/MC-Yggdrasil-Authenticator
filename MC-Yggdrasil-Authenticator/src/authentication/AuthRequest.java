package authentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import com.google.gson.Gson;

/**
 * Class used for authenticating a user on the Mojang authentication server.
 * The constructor is given a username and password. A call to authenticate() is made
 * to get the needed token to connect to a Minecraft server. signout() invalidates all current
 * authentication tokens for the user with their username and password. refresh() keeps a user
 * logged on between game sessions. validate() verifies the authentication token for the
 * currently-active session and invalidate() is the same as signout() but uses the clientToken and
 * accessToken to invalidate the accessTokens.
 * 
 * @author wpenson
 *
 */
public class AuthRequest {
	private String username;
	private String password;
	private String clientToken;
	private Agent agent;
	private User user;	// Post authentication info for Minecraft user
	private final String MINECRAFT_AUTH_SERVER = "https://authserver.mojang.com/";
	private final Gson gson = new Gson();
	
	/**
	 * Creates an AuthRequest with the given username and password and also creates a random clientToken.
	 * 
	 * @param username Username
	 * @param password Password
	 */
	public AuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
		user = new User();
		clientToken = UUID.randomUUID().toString();
		agent = new Agent();
	}
	
	/**
	 * Returns User where the accessToken, SelectedProfile, and AvailableProfiles are stored.
	 * 
	 * @return User
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Returns true if User is successfully authenticated. AccessToken and
	 * SelectedProfile are updated in User if it was successful.
	 * 
	 * @return True or False
	 */
	public boolean authenticate() {
		Payload ap = new Payload(username, password, clientToken, agent);
		String json = gson.toJson(ap);

		json = connect("authenticate", json);
		if (json.equals("false"))
			return false;
		
		Response ar = gson.fromJson(json, Response.class);
		clientToken = ar.getClientToken();
		user.setAccessToken(ar.getAccessToken());
		user.setSelectedProfile(ar.getSelectedProfile());

		return true;
	}
	
	/**
	 * Returns true if successful and invalidates accessTokens if user is successfully signed out.
	 * 
	 * @return True or False
	 */
	public boolean signout() {
		Payload sp = new Payload(username, password);
		String json = gson.toJson(sp);

		json = connect("signout", json);
		
		if (json.equals("false") || !json.equals("null"))
			return false;
		
		user.setAccessToken(null);
		
		return true;
	}
	
	/**
	 * Returns true if successful and updates the accessToken and SelectedProfile for the User.
	 * 
	 * @return True or False
	 */
	public boolean refresh() {
		if (!user.hasAccessToken())
			return false;
		
		Payload ap = new Payload(user.getAccessToken(), clientToken, user.getSelectedProfile());
		String json = gson.toJson(ap);
		
		json = connect("refresh", json);
		if (json.equals("false"))
			return false;
		
		Response ar = gson.fromJson(json, Response.class);
		clientToken = ar.getClientToken();
		user.setAccessToken(ar.getAccessToken());
		user.setSelectedProfile(ar.getSelectedProfile());

		return true;
	}
	
	/**
	 * Returns true if token is valid.
	 * 
	 * @return True or False
	 */
	public boolean validate() {
		if (!user.hasAccessToken())
			return false;
		
		Payload sp = new Payload(user.getAccessToken());
		String json = gson.toJson(sp);

		json = connect("validate", json);
		
		if (json.equals("false") || !json.equals("null"))
			return false;
		
		return true;
	}
	
	/**
	 * Returns true if invalidated correctly and sets accessToken to null.
	 * 
	 * @return True or False
	 */
	public boolean invalidate() {
		if (!user.hasAccessToken())
			return false;
		
		Payload sp = new Payload(user.getAccessToken(), clientToken);
		String json = gson.toJson(sp);

		json = connect("invalidate", json);
		
		if (json.equals("false") || !json.equals("null"))
			return false;
		
		user.setAccessToken(null);
		
		return true;
	}
	
	/**
	 * Connects to the Mojang authentication server with the given payload. If successful, a json string is returned
	 * with the appropriate information. On failure, a string of "false" is returned and is dealt with accordingly.
	 * 
	 * @param payload String of payload type
	 * @param json String in json format
	 * @return String in json format
	 */
	private String connect(String payload, String json) {
		String response = "false";
		
		try {
			URL url = new URL(MINECRAFT_AUTH_SERVER + payload);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			byte[] requestAsBytes = json.getBytes();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", "MCTextualClient/1.0");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(requestAsBytes.length));
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.write(requestAsBytes);
			wr.flush();
			wr.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			if (br.ready()) {
				response = "";
				
				while(br.ready())
					response += br.readLine();
			}
			else {
				response = "null";
			}
			
			connection.disconnect();
		} catch (MalformedURLException e) {
			return response;
		} catch (IOException e) {
			return response;
		}
		
		return response;
	}
}
