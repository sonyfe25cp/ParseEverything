package webpageparser.html;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webpageparser.utils.Utils;

import com.parseeverything.html.SinaNewsParser;
import com.parseeverything.result.NewsModel;

public class TestSinaNewsParser extends TestCase {

	static Logger logger = LoggerFactory.getLogger(TestSinaNewsParser.class);
	
    @Test
    public void testParse() {
        SinaNewsParser snp = new SinaNewsParser();
        NewsModel news = snp.parse("http://news.sina.com.cn/c/2014-03-07/114629648141.shtml",
                Utils.getResouce("sina-news-001.html"));
        Assert.assertEquals("李阳疯狂英语长沙校区关张 学生学费没退款", news.getTitle());
        Assert.assertEquals("2014-03-07", news.getDate());
        Assert.assertEquals("http://news.sina.com.cn/c/2014-03-07/114629648141.shtml", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {
        SinaNewsParser snp = new SinaNewsParser();
        NewsModel news = snp.parse("http://news.sina.com.cn/c/2014-03-07/100729647177.shtml",
                Utils.getResouce("sina-news-002.html"));
        Assert.assertEquals("王岐山两会赞陈道明:这些腕儿甘于清贫", news.getTitle());
        Assert.assertEquals("2014-03-07", news.getDate());
        Assert.assertEquals("http://news.sina.com.cn/c/2014-03-07/100729647177.shtml", news.getUrl());
        System.out.println(news);
    }

}
