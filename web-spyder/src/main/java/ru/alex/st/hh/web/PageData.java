package ru.alex.st.hh.web;

import java.net.URL;
import java.nio.file.Path;

public class PageData {
	
	private URL urlString;
	
	private Path diskPath;
	
	public PageData(URL urlString, Path diskPath) {
	    this.urlString = urlString;
	    this.diskPath = diskPath;
	}

	public URL getUrl() {
		return urlString;
	}

	public void setUrl(URL url) {
		this.urlString = url;
	}

	public Path getDiskPath() {
		return diskPath;
	}

	public void setDiskPath(Path diskPath) {
		this.diskPath = diskPath;
	}
	
	@Override
	public String toString() {
	    return String.format("PageData {url = %s, diskPath %s}", urlString, String.valueOf(diskPath));
	}

}
