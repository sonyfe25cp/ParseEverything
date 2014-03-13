package webpageparser.html;

import junit.framework.TestCase;

import org.junit.Test;

import com.parseeverything.html.SinaNewsParser;
import com.parseeverything.result.NewsModel;

public class TestSinaNewsParser extends TestCase {

    @Test
    public void testParse() {
        SinaNewsParser snp = new SinaNewsParser();
        NewsModel news = snp.parse("http://news.sina.com.cn/c/2014-03-07/114629648141.shtml",
                webpageparser.utils.Utils.getResouce("sina-news-001.html"));
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {
        SinaNewsParser snp = new SinaNewsParser();
        NewsModel news = snp.parse("http://news.sina.com.cn/c/2014-03-07/100729647177.shtml",
                webpageparser.utils.Utils.getResouce("sina-news-002.html"));
        System.out.println(news);
    }

}
