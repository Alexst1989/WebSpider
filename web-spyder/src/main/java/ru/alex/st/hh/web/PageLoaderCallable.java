package ru.alex.st.hh.web;

import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.tree.TreeNode;

public class PageLoaderCallable implements Callable<PageLoaderResult> {

    private static final Logger LOGGER = LogManager.getLogger(PageLoaderCallable.class);

    private static final ReentrantLock lock = new ReentrantLock();

    private TreeNode<PageData> treeNode;
    private DiskPageWriter diskWriter;
    private LinkParser linkParser;
    private ExecutorService executor;
    private SpiderConfiguration config;
    private Set<String> globalLinkSet;

    public PageLoaderCallable(SpiderConfiguration config, TreeNode<PageData> treeNode, DiskPageWriter diskWriter,
                    ExecutorService executor, Set<String> globalLinkSet) {
        this.config = config;
        this.treeNode = treeNode;
        this.diskWriter = diskWriter;
        this.globalLinkSet = globalLinkSet;
        this.linkParser = new LinkParser(treeNode.getData().getUrl());
        this.executor = executor;
    }

    @Override
    public PageLoaderResult call() throws Exception {
        Path path = diskWriter.writePage(treeNode, linkParser);
        treeNode.getData().setDiskPath(path);
         List<Future<PageLoaderResult>> futureList = new LinkedList<>();
        if (treeNode.getLevel() < config.getDepth()) {
            ExecutorService InnerExecutor = Executors.newFixedThreadPool(getNThreads());
            Set<String> linkList = linkParser.getLinks();
            LOGGER.info("Page {}. Links will be processed {}", treeNode.getData().getUrl().toString(), linkList.size());
            int counter = 0;
            for (String link : linkList) {
                counter++;
                lock.lock();
                try {
                    if (!globalLinkSet.contains(link)) {
                        globalLinkSet.add(link);
                        lock.unlock();
                        LOGGER.trace("number {} of {}. found link: {}. New thread will be created", counter,
                                        linkList.size(), link);
                        URL url = new URL(link);
                        TreeNode<PageData> node = treeNode.addChild(new PageData(url, null));
                        futureList.add(executor.submit(new PageLoaderCallable(config, node, diskWriter, InnerExecutor, globalLinkSet)));
                    } else {
                        LOGGER.trace("Dublicte found. Link {} has already been processed", link);
                    }
                } finally {
                    if (lock.isLocked())
                        lock.unlock();
                }
            }
            for (Future<PageLoaderResult> future : futureList) {
                future.get();
            }
            InnerExecutor.shutdown();
        }
        
        return null;
    }
    
    private static int getNThreads() {
        return Runtime.getRuntime().availableProcessors() * 3;
    }

}
