/**
 * 
 */
package webpageparser.html;

/**
 *
 * @author Mi Jing
 * @date 2014-3-15
 */

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webpageparser.utils.Utils;

import com.parseeverything.html.CommercialTimesParser;
import com.parseeverything.result.NewsModel;

public class TestCommercialParser extends TestCase{
	static Logger logger = LoggerFactory.getLogger(TestCommercialParser.class);
	
    @Test
    public void testParse() {
        CommercialTimesParser ctp = new CommercialTimesParser();
        NewsModel news = ctp.parse("http://www.nst.com.my/business/nation/maybank-bullish-on-oldtown-s-expansion-plans-1.513461?cache=03%252F7.212150%253Fpage%253D0%253Fpage%253D0%252F7.247626%252F7.266429%252F7.288059%252F7.288059%253Fpage%253D0",
                Utils.getResouce("Commercial-news-001.html"));
        Assert.assertEquals("Maybank bullish on Oldtown’s expansion plans", news.getTitle());
        Assert.assertEquals("15 March 2014", news.getDate());
        Assert.assertEquals("http://www.nst.com.my/business/nation/maybank-bullish-on-oldtown-s-expansion-plans-1.513461?cache=03%252F7.212150%253Fpage%253D0%253Fpage%253D0%252F7.247626%252F7.266429%252F7.288059%252F7.288059%253Fpage%253D0", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {
        CommercialTimesParser ctp = new CommercialTimesParser();
        NewsModel news = ctp.parse("http://www.nst.com.my/business/latest/sime-darby-acquires-70pc-in-tfp-engineering-1.512763?cache=03%252F7.212150%253Fpage%253D0%253Fpage%253D0%252F7.247626%252F7.266429%252F7.288059%252F7.288059%253Fpage%253D0",
                Utils.getResouce("Commercial-news-002.html"));
        Assert.assertEquals("Sime Darby acquires 70pc in TFP Engineering", news.getTitle());
        Assert.assertEquals("14 March 2014", news.getDate());
        Assert.assertEquals("http://www.nst.com.my/business/latest/sime-darby-acquires-70pc-in-tfp-engineering-1.512763?cache=03%252F7.212150%253Fpage%253D0%253Fpage%253D0%252F7.247626%252F7.266429%252F7.288059%252F7.288059%253Fpage%253D0", news.getUrl());
        System.out.println(news);
    }

}
