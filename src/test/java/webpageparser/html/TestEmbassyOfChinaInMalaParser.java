/**
 * 
 */
package webpageparser.html;

/**
 *
 * @author Mi Jing
 * @date 2014-3-16
 */

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webpageparser.utils.Utils;

import com.parseeverything.html.EmbassyOfChinaInMalaParser;
import com.parseeverything.result.NewsModel;

public class TestEmbassyOfChinaInMalaParser extends TestCase{
	
	static Logger logger=LoggerFactory.getLogger(TestEmbassyOfChinaInMalaParser.class);
	
	//测试经贸往来新闻
	@Test
	public void testParse(){
		EmbassyOfChinaInMalaParser ecmp=new EmbassyOfChinaInMalaParser();
		NewsModel news=ecmp.parse("http://my.china-embassy.org/chn/jmwls/t1017273.htm", 
                Utils.getResouce("EmbassyOfChinaInMala-news-001.html"));
		Assert.assertEquals("柴玺大使会见商务部新闻发言人沈丹阳一行", news.getTitle());
		Assert.assertEquals("2013/02/28", news.getDate());
		Assert.assertEquals("http://my.china-embassy.org/chn/jmwls/t1017273.htm", news.getUrl());
		System.out.println(news);
	}
	
	//测试使馆新闻
	@Test
	public void testParse2(){
		EmbassyOfChinaInMalaParser ecmp=new EmbassyOfChinaInMalaParser();
		NewsModel news=ecmp.parse("http://my.china-embassy.org/chn/sgxw/t1135428.htm", 
                Utils.getResouce("EmbassyOfChinaInMala-news-002.html"));
		Assert.assertEquals("黄惠康大使举办“使馆应急小组在行动”媒体吹风会", news.getTitle());
		Assert.assertEquals("2014/03/09", news.getDate());
		Assert.assertEquals("http://my.china-embassy.org/chn/sgxw/t1135428.htm", news.getUrl());
		System.out.println(news);
	}

}
