package com.axaih.axwt.soy;

import com.google.template.soy.data.SoyMapData;

/**
 * <p>EmbeddedResourcesMap interface.</p>
 *
 * @author andreani
 * @version $Id: $Id
 */
public interface EmbeddedResourcesMap {
	/**
	 * <p>get.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 * @return a {@link com.google.template.soy.data.SoyMapData} object.
	 */
	public SoyMapData get(String key) ;
}
