package com.axaih.configuration;

import com.google.inject.AbstractModule;

public class ConfigurationModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ConfigurationService.class).to(ConfigurationServiceImpl.class);
	}

}
