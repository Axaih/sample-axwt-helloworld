package com.axaih.configuration;

/**
 * <p>
 * ConfigurationServiceImpl class.
 * </p>
 * 
 * @author andreani
 * @version $Id: $Id
 */
public class ConfigurationServiceImpl implements ConfigurationService {

	private String rootPath;

	private String rootL0path;

	/**
	 * <p>
	 * Constructor for ConfigurationServiceImpl.
	 * </p>
	 */
	public ConfigurationServiceImpl() {
 /*
		rootPath = new String("http://inline.axaih.com/");
		rootL0path = new String("http://inline-l0.axaih.com/"); 
*/
		rootPath = new String("http://inline-tst.axaih.com:8080/");
		rootL0path = new String("http://inline-l0-tst.axaih.com:8080/");
	}
 
	public String getRootL0path() {
		return rootL0path;
	};

	/**
	 * <p>
	 * Getter for the field <code>rootPath</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */ 
	public String getRootPath() {

		return rootPath;
	}

}
