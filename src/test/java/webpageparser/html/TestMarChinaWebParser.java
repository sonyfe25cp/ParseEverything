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

import com.parseeverything.html.MarChinaWebParser;
import com.parseeverything.result.NewsModel;

public class TestMarChinaWebParser extends TestCase{
	
	static Logger logger=LoggerFactory.getLogger(TestMarChinaWebParser.class);
	//测试财经新闻
	@Test
	public void testparse(){
		MarChinaWebParser mcwp=new MarChinaWebParser();
		NewsModel news=mcwp.parse("http://mandarin.bernama.com/v3/index.php?sid=news&cat_news=ge&id=104068",
				Utils.getResouce("MarChinaWeb-news-001.html"));
		Assert.assertEquals("大马去年首11月总贸易额达1.247兆", news.getTitle());
		Assert.assertEquals("http://mandarin.bernama.com/v3/index.php?sid=news&cat_news=ge&id=104068", news.getUrl());
		System.out.println(news);
	}
	
	@Test
	public void testparse2(){
		MarChinaWebParser mcwp=new MarChinaWebParser();
		NewsModel news=mcwp.parse("http://mandarin.bernama.com/v3/index.php?sid=news&cat_news=ge&id=104041",
				Utils.getResouce("MarChinaWeb-news-002.html"));
		Assert.assertEquals("城市雇员不满意晋升机会和薪金", news.getTitle());
		Assert.assertEquals("http://mandarin.bernama.com/v3/index.php?sid=news&cat_news=ge&id=104041", news.getUrl());
		System.out.println(news);
	}
	
	//测试头条新闻
	@Test
	public void testparse3(){
		MarChinaWebParser mcwp=new MarChinaWebParser();
		NewsModel news=mcwp.parse("http://mandarin.bernama.com/v3/index.php?sid=news&id=106365&cat_news=ts",
				Utils.getResouce("MarChinaWeb-news-003.html"));
		System.out.println(news.getTitle());
		Assert.assertEquals("警方查接触失联客机工程师 希山：寻求15国家援助搜索", news.getTitle());
		Assert.assertEquals("http://mandarin.bernama.com/v3/index.php?sid=news&id=106365&cat_news=ts", news.getUrl());
		System.out.println(news);
	}

}
