/**
 * Copyright (c) 2013 Axaih Ltda
 * Permission is hereby granted, free of charge, to any person obtaining a copy of 
 * this software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished 
 * to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 */
package com.axaih.axwt.soy;

import org.springframework.core.io.Resource;

/**
 * <p>
 * ClosureTemplateConfig interface.
 * </p>
 *
 * @author andreani
 * @version $Id: $Id
 */
public interface ClosureTemplateConfig {

	/**
	 * <p>
	 * getTemplatesLocation.
	 * </p>
	 *
	 * @return a {@link org.springframework.core.io.Resource} object.
	 */
	Resource getTemplatesLocation();

	/**
	 * <p>
	 * getEmbeddedResourcesLocation.
	 * </p>
	 *
	 * @return a {@link org.springframework.core.io.Resource} object.
	 */
	Resource getEmbeddedResourcesLocation();

	/**
	 * <p>
	 * getRenamingMapFileLocation.
	 * </p>
	 *
	 * @return a {@link org.springframework.core.io.Resource} object.
	 */
	Resource getRenamingMapFileLocation();

	/**
	 * <p>
	 * isRecursive.
	 * </p>
	 *
	 * @return a boolean.
	 */
	boolean isRecursive();

	/**
	 * <p>
	 * isDevMode.
	 * </p>
	 *
	 * @return a boolean.
	 */
	boolean isDevMode();
}
