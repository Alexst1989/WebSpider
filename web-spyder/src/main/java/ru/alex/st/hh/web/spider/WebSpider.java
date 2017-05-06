package ru.alex.st.hh.web.spider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.disk.search.SearchResult;
import ru.alex.st.hh.tree.TreeNode;
import ru.alex.st.hh.web.PageData;
import ru.alex.st.hh.web.PageLoaderCallable;
import ru.alex.st.hh.web.WebPageLoader;

public class WebSpider {
	
	private static final Logger LOGGER = LogManager.getLogger(WebSpider.class);
	
	private SpiderConfiguration config;
	
	private WebPageLoader loader;
	
	private TreeNode<PageData> treeNode;
	
	private String serverLink;
	
	public WebSpider(SpiderConfiguration config) {
	    this.config = config;
		treeNode = new TreeNode<PageData>(new PageData(this.config.getStartUrl(), null));
		
	}
	
	public void loadPages() {
	    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 3);
	    //loader = new WebPageLoader(treeNode, new DiskPageWriter(config));
	    DiskPageWriter diskWriter = new DiskPageWriter(config);
	    PageLoaderCallable callable = new PageLoaderCallable(config, treeNode, diskWriter, executor);
	    executor.submit(callable);
	    
	}
	
	
	public SearchResult searchInLoadedPages(String wordToSearch) {
	    if (!loader.isFinished()) {
	        throw new SpiderException("Spider havn't load web pages");
	    }
	    SearchResult resultSearch = new SearchResult();
	    
	    return resultSearch;
	}
	

}
