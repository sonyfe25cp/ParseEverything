/**
 * 
 */
package webpageparser.html;

/**
 *
 * @author Mi Jing
 * @date 2014-3-19
 */

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webpageparser.utils.Utils;

import com.parseeverything.html.AntaraNewsAgencyParser;
import com.parseeverything.result.NewsModel;

public class TestAntaraNewsAgencyParser extends TestCase{
	
	static Logger logger = LoggerFactory.getLogger(TestAntaraNewsAgencyParser.class);
	
    @Test
    public void testParse() {
    	AntaraNewsAgencyParser anap = new AntaraNewsAgencyParser();
        NewsModel news = anap.parse("http://www.antaranews.com/en/news/93254/japan-plans-to-send-home-remains-of-20-thousand-soldiers",
                Utils.getResouce("AntaraNewsAgency-news-001.html"));
        Assert.assertEquals("Japan plans to send home remains of 20 thousand soldiers", news.getTitle());
        Assert.assertEquals("2014-03-19", news.getPublishDate());
        Assert.assertEquals("http://www.antaranews.com/en/news/93254/japan-plans-to-send-home-remains-of-20-thousand-soldiers", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {
    	AntaraNewsAgencyParser anap = new AntaraNewsAgencyParser();
        NewsModel news = anap.parse("http://www.antaranews.com/en/news/93131/rupiah-strengthens-at-rp11372-on-monday-evening",
                Utils.getResouce("AntaraNewsAgency-news-002.html"));
        Assert.assertEquals("Rupiah strengthens at Rp11,372 on Monday evening", news.getTitle());
        Assert.assertEquals("2014-03-10", news.getPublishDate());
        Assert.assertEquals("http://www.antaranews.com/en/news/93131/rupiah-strengthens-at-rp11372-on-monday-evening", news.getUrl());
        System.out.println(news);
    }

}



