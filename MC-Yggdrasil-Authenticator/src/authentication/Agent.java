package authentication;

/**
 * Class used for serialization by Gson to build up the json payloads. Specifies the game to
 * be authenticated to on the Mojang servers.
 * 
 * @author wpenson
 *
 */
public class Agent {
	private String name;
	private String version;

	/**
	 * Specifies that the client is requesting Minecraft on the Mojang authentication servers.
	 */
	public Agent() {
		name = "Minecraft";
		version = "1";
	}
	
	/**
	 * Returns the name contained in Agent.
	 * 
	 * @return The string "Minecraft"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the version used for authentication.
	 * 
	 * @return The string "1" typically
	 */
	public String getVersion() {
		return version;
	}
}
