package ru.alex.st.hh.config;

public class ConfigurationBuilder  {
	
	private SpiderConfiguration config;
	
	public ConfigurationBuilder() {
		config = new SpiderConfigurationImpl();
	}
	
	public ConfigurationBuilder setDiskStoragePath(String diskStoragePath) {
		config.setDiskStoragePath(diskStoragePath);
		return this;
	}
	
	public ConfigurationBuilder setStartUrl(String startUrl) {
		config.setStartUrl(startUrl);
		return this;
	}
	
	public ConfigurationBuilder setDepth(int depth) {
		config.setDepth(depth);
		return this;
	}
	
	public ConfigurationBuilder setLocale(String localeStringValue) {
		config.setLocale(localeStringValue);
		return this;
	}
	
	public ConfigurationBuilder setLinkLevelLimit(long limit) {
	    config.setLinkLevelLimit(limit);
	    return this;
	}
	
	public SpiderConfiguration build() {
		return config;
	}

}
