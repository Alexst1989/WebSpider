package ru.alex.st.hh.config;

import java.net.URL;
import java.nio.file.Path;
import java.util.Locale;

public interface SpiderConfiguration {
 
	/**
	 * Path in file system, where spider will hold articles
	 * 
	 * @return String with file path
	 */
	Path getDiskStoragePath();
	
	/**
	 * Path in file system, where spider will hold articles
	 * 
	 * @param diskStoragePath
	 */
	void setDiskStoragePath(String diskStoragePath);
	
	/**
	 * Returns a start url for spider, where it begins to build tree of links
	 * 
	 * @return start url for spider
	 */
	URL getStartUrl();
	
	/**
	 * Sets a start url for spider, where it begins to build tree of links
	 * 
	 * @param startUrl string value of url
	 */
	public void setStartUrl(String startUrl);

	/**
	 * Returns depth level of building links tree 
	 * 
	 * @return int value of depth level
	 */
	public int getDepth();

	/**
	 * Sets a depth level for building links tree
	 * 
	 * @param depth - number of max depth level
	 */
	public void setDepth(int depth);
	
	/**
	 * Local string value for Localised messages
	 * 
	 * @return local from configuration or null
	 */
	public Locale getLocale();


	/**
	 * Sets locale for application
	 */
	public void setLocale(String localeStringValue);
	
}
