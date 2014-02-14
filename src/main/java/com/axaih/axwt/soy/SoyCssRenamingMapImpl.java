package com.axaih.axwt.soy;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.template.soy.shared.SoyCssRenamingMap;

/**
 * <p>
 * SoyCssRenamingMapImpl class.
 * </p>
 * 
 * @author andreani
 * @version $Id: $Id
 */
public class SoyCssRenamingMapImpl implements SoyCssRenamingMap {
	/** Constant <code>isMultipleCommandsSuppported=false</code> */
	static boolean isMultipleCommandsSuppported = false;
	/** Constant <code>logger</code> */

	private static final Logger logger = Logger
			.getLogger(SoyCssRenamingMapImpl.class.getName());

	private Map<String, String> map;

	/**
	 * <p>
	 * Constructor for SoyCssRenamingMapImpl.
	 * </p>
	 * 
	 * @param map
	 *            a {@link java.util.Map} object.
	 */
	public SoyCssRenamingMapImpl(Map<String, String> map) {
		this.map = map;
	}

	/** {@inheritDoc} */
	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		String ret = map.get(key);
		if (ret == null) {
			logger.log(Level.FINE, "get miss to key:" + key);
			ret = parseTopDown(key);
			logger.log(java.util.logging.Level.FINE, "css renaming: key=" + key
					+ " | value: " + ret);
		}
		return ret;
	}

	/**
	 * <p>
	 * parse.
	 * </p>
	 * 
	 * @param string
	 *            a {@link java.lang.String} object.
	 * @param tokenRenaming
	 *            a {@link java.lang.String} object.
	 * @param regexKey
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	private String parse(String string, String tokenRenaming, String regexKey) {
		String ret = null;
		String partialRenaming = null;
		String[] keyArray = string.split(regexKey);
		String renamingStr = partialRenaming = this.parseTopDown(keyArray[0]);
		if (renamingStr != null) {
			int i = 1;
			while ((i < keyArray.length)
					&& ((partialRenaming = this.parseTopDown(keyArray[i])) != null)) {
				renamingStr = renamingStr.concat(tokenRenaming).concat(
						partialRenaming);
				i++;
			}
			if (partialRenaming != null) {
				map.put(string, renamingStr);
				ret = renamingStr;
			}
		}
		return ret;
	}

	/**
	 * <p>
	 * parseTopDown.
	 * </p>
	 * 
	 * @param string
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	private String parseTopDown(String string) {
		String ret = null;
		if (string.contains(" ")) {
			if (isMultipleCommandsSuppported) {
				ret = this.parse(string, " ", "\\s+");
			} else {
				ret = null;
			}
		} else if (string.contains("-")) {
			ret = this.parse(string, "-", "-");
		} else {
			ret = map.get(string);
			if (ret == null) {
				ret = string;
			}
		}
		return ret;
	}
}
