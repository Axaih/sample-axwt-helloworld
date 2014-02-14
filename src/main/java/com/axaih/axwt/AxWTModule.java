package com.axaih.axwt;

import javax.servlet.ServletContext;

import org.springframework.core.io.Resource;

import com.axaih.axwt.soy.ClosureTemplateConfig;
import com.axaih.axwt.soy.ClosureTemplateConfigurer;
import com.axaih.axwt.soy.ClosureTemplateViewResolver;
import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;

public class AxWTModule extends ServletModule {
	@Override
	protected void configureServlets() {
		// TODO Auto-generated method stub

		bind(ClosureTemplateConfig.class).to(ClosureTemplateConfigurer.class);

		bind(ClosureTemplateViewResolver.class);

	}

	@Provides
	ClosureTemplateConfigurer provideClosureTemplateConfigurer(
			ClosureTemplateConfig closureTemplateConfig) {

		ServletContext servletContext = getServletContext();
	
		Resource templatesLocation = new org.springframework.core.io.FileSystemResource(
				servletContext.getRealPath("/WEB-INF/templates/"));

		Resource renamingMapFileLocation = new org.springframework.core.io.FileSystemResource(
				servletContext.getRealPath("/WEB-INF/renaming-map/"));
		Resource embeddedResourcesLocation = new org.springframework.core.io.FileSystemResource(
				servletContext.getRealPath("/WEB-INF/embeddedResources/"));

		ClosureTemplateConfigurer closureTemplateConfigurer = new ClosureTemplateConfigurer(
				 templatesLocation, embeddedResourcesLocation,  renamingMapFileLocation);
	 
		return closureTemplateConfigurer;

	}

}
