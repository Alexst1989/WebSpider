package ru.alex.st.hh.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> implements Iterable<TreeNode<T>> {

	T data;
	TreeNode<T> parent;
	List<TreeNode<T>> children;

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}

	public TreeNode(T data) {
		this.data = data;
		this.children = new LinkedList<TreeNode<T>>();
	}

	public TreeNode<T> addChild(T child) {
		TreeNode<T> childNode = new TreeNode<T>(child);
		childNode.parent = this;
		this.children.add(childNode);
		return childNode;
	}

	public int getLevel() {
		if (this.isRoot())
			return 0;
		else
			return parent.getLevel() + 1;
	}
	
	public T getData() {
	    return data;
	}
	
	public TreeNode<T> getParent() {
	    return parent;
	}
	
	public List<TreeNode<T>> getChildren() {
	    return children;
	}

	@Override
	public String toString() {
		return data != null ? data.toString() : "[data null]";
	}

	@Override
	public Iterator<TreeNode<T>> iterator() {
		TreeNodeIterator<T> iter = new TreeNodeIterator<T>(this);
		return iter;
	}
	
	

}