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

import com.parseeverything.html.IndonesianembassyChinaParser;
import com.parseeverything.result.NewsModel;

public class TestIndonesianembassyChinaParser extends TestCase{
	
	static Logger logger = LoggerFactory.getLogger(TestIndonesianembassyChinaParser.class);
	
    @Test
    public void testParse() {
    	IndonesianembassyChinaParser icp = new IndonesianembassyChinaParser();
        NewsModel news = icp.parse("http://indonesianembassy-china.org/2012/09/14/finding-a-secure-instant-online-loan/",
                Utils.getResouce("IndonesianembassyChina-news-001.html"));
        Assert.assertEquals("Finding A Secure Instant Online Loan", news.getTitle());
        Assert.assertEquals("2012-09-14", news.getDate());
        Assert.assertEquals("http://indonesianembassy-china.org/2012/09/14/finding-a-secure-instant-online-loan/", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {
    	IndonesianembassyChinaParser icp = new IndonesianembassyChinaParser();
        NewsModel news = icp.parse("http://indonesianembassy-china.org/2012/09/14/finding-a-cash-advance-online/",
                Utils.getResouce("IndonesianembassyChina-news-002.html"));
        Assert.assertEquals("Finding A Cash Advance Online", news.getTitle());
        Assert.assertEquals("2012-09-14", news.getDate());
        Assert.assertEquals("http://indonesianembassy-china.org/2012/09/14/finding-a-cash-advance-online/", news.getUrl());
        System.out.println(news);
    }


}
