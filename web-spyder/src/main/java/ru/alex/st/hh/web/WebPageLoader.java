package ru.alex.st.hh.web;

import java.nio.file.Path;

import ru.alex.st.hh.disk.DiskPageWriter;
import ru.alex.st.hh.tree.TreeNode;

public class WebPageLoader implements Runnable {
	
	private TreeNode<PageData> treeNode;
	
	private DiskPageWriter pageWriter;
	
	private boolean finished;
	
	public WebPageLoader(TreeNode<PageData> treeNode, DiskPageWriter pageWriter) {
		this.treeNode = treeNode;
		this.pageWriter = pageWriter;
	}

	@Override
	public void run() {
	    Path diskPath = pageWriter.writePage(treeNode.getData().getUrlString(), "1.txt");
	    treeNode.getData().setDiskPath(diskPath);
	}
	
	public boolean isFinished() {
	    return finished;
	}
	
	

}
