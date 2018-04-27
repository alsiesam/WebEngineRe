package main;
/* --
COMP4321 Lab2 Exercise
Student Name:
Student ID:
Section:
Email:
*/

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

public class Crawler
{
	private String url;
	public static Vector<String> urls = new Vector<String>();

	Crawler(String _url)
	{
		url = _url;
	}
	public Vector<String> extractWords() throws ParserException

	{
		// extract words in url and return them
		// use StringTokenizer to tokenize the result from StringBean
		// ADD YOUR CODES HERE
		Vector<String> result = new Vector<String>();
		StringBean bean = new StringBean();
		bean.setURL(url);
		bean.setLinks(false);
		String contents = bean.getStrings();
		StringTokenizer st = new StringTokenizer(contents);
		while (st.hasMoreTokens()) {
		    result.add(st.nextToken());
		}
		return result;
	}
	public ArrayList<String> extractLinks() throws ParserException

	{
		// extract links in url and return them
		// ADD YOUR CODES HERE
		ArrayList<String> result = new ArrayList<String>();
		LinkBean bean = new LinkBean();
		bean.setURL(url);
		URL[] urls = bean.getLinks();
		for (URL s : urls) {
		    result.add(s.toString());
		}
		return result;
	}
	
	public void extractDetail(Crawler crawler, WebPage webPage) throws ParserException

	{
		
		webPage.page_size = new Integer(-1);
		try{
			
			webPage.url = new URL(crawler.url);
			HttpURLConnection conn = (HttpURLConnection)webPage.url.openConnection();
			conn.setDoOutput(false);
			conn.setRequestMethod("HEAD");
			
			Map<String, List<String>> fields = conn.getHeaderFields();

			if (fields.get("Content-Length") != null && !fields.get("Content-Length").isEmpty())
			{
				String sLength = (String) fields.get("Content-Length").get(0);
				
				if (sLength != null)
					webPage.page_size = Integer.parseInt(sLength);
			}
			
			if (fields.get("Last-Modified") != null && !fields.get("Last-Modified").isEmpty())
			{
				String sLength = (String) fields.get("Last-Modified").get(0);
				
				if (sLength != null)
					webPage.date =  sLength;
			}
			else if (fields.get("Date") != null && !fields.get("Date").isEmpty())
			{
				String sLength = (String) fields.get("Date").get(0);
				
				if (sLength != null)
					webPage.date =  sLength;
			}
			
			// Debug: System.out.println("urlConnection:"+urlConnection.getURL());

		} catch (java.net.MalformedURLException e){
			e.printStackTrace ();
		} catch (IOException e){
			e.printStackTrace ();
		}
		// Debug: parser.setResource("http://www.yahoo.com");
		
		
		Parser parser = new Parser();
		parser.setResource(crawler.url);
		TitleTag title = new TitleTag();
		TagNode tnode = new TagNode();
		tnode.setChildren(parser.extractAllNodesThatMatch(new AndFilter()));

		
		
		for(int i=0; i<tnode.getChildren().size();i++){
			
			if (tnode.getChildren().elementAt(i) instanceof TitleTag)
			{
				title = (TitleTag) tnode.getChildren().elementAt(i);
				webPage.title = title.getTitle();
			}
			
		}
		
		
	}
	
}