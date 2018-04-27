package main;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;

public class WebPage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7373624237238116233L;
	public URL url;
	public String date;
	public int page_size;
	public ArrayList<String> childLinks;
	public String title;
	public Vector<String> content;
	
	public WebPage(){
		
	}
	
	public void printAll(){

		System.out.println("Page Title: "+title);
		System.out.println("URL: " + url.toString());
		System.out.println("Last Modified Date: " + date);
		if (page_size>=0)
			System.out.println("Size of Page: " + page_size);
		else
			System.out.println("Size of Page: IS NOT FOUND");
		
		System.out.println("");
		
		System.out.println("Words in "+url.toString()+":");
		for(int i = 0; i < content.size(); i++)
			System.out.print(content.get(i)+" ");
		System.out.println("\n\n");
		
		for(int i = 0; i < childLinks.size(); i++)		
		System.out.println("Child Link " + i + " " + childLinks.get(i));
		System.out.println("");
		
		
	}
}