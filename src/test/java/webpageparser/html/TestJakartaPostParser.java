/**
 * 
 */
package webpageparser.html;

/**
 *
 * @author Mi Jing
 * @date 2014-3-17
 */

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webpageparser.utils.Utils;

import com.parseeverything.html.JakartaPostnewsParser;
import com.parseeverything.result.NewsModel;

public class TestJakartaPostParser extends TestCase{
	
	static Logger logger = LoggerFactory.getLogger(TestJakartaPostParser.class);
	
	//测试national news
    @Test
    public void testParse() {
    	JakartaPostnewsParser jpp = new JakartaPostnewsParser();
        NewsModel news = jpp.parse("http://www.thejakartapost.com/news/2014/03/16/indonesia-needs-tax-savvy-leader-ngos.html",
                Utils.getResouce("JakartaPost-news-001.html"));
        Assert.assertEquals("Indonesia needs tax-savvy leader: NGOs", news.getTitle());
        Assert.assertEquals("2014/03/16", news.getDate());
        Assert.assertEquals("http://www.thejakartapost.com/news/2014/03/16/indonesia-needs-tax-savvy-leader-ngos.html", news.getUrl());
        System.out.println(news);
    }
    
    //测试business news
    @Test
    public void testParse2() {
       	JakartaPostnewsParser jpp = new JakartaPostnewsParser();
        NewsModel news = jpp.parse("http://www.thejakartapost.com/news/2014/03/15/pln-struggles-resolve-power-crisis-n-sumatra.html",
                Utils.getResouce("JakartaPost-news-002.html"));
        Assert.assertEquals("PLN struggles to resolve power crisis in N. Sumatra", news.getTitle());
        Assert.assertEquals("2014/03/15", news.getDate());
        Assert.assertEquals("http://www.thejakartapost.com/news/2014/03/15/pln-struggles-resolve-power-crisis-n-sumatra.html", news.getUrl());
        System.out.println(news);
    }
    
    //测试world news
    @Test
    public void testParse3() {
       	JakartaPostnewsParser jpp = new JakartaPostnewsParser();
        NewsModel news = jpp.parse("http://www.thejakartapost.com/news/2014/03/17/another-group-60-migrants-tries-enter-us.html",
                Utils.getResouce("JakartaPost-news-003.html"));
        Assert.assertEquals("Another group of 60 migrants tries to enter US", news.getTitle());
        Assert.assertEquals("2014/03/17", news.getDate());
        Assert.assertEquals("http://www.thejakartapost.com/news/2014/03/17/another-group-60-migrants-tries-enter-us.html", news.getUrl());
        System.out.println(news);
    }


}
