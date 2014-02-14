package com.axaih.common.boot;

import java.util.Map;

/**
 * <p>BootImpl class.</p>
 *
 * @author andreani
 * @version $Id: $Id
 */
public class BootImpl implements Boot{

	 private PathImpl path;

	 private PathImpl l0path;
	 private String initState; 
	 private Map<String,Object >InitData; 
	 


	public PathImpl getL0path() {
		return l0path;
	}

	public void setL0path(PathImpl l0path) {
		this.l0path = l0path;
	}

	/**
	 * <p>getInitData.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, Object> getInitData() {
		return InitData;
	}

	/** {@inheritDoc} */
	public void setInitData(Map<String, Object> initData) {
		InitData = initData;
	}

	/**
	 * <p>getInitState.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getInitState() {
		return initState;
	}

	/** {@inheritDoc} */
	public void setInitState(String initState) {
		this.initState = initState;
	}

	/**
	 * <p>Getter for the field <code>path</code>.</p>
	 *
	 * @return a {@link com.axaih.shorts.common.boot.PathImpl} object.
	 */
	public PathImpl getPath() {
		return path;
	}

	/** {@inheritDoc} */
	public void setPath(PathImpl path) {
		this.path = path;
	}
	 
}
