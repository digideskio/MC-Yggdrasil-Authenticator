package authentication;

/**
 * Contains an id and name which stores the relevant information about the selected
 * profile as defined by what is returned by the Mojang authentication server.
 * 
 * @author wpenson
 *
 */
public class SelectedProfile {
	private String id;
	private String name;
	
	/**
	 * Default constructor.
	 */
	public SelectedProfile() {}
	
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
