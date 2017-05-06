package ru.alex.st.hh.web.spider;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.disk.search.SearchResult;
import ru.alex.st.hh.tree.TreeNode;
import ru.alex.st.hh.web.PageData;
import ru.alex.st.hh.web.PageLoaderCallable;
import ru.alex.st.hh.web.PageLoaderResult;

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
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 3);
        DiskPageWriter diskWriter = new DiskPageWriter(config);
        PageLoaderCallable callable = new PageLoaderCallable(config, treeNode, diskWriter, executor, globalLinkSet);
        Future<PageLoaderResult> future = executor.submit(callable);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
             LOGGER.error("exception", e);
        }
        executor.shutdown();
    }

    public SearchResult searchInLoadedPages(String wordToSearch) {
        if (!isLoaded) {
            throw new SpiderException("Spider havn't load web pages");
        }
        SearchResult resultSearch = new SearchResult();

        return resultSearch;
    }

}
