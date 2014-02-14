package com.axaih.axwt.soy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.template.soy.data.SoyMapData;

/**
 * <p>
 * EmbeddedResourcesMapImpl class.
 * </p>
 * 
 * @author andreani
 * @version $Id: $Id
 */
public class EmbeddedResourcesMapImpl implements EmbeddedResourcesMap {
	Map<String, SoyMapData> map;

	/**
	 * <p>
	 * Constructor for EmbeddedResourcesMapImpl.
	 * </p>
	 * 
	 * @param embeddedJsResourcesMap
	 *            a {@link java.util.Map} object.
	 * @param embeddedCssResourcesMap
	 *            a {@link java.util.Map} object.
	 */
	EmbeddedResourcesMapImpl(Map<String, String> embeddedJsResourcesMap,
			Map<String, String> embeddedCssResourcesMap) {

		this.map = new HashMap<String, SoyMapData>();
		Set<String> union = new HashSet<String>(embeddedJsResourcesMap.keySet());
		union.addAll(embeddedCssResourcesMap.keySet());

		for (String key : union) {
			SoyMapData soyMapData = new SoyMapData();

			String embeddedResource = embeddedJsResourcesMap.get(key);
			if (embeddedResource != null) {
				soyMapData.put("embeddedJsResource", embeddedResource);
			}

			embeddedResource = embeddedCssResourcesMap.get(key);
			if (embeddedResource != null) {

				soyMapData.put("embeddedCssResource", embeddedResource);
			}

			map.put(key, soyMapData);
		}

	}
 
	public SoyMapData get(String key) {
		// TODO Auto-generated method stub

		return this.map.get(key);
	}

}
