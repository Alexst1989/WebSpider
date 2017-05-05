package ru.alex.st.hh.config;

public interface SpyderConfiguration {
 
	/**
	 * Path in file system, where spyder will hold articles
	 * 
	 * @return String with file path
	 */
	String getDiskStoragePath();
	
	/**
	 * Path in file system, where spyder will hold articles
	 * 
	 * @param diskStoragePath
	 */
	void setDiskStoragePath(String diskStoragePath);
	
	/**
	 * Returns a start url for spyder, where it begins to build tree of links
	 * 
	 * @return start url for spyder
	 */
	String getStartUrl();
	
	/**
	 * Sets a start url for spyder, where it begins to build tree of links
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


}
