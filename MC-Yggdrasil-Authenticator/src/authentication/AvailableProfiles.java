package authentication;

/**
 * Contains an id and name that stores information about the current
 * profiles available as defined by what is returned by the Mojang authentication
 * server.
 * 
 * @author wpenson
 *
 */
public class AvailableProfiles {
	private String id;
	private String name;
	
	/**
	 * Default constructor.
	 */
	public AvailableProfiles() {}
	
	/**
	 * Returns ID.
	 * 
	 * @return ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets ID.
	 * 
	 * @param id ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Returns name.
	 * 
	 * @return Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets name.
	 * 
	 * @param name Name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
