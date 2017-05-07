package ru.alex.st.hh.web.spider;

import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.disk.search.DiskSearcher;
import ru.alex.st.hh.disk.search.SearchResult;
import ru.alex.st.hh.tree.TreeNode;
import ru.alex.st.hh.web.PageData;
import ru.alex.st.hh.web.PageLoaderRunnable;

public class WebSpider {

    private static final Logger LOGGER = LogManager.getLogger(WebSpider.class);

    private SpiderConfiguration config;
    private TreeNode<PageData> treeNode;
    private boolean isLoaded;
    private ConcurrentSkipListSet<String> globalLinkSet = new ConcurrentSkipListSet<>();

    public WebSpider(SpiderConfiguration config) {
        this.config = config;
        treeNode = new TreeNode<PageData>(new PageData(this.config.getStartUrl(), null));
    }

    public void loadPages() {
        DiskPageWriter diskWriter = new DiskPageWriter(config);
        PageLoaderRunnable runnable = new PageLoaderRunnable(config, treeNode, diskWriter, globalLinkSet);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            LOGGER.error("Error", e);
        }
        isLoaded = true;
    }

    public SearchResult searchInLoadedPages(String regex) {
        if (!isLoaded) {
            throw new SpiderException("Spider havn't load web pages");
        }
        DiskSearcher searcher = new DiskSearcher(config, treeNode, regex);
        return searcher.search();
    }

}
