package ru.alex.st.hh.tree;

import org.testng.annotations.Test;

public class TreeNodeTest {
	
	private TreeNode<String> tree = new TreeNode<String>("https://ru.wikipedia.org/wiki");
	
	@Test
	public void testTree() {
		TreeNode<String> node2_1 = tree.addChild("https://ru.wikipedia.org/wiki_2_1");
		TreeNode<String> node2_2 = tree.addChild("https://ru.wikipedia.org/wiki_2_2");
		TreeNode<String> node2_3 = tree.addChild("https://ru.wikipedia.org/wiki_2_3");
		
		TreeNode<String> node3_1 = node2_1.addChild("https://ru.wikipedia.org/wiki_3_1");
		
		for (TreeNode<String> node : tree) {
			System.out.println(node.data);
		}
		
		
	}
	

}
