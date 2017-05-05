package ru.alex.st.hh.web.spyder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.SpyderConfiguration;

public class WebSpyder {
	
	private static final Logger LOGGER = LogManager.getLogger(WebSpyder.class);
	
	private String startUrl;
	
	private int depth;
	
	public WebSpyder(SpyderConfiguration config) {
		this.startUrl = config.getStartUrl();
		this.depth = config.getDepth();
		
	}
	
	

}
