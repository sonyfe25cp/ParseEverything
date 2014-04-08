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

import com.parseeverything.html.NewStraitsTimesParser;
import com.parseeverything.result.NewsModel;

public class TestNewStraitsTimesParser extends TestCase{
	
	static Logger logger = LoggerFactory.getLogger(TestNewStraitsTimesParser.class);
	
    @Test
    public void testParse() {
    	NewStraitsTimesParser nstp = new NewStraitsTimesParser();
        NewsModel news = nstp.parse("http://www.nst.com.my/world/eu-ready-to-resume-business-with-iran-on-jan-20-1.519640",
                Utils.getResouce("NewStraitsTimes-news-001.html"));
        System.out.println(news.getPublishDate());
        System.out.println(news.getTitle());
        Assert.assertEquals("EU ready to resume business with Iran on Jan 20", news.getTitle());
        Assert.assertEquals("2014-03-18", news.getPublishDate());
        Assert.assertEquals("http://www.nst.com.my/world/eu-ready-to-resume-business-with-iran-on-jan-20-1.519640", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {
    	NewStraitsTimesParser nstp = new NewStraitsTimesParser();
        NewsModel news = nstp.parse("http://www.nst.com.my/business/latest/oil-prices-mixed-in-asia-1.519190",
                Utils.getResouce("NewStraitsTimes-news-002.html"));
        Assert.assertEquals("Oil prices mixed in Asia", news.getTitle());
        Assert.assertEquals("2014-03-18", news.getPublishDate());
        Assert.assertEquals("http://www.nst.com.my/business/latest/oil-prices-mixed-in-asia-1.519190", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse3() {
    	NewStraitsTimesParser nstp = new NewStraitsTimesParser();
        NewsModel news = nstp.parse("http://www.nst.com.my/nation/general/i-m-a-stronger-underdog-1.518461",
                Utils.getResouce("NewStraitsTimes-news-003.html"));
        Assert.assertEquals("'I'm a stronger underdog'", news.getTitle());
        Assert.assertEquals("2014-03-18", news.getPublishDate());
        Assert.assertEquals("http://www.nst.com.my/nation/general/i-m-a-stronger-underdog-1.518461", news.getUrl());
        System.out.println(news);
    }

}
