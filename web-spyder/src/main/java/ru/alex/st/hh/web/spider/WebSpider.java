package ru.alex.st.hh.web.spider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.disk.search.SearchResult;
import ru.alex.st.hh.tree.TreeNode;
import ru.alex.st.hh.web.PageData;
import ru.alex.st.hh.web.WebPageLoader;

public class WebSpider {
	
	private static final Logger LOGGER = LogManager.getLogger(WebSpider.class);
	
	private SpiderConfiguration config;
	
	private WebPageLoader loader;
	
	private TreeNode<PageData> treeNode;
	
	public WebSpider(SpiderConfiguration config) {
	    this.config = config;
		treeNode = new TreeNode<PageData>(new PageData(this.config.getStartUrl(), null));
	}
	
	public void loadPages() {
//	    loader = new WebPageLoader(treeNode, new DiskPageWriter(config));
//	    Thread loaderThread = new Thread(loader);
//	    loaderThread.start();
	}
	
	
	public SearchResult searchInLoadedPages(String wordToSearch) {
	    if (!loader.isFinished()) {
	        throw new SpiderException("Spider havn't load web pages");
	    }
	    SearchResult resultSearch = new SearchResult();
	    
	    return resultSearch;
	}
	

}
