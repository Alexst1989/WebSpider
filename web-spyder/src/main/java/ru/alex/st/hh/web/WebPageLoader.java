package ru.alex.st.hh.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import ru.alex.st.hh.tree.TreeNode;

public class WebPageLoader implements Runnable {
	
	private 
	
	public TreeNode<String> buildTree(String startUrlString, int depth) {
		try {
			URL startUrl = new URL(startUrlString);
			try {
				InputStream is = startUrl.openStream();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void run() {
		
	}
	
	

}
