/**
 * 
 */
package webpageparser.html;

/**
*
* @author Mi Jing
* @date 2014-3-18
*/

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webpageparser.utils.Utils;

import com.parseeverything.html.IndonesialJournalParser;
import com.parseeverything.result.NewsModel;

import junit.framework.TestCase;


public class TestIndonesialJournalParser extends TestCase{
	
	static Logger logger=LoggerFactory.getLogger(TestIndonesialJournalParser.class);
	
	@Test
	public void testParse(){
		IndonesialJournalParser ijp=new IndonesialJournalParser();
		NewsModel news=ijp.parse("http://www.guojiribao.com/shtml/gjrb/20140318/152445.shtml",
				  Utils.getResouce("IndonesialJournal-news-001.html"));
		System.out.println(news.getDate());
		Assert.assertEquals("苏西洛在万杜尔参加民主党竞选活动",news.getTitle());
		Assert.assertEquals("2014-03-18",news.getDate());
		Assert.assertEquals("http://www.guojiribao.com/shtml/gjrb/20140318/152445.shtml", news.getUrl());
		System.out.println(news);
	}

	@Test
	public void testParse2(){
		IndonesialJournalParser ijp=new IndonesialJournalParser();
		NewsModel news=ijp.parse("http://www.guojiribao.com/shtml/gjrb/20140318/152452.shtml",
				  Utils.getResouce("IndonesialJournal-news-002.html"));
		System.out.println(news.getDate());
		Assert.assertEquals("工业部设法调降机械进口量",news.getTitle());
		Assert.assertEquals("2014-03-18",news.getDate());
		Assert.assertEquals("http://www.guojiribao.com/shtml/gjrb/20140318/152452.shtml", news.getUrl());
		System.out.println(news);
	}
	
	@Test
	public void testParse3(){
		IndonesialJournalParser ijp=new IndonesialJournalParser();
		NewsModel news=ijp.parse("http://www.guojiribao.com/shtml/gjrb/20140318/152449.shtml",
				  Utils.getResouce("IndonesialJournal-news-003.html"));
		System.out.println(news.getDate());
		Assert.assertEquals("国会:须招商兴建跨苏岛高速公路",news.getTitle());
		Assert.assertEquals("2014-03-18",news.getDate());
		Assert.assertEquals("http://www.guojiribao.com/shtml/gjrb/20140318/152449.shtml", news.getUrl());
		System.out.println(news);
	}
}
