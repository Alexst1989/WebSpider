package ru.alex.st.hh.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.MessageSource;
import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.tree.TreeNode;

public class PageLoaderRunnable implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(PageLoaderRunnable.class);

    private static final String INFO1 = "spider.webloaderrunnable.info1";
    private static final String DEBUG1 = "spider.webloaderrunnable.debug1";
    private static final String TRACE1 = "spider.webloaderrunnable.trace1";
    private static final String TRACE2 = "spider.webloaderrunnable.trace2";
    private static final String TRACE3 = "spider.webloaderrunnable.trace3";
    private static final String TRACE4 = "spider.webloaderrunnable.trace4";
    private static final String ERROR1 = "spider.webloaderrunnable.error1";
    private static final String ERROR2 = "spider.webloaderrunnable.error2";

    private static final ReentrantLock lock = new ReentrantLock();

    private TreeNode<PageData> treeNode;
    private DiskPageWriter diskWriter;
    private LinkParser linkParser;
    private SpiderConfiguration config;
    private Set<String> globalLinkSet;

    public PageLoaderRunnable(SpiderConfiguration config, TreeNode<PageData> treeNode, DiskPageWriter diskWriter,
                    Set<String> globalLinkSet) {
        this.config = config;
        this.treeNode = treeNode;
        this.diskWriter = diskWriter;
        this.globalLinkSet = globalLinkSet;
        this.linkParser = new LinkParser(treeNode.getData().getUrl());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void run() {
        List<Future> futureList = new LinkedList<>();
        ExecutorService InnerExecutor = Executors.newFixedThreadPool(getNThreads());
        try {
            Path path = diskWriter.writePage(treeNode, linkParser);
            treeNode.getData().setDiskPath(path);
            if (treeNode.getLevel() < config.getDepth()) {
                Set<String> linkList = linkParser.getLinks();
                LOGGER.info(MessageSource.getMessage(INFO1, config.getLocale()), treeNode.getData().getUrl().toString(),
                                Math.min(linkList.size(), config.getLinkLevelLimit()));
                int counter = 0;
                for (String link : linkList) {
                    counter++;
                    if (config.getLinkLevelLimit() == 0 || counter <= config.getLinkLevelLimit()) { // For
                                                                                                    // debugging
                                                                                                    // purposes
                        lock.lock();
                        try {
                            if (!globalLinkSet.contains(link)) {
                                globalLinkSet.add(link);
                                lock.unlock();
                                LOGGER.debug(MessageSource.getMessage(DEBUG1, config.getLocale()), counter,
                                                linkList.size(), link);
                                URL url = new URL(link);
                                TreeNode<PageData> node = treeNode.addChild(new PageData(url, null));
                                futureList.add(InnerExecutor.submit(
                                                new PageLoaderRunnable(config, node, diskWriter, globalLinkSet)));
                            } else {
                                LOGGER.trace(MessageSource.getMessage(TRACE1, config.getLocale()), link);
                            }
                        } catch (MalformedURLException e) {
                            LOGGER.error(MessageSource.getMessage(ERROR1, config.getLocale()), e);
                        } finally {
                            if (lock.isLocked())
                                lock.unlock();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error(MessageSource.getMessage(ERROR2, config.getLocale()), ex);
            return;
        } finally {
            LOGGER.trace(MessageSource.getMessage(TRACE2, config.getLocale()), InnerExecutor);
            for (Future future : futureList) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.error(MessageSource.getMessage(ERROR2, config.getLocale()), e);
                }
            }
            LOGGER.trace(MessageSource.getMessage(TRACE3, config.getLocale()), InnerExecutor);
            InnerExecutor.shutdown();
            LOGGER.trace(MessageSource.getMessage(TRACE4, config.getLocale()), InnerExecutor);
        }
    }

    private static int getNThreads() {
        return Runtime.getRuntime().availableProcessors();
    }

}
