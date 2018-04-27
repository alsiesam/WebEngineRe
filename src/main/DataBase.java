package main;


import java.io.IOException;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;

public class DataBase
{
    private RecordManager recman;
    private HTree hashtable;

    DataBase(String recordmanager, String objectname) throws IOException
    {
        recman = RecordManagerFactory.createRecordManager(recordmanager);
        long recid = recman.getNamedObject(objectname);
            
        if (recid != 0)
            hashtable = HTree.load(recman, recid);
        else
        {
            hashtable = HTree.createInstance(recman);
            recman.setNamedObject( objectname, hashtable.getRecid() );
        }
    }


    public void finalize() throws IOException
    {
        recman.commit();
        recman.close();                
    } 

    public void addEntry(String url, WebPage webPage) throws IOException
    {
        // Add a "docX Y" entry for the key "word" into hashtable
        // ADD YOUR CODES HERE
    	//Serialization srhelper = new Serialization();
    	//hashtable.put(url, webPage);
    	//WebPage content = (WebPage)srhelper.deserialize(hashtable.get(url));
        if (hashtable.get(url) == null) 
        {
        	hashtable.put(url, webPage);
        }
        //System.out.println("Added");
        
    }
    public void delEntry(String url) throws IOException
    {
        // Delete the word and its list from the hashtable
        // ADD YOUR CODES HERE
        hashtable.remove(url);
        //System.out.println("Deleted");
    } 
    
    
    public void printAll() throws IOException
    {
        // Print all the data in the hashtable
        // ADD YOUR CODES HERE
    	
    	FastIterator iter = hashtable.keys();
        String key;
        while( (key=(String)iter.next()) != null ) {
            {
            	WebPage temp_webPage = (WebPage) hashtable.get(key);
            	//System.out.println(key + " = " );
            	temp_webPage.printAll();
            }
        }
    } 
}