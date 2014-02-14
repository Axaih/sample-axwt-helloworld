package com.axaih.common.boot;

import java.util.Map;

/**
 * <p>
 * Boot interface.
 * </p>
 * 
 * @author andreani
 * @version $Id: $Id
 */
public interface Boot {

	public PathImpl getL0path();

	public void setL0path(PathImpl l0path);

	/**
	 * <p>
	 * getInitData.
	 * </p>
	 * 
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, Object> getInitData();

	/**
	 * <p>
	 * setInitData.
	 * </p>
	 * 
	 * @param initData
	 *            a {@link java.util.Map} object.
	 */
	public void setInitData(Map<String, Object> initData);

	/**
	 * <p>
	 * getInitState.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getInitState();

	/**
	 * <p>
	 * setInitState.
	 * </p>
	 * 
	 * @param initState
	 *            a {@link java.lang.String} object.
	 */
	public void setInitState(String initState);

	/**
	 * <p>
	 * getPath.
	 * </p>
	 * 
	 * @return a {@link com.axaih.shorts.common.boot.PathImpl} object.
	 */
	public PathImpl getPath();

	/**
	 * <p>
	 * setPath.
	 * </p>
	 * 
	 * @param path
	 *            a {@link com.axaih.shorts.common.boot.PathImpl} object.
	 */
	public void setPath(PathImpl path);

}
