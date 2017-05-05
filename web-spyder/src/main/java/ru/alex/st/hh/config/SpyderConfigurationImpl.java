package ru.alex.st.hh.config;

public class SpyderConfigurationImpl implements SpyderConfiguration {

	private String diskStoragePath;

	private String startUrl;

	private int depth;

	public String getDiskStoragePath() {
		return diskStoragePath;
	}

	public void setDiskStoragePath(String diskStoragePath) {
		this.diskStoragePath = diskStoragePath;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	
}
