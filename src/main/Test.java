package main;
import java.util.Vector;

import org.htmlparser.beans.StringBean;
import org.htmlparser.Node;
import org.htmlparser.util.NodeList;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.StringTokenizer;

import org.htmlparser.beans.LinkBean;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.htree.HTree;
import jdbm.helper.FastIterator;
import java.io.Serializable;

import org.htmlparser.util.ParserException;

public class Test {
	public static Vector<String> urls = new Vector<String>();
	public static void main(String[] args)
	{
		BufferedReader br = null;
		FileReader fr = null;
		
		try
		{
			fr = new FileReader("url.txt");
			br = new BufferedReader(fr);
			
			String sCurrentLine;

			while((sCurrentLine = br.readLine()) !=null)
			{
				urls.add(sCurrentLine);

			}

		} catch (IOException e){
			e.printStackTrace ();
		} finally{
			
				try{
					if(br!=null)
						br.close();
					if(fr!=null)
						fr.close();
				} catch (IOException e){
					e.printStackTrace ();
				}
			
			}
		
		try
        {
            DataBase dataBase = new DataBase("spider","ht1");

            try
    			{
    			
	    			//Crawler crawler = new Crawler("http://www.cs.ust.hk/~dlee/4321/");
	    			for (String s : urls){
	
	            		Crawler crawler = new Crawler(s);
	    				WebPage webPage = new WebPage();
	    			
	    				crawler.extractDetail(crawler, webPage);
	    				webPage.content = crawler.extractWords();		
	    				webPage.childLinks = crawler.extractLinks();
	    				
	    				dataBase.addEntry(s, webPage);
	
	    			}
    			
    			}
    			catch (ParserException e)
            {
                    	e.printStackTrace ();
            }
            dataBase.printAll();
            dataBase.finalize();
        }
        catch(IOException ex)
        {
            System.err.println(ex.toString());
        }
		
		
		
	}
}
