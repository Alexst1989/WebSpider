package ru.alex.st.hh.config;

import java.util.Locale;


public class SpiderConfigurationImpl implements SpiderConfiguration {

	private String diskStoragePath;

	private String startUrl;

	private int depth;
	
	private Locale locale = Locale.getDefault();

	@Override
	public String getDiskStoragePath() {
		return diskStoragePath;
	}

	@Override
	public void setDiskStoragePath(String diskStoragePath) {
		this.diskStoragePath = diskStoragePath;
	}

	@Override
	public String getStartUrl() {
		return startUrl;
	}

	@Override
	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public void setLocale(String localeStringValue) {
		locale = new Locale(localeStringValue);
	}

	
}
