package com.axaih.axwt.soy;

import org.springframework.core.io.Resource;

import com.google.inject.Singleton;

/**
 * <p>
 * ClosureTemplateConfigurer class.
 * </p>
 * 
 * @author andreani
 * @version $Id: $Id
 */
@Singleton
public class ClosureTemplateConfigurer implements ClosureTemplateConfig {

	private Resource templatesLocation;
	private Resource renamingMapFileLocation;
	private Resource embeddedResourcesLocation;

	private boolean recursive = true;
	private boolean devMode = false;

	public ClosureTemplateConfigurer(Resource templatesLocation,
			Resource embeddedResourcesLocation, Resource renamingMapFileLocation) {
		this.templatesLocation = templatesLocation;
		this.embeddedResourcesLocation = embeddedResourcesLocation;
		this.renamingMapFileLocation = renamingMapFileLocation;
	}

	/**
	 * <p>
	 * Setter for the field <code>templatesLocation</code>.
	 * </p>
	 * 
	 * @param templatesLocation
	 *            a {@link org.springframework.core.io.Resource} object.
	 */
	public void setTemplatesLocation(Resource templatesLocation) {
		this.templatesLocation = templatesLocation;
	}

	public Resource getTemplatesLocation() {
		return templatesLocation;
	}

	/**
	 * <p>
	 * Setter for the field <code>embeddedResourcesLocation</code>.
	 * </p>
	 * 
	 * @param embeddedResourcesLocation
	 *            a {@link org.springframework.core.io.Resource} object.
	 */
	public void setEmbeddedResourcesLocation(Resource embeddedResourcesLocation) {
		this.embeddedResourcesLocation = embeddedResourcesLocation;
	}

	public Resource getEmbeddedResourcesLocation() {
		// TODO Auto-generated method stub
		return this.embeddedResourcesLocation;
	}

	/**
	 * <p>
	 * Getter for the field <code>renamingMapFileLocation</code>.
	 * </p>
	 * 
	 * @return a {@link org.springframework.core.io.Resource} object.
	 */
	public Resource getRenamingMapFileLocation() {
		return renamingMapFileLocation;
	}

	/**
	 * <p>
	 * Setter for the field <code>renamingMapFileLocation</code>.
	 * </p>
	 * 
	 * @param renamingMapFileLocation
	 *            a {@link org.springframework.core.io.Resource} object.
	 */
	public void setRenamingMapFileLocation(Resource renamingMapFileLocation) {
		this.renamingMapFileLocation = renamingMapFileLocation;
	}

	/**
	 * <p>
	 * isRecursive.
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public boolean isRecursive() {
		return recursive;
	}

	/**
	 * <p>
	 * Setter for the field <code>recursive</code>.
	 * </p>
	 * 
	 * @param recursive
	 *            a boolean.
	 */
	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	/**
	 * <p>
	 * isDevMode.
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public boolean isDevMode() {
		return devMode;
	}

	/**
	 * <p>
	 * Setter for the field <code>devMode</code>.
	 * </p>
	 * 
	 * @param devMode
	 *            a boolean.
	 */
	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}

}
