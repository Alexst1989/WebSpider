package ru.alex.st.hh.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.programm.Programm;
import ru.alex.st.hh.tree.TreeNode;

public class WebPageLoader implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Programm.class);

    private TreeNode<PageData> treeNode;

    private DiskPageWriter pageWriter;

    private boolean finished;

    public WebPageLoader(TreeNode<PageData> treeNode, DiskPageWriter pageWriter) {
        this.treeNode = treeNode;
        this.pageWriter = pageWriter;
    }

    @Override
    public void run() {
        LinkParser parser = null;
        parser = new LinkParser(treeNode.getData().getUrl());
        Path diskPath = pageWriter.writePage(treeNode, parser);
        Set<String> linkSet = parser.getLinks();
        for (String link : linkSet) {
            try {
                treeNode.addChild(new PageData(new URL(link), null));
            } catch (MalformedURLException e) {
            }
        }

        treeNode.getData().setDiskPath(diskPath);
    }

    public boolean isFinished() {
        return finished;
    }

}
