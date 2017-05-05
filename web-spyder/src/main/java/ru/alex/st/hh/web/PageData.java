package ru.alex.st.hh.web;

import java.nio.file.Path;

public class PageData {
	
	private String urlString;
	
	private Path diskPath;

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public Path getDiskPath() {
		return diskPath;
	}

	public void setDiskPath(Path diskPath) {
		this.diskPath = diskPath;
	}
	
	

}
