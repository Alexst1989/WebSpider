package ru.alex.st.hh.web;

import java.net.URL;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import ru.alex.st.hh.config.SpiderConfiguration;
import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.tree.TreeNode;

public class PageLoaderCallable implements Callable<PageLoaderResult> {

    private TreeNode<PageData> treeNode;
    private DiskPageWriter diskWriter;
    private LinkParser linkParser;
    private ExecutorService executor;
    private SpiderConfiguration config;

    public PageLoaderCallable(SpiderConfiguration config, TreeNode<PageData> treeNode, DiskPageWriter diskWriter,
                    ExecutorService executor) {
        this.config = config;
        this.treeNode = treeNode;
        this.diskWriter = diskWriter;
        // try {
        this.linkParser = new LinkParser(treeNode.getData().getUrl());
        this.executor = executor;
        // } catch (MalformedURLException e) {
        // throw new SpiderException(String.format("Cann't create URL from %s",
        // treeNode.getData().getUrl()));
        // }

    }

    @Override
    public PageLoaderResult call() throws Exception {
        Path path = diskWriter.writePage(treeNode, linkParser);
        treeNode.getData().setDiskPath(path);
        if (treeNode.getLevel() <= config.getDepth()) {
            Set<String> linkList = linkParser.getLinks();
            for (String link : linkList) {
                URL url = new URL(link);
                TreeNode<PageData> node = treeNode.addChild(new PageData(url, null));
                executor.submit(new PageLoaderCallable(config, node, this.diskWriter, executor));
            }
        }
        return null;
    }

}
