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

import com.parseeverything.html.ChinaIndonesiaEconomicParser;
import com.parseeverything.result.NewsModel;

public class TestChinaIndonesiaEconomicParser extends TestCase{
	
	static Logger logger = LoggerFactory.getLogger(TestChinaIndonesiaEconomicParser.class);
	
    @Test
    public void testParse() {
    	ChinaIndonesiaEconomicParser ciep = new ChinaIndonesiaEconomicParser();
        NewsModel news = ciep.parse("http://www.cic.mofcom.gov.cn/ciweb/cic/info/Article.jsp?a_no=344905&col_no=459",
                Utils.getResouce("ChinaIndonesiaEconomic.news-001.html"));
        Assert.assertEquals("镇宁木艺彩娃畅销东南亚", news.getTitle());
        Assert.assertEquals("2014-03-10", news.getPublishDate());
        Assert.assertEquals("http://www.cic.mofcom.gov.cn/ciweb/cic/info/Article.jsp?a_no=344905&col_no=459", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {
    	ChinaIndonesiaEconomicParser ciep = new ChinaIndonesiaEconomicParser();
        NewsModel news = ciep.parse("http://www.cic.mofcom.gov.cn/ciweb/cic/info/Article.jsp?a_no=345215&col_no=461",
                Utils.getResouce("ChinaIndonesiaEconomic.news-002.html"));
        Assert.assertEquals("统计局公布一月份武器进口清单", news.getTitle());
        Assert.assertEquals("2014-03-13", news.getPublishDate());
        Assert.assertEquals("http://www.cic.mofcom.gov.cn/ciweb/cic/info/Article.jsp?a_no=345215&col_no=461", news.getUrl());
        System.out.println(news);
    }


}
