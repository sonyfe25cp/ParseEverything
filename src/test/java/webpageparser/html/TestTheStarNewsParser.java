/**
 * 
 */
package webpageparser.html;

/**
 *
 * @author Mi Jing
 * @date 2014-3-18
 */

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webpageparser.utils.Utils;


import com.parseeverything.html.TheStarNewsParser;
import com.parseeverything.result.NewsModel;

public class TestTheStarNewsParser extends TestCase{
	
	static Logger logger = LoggerFactory.getLogger(TestTheStarNewsParser.class);
	
    @Test
    public void testParse() {
    	TheStarNewsParser tsnp = new TheStarNewsParser();
        NewsModel news = tsnp.parse("http://www.thestar.com.my/Business/Business-News/2014/03/18/Msia-China-ramping-up-RandD-report/",
                Utils.getResouce("TheStarNews-001.html"));
        Assert.assertEquals("Malaysia, China ramping up R&D, says report", news.getTitle());
        Assert.assertEquals("2014-03-18", news.getDate());
        Assert.assertEquals("http://www.thestar.com.my/Business/Business-News/2014/03/18/Msia-China-ramping-up-RandD-report/", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {
       	TheStarNewsParser tsnp = new TheStarNewsParser();
        NewsModel news = tsnp.parse("http://www.thestar.com.my/News/Nation/2014/03/18/Missing-MH370-Not-terror-hub/",
                Utils.getResouce("TheStarNews-002.html"));
        Assert.assertEquals("Missing MH370: Malaysia not a terror hub, says Hisham", news.getTitle());
        Assert.assertEquals("2014-03-18", news.getDate());
        Assert.assertEquals("http://www.thestar.com.my/News/Nation/2014/03/18/Missing-MH370-Not-terror-hub/", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse3() {
       	TheStarNewsParser tsnp = new TheStarNewsParser();
        NewsModel news = tsnp.parse("http://www.thestar.com.my/News/World/2014/03/18/Libyan-port-rebels-say-US-seizure-of-oil-tanker-act-of-piracy/",
                Utils.getResouce("TheStarNews-003.html"));
        Assert.assertEquals("Libyan port rebels say U.S. seizure of oil tanker act of piracy", news.getTitle());
        Assert.assertEquals("2014-03-18", news.getDate());
        Assert.assertEquals("http://www.thestar.com.my/News/World/2014/03/18/Libyan-port-rebels-say-US-seizure-of-oil-tanker-act-of-piracy/", news.getUrl());
        System.out.println(news);
    }
    


}
