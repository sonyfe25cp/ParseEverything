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

import com.parseeverything.html.IndonesiaShangBaoParser;
import com.parseeverything.result.NewsModel;

public class TestIndonesiaShangBaoParser extends TestCase{
	
	static Logger logger = LoggerFactory.getLogger(TestIndonesiaShangBaoParser.class);
	
    @Test
    public void testParse() {
    	IndonesiaShangBaoParser isbp = new IndonesiaShangBaoParser();
        NewsModel news = isbp.parse("http://www.shangbaoindonesia.com/berita-internasional/keuangan-internasional/%E7%BE%8E%E5%AA%92%EF%BC%9A%E4%B8%AD%E5%9B%BD%E7%BB%8F%E6%B5%8E%E6%94%BE%E7%BC%93%E6%98%AF%E7%BE%8E%E5%9B%BD%E5%A4%8D%E8%8B%8F%E6%9C%80%E5%A4%A7%E5%A8%81%E8%83%81.html",
                Utils.getResouce("IndonesiaShangbao-news-001.html"));
        System.out.println(news.getPublishDate());
        Assert.assertEquals("美媒：中国经济放缓是美国复苏最大威胁", news.getTitle());
       // Assert.assertEquals("2014-03-16", news.getDate());
        Assert.assertEquals("http://www.shangbaoindonesia.com/berita-internasional/keuangan-internasional/%E7%BE%8E%E5%AA%92%EF%BC%9A%E4%B8%AD%E5%9B%BD%E7%BB%8F%E6%B5%8E%E6%94%BE%E7%BC%93%E6%98%AF%E7%BE%8E%E5%9B%BD%E5%A4%8D%E8%8B%8F%E6%9C%80%E5%A4%A7%E5%A8%81%E8%83%81.html", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {   //问题：网址中的汉字成了乱码
       	IndonesiaShangBaoParser isbp = new IndonesiaShangBaoParser();
        NewsModel news = isbp.parse("http://www.shangbaoindonesia.com/indonesia-finance/%E7%AE%A1%E7%90%86%E5%88%B6%E5%BA%A6%E8%89%AF%E5%A5%BD-bri%E6%88%90%E5%85%A8%E7%90%83%E6%9C%80%E7%9B%88%E5%88%A9%E9%93%B6%E8%A1%8C%E4%B9%8B%E4%B8%80.html",
                Utils.getResouce("IndonesiaShangbao-news-002.html"));
        System.out.println(news.getPublishDate());
        Assert.assertEquals("管理制度良好 BRI成全球最盈利银行之一", news.getTitle());
        //Assert.assertEquals("2014-03-17", news.getDate());
        Assert.assertEquals("http://www.shangbaoindonesia.com/indonesia-finance/%E7%AE%A1%E7%90%86%E5%88%B6%E5%BA%A6%E8%89%AF%E5%A5%BD-bri%E6%88%90%E5%85%A8%E7%90%83%E6%9C%80%E7%9B%88%E5%88%A9%E9%93%B6%E8%A1%8C%E4%B9%8B%E4%B8%80.html", news.getUrl());
        System.out.println(news);
    }


}
