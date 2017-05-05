package ru.alex.st.hh.config;

public class ConfigurationBuilder  {
	
	private SpyderConfiguration config;
	
	public ConfigurationBuilder() {
		config = new SpyderConfigurationImpl();
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
	
	public SpyderConfiguration build() {
		return config;
	}

}
