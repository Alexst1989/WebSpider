package ru.alex.st.hh.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.tree.TreeNode;

public class PageLoaderRunnable implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(PageLoaderRunnable.class);

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
        try {
            Path path = diskWriter.writePage(treeNode, linkParser);
            treeNode.getData().setDiskPath(path);
            List<Future> futureList = new LinkedList<>();
            if (treeNode.getLevel() < config.getDepth()) {
                ExecutorService InnerExecutor = Executors.newFixedThreadPool(getNThreads());
                Set<String> linkList = linkParser.getLinks();
                LOGGER.info("Page {}. Links will be processed {}", treeNode.getData().getUrl().toString(),
                                linkList.size());
                int counter = 0;
                for (String link : linkList) {
                    counter++;
                    lock.lock();
                    try {
                        if (!globalLinkSet.contains(link)) {
                            globalLinkSet.add(link);
                            lock.unlock();
                            LOGGER.debug("number {} of {}. found link: {}. New thread will be created", counter,
                                            linkList.size(), link);
                            URL url = new URL(link);
                            TreeNode<PageData> node = treeNode.addChild(new PageData(url, null));
                            futureList.add(InnerExecutor
                                            .submit(new PageLoaderRunnable(config, node, diskWriter, globalLinkSet)));
                        } else {
                            LOGGER.trace("Dublicte found. Link {} has already been processed", link);
                        }
                    } catch (MalformedURLException e) {
                        LOGGER.error("Bad url", e);
                    } finally {
                        if (lock.isLocked())
                            lock.unlock();
                    }
                }
                LOGGER.trace("TthreadPool {} is waiting for children completition", InnerExecutor);
                for (Future future : futureList) {
                    future.get();
                }
                LOGGER.trace("TthreadPool {} is ready to shutdown", InnerExecutor);
                InnerExecutor.shutdown();
                LOGGER.trace("ThreadPool {} is shut down.", InnerExecutor);
            }
        } catch (Exception ex) {
            LOGGER.error("Exception caught. Thread will finish now.", ex);
            return;
        }
    }

    private static int getNThreads() {
        return Runtime.getRuntime().availableProcessors();
    }

}
