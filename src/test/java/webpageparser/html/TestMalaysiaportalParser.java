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

import com.parseeverything.html.MalaysiaportalParser;
import com.parseeverything.result.NewsModel;

public class TestMalaysiaportalParser extends TestCase{
	
	static Logger logger=LoggerFactory.getLogger(TestMalaysiaportalParser.class);
	
	@Test
	public void testParse(){
		MalaysiaportalParser mpp=new MalaysiaportalParser();
		NewsModel news=mpp.parse("http://www.malaysia-today.net/najib-mh370-was-deliberately-turned-back/",
				Utils.getResouce("Malaysiaprotal-news-001.html"));
		Assert.assertEquals("NAJIB: MH370 WAS DELIBERATELY TURNED BACK", news.getTitle());
		Assert.assertEquals("2014-03-15",news.getPublishDate());
		Assert.assertEquals("http://www.malaysia-today.net/najib-mh370-was-deliberately-turned-back/",news.getUrl());
		System.out.println(news);
	}
	
	@Test
	public void testParse2(){
		MalaysiaportalParser mpp=new MalaysiaportalParser();
		NewsModel news=mpp.parse("http://www.malaysia-today.net/in-search-for-mh370-why-did-china-take-so-long-to-release-satellite-photos/",
				Utils.getResouce("Malaysiaprotal-news-002.html"));
		Assert.assertEquals("IN SEARCH FOR MH370, WHY DID CHINA TAKE SO LONG TO RELEASE SATELLITE PHOTOS?", news.getTitle());
		Assert.assertEquals("2014-03-13",news.getPublishDate());
		Assert.assertEquals("http://www.malaysia-today.net/in-search-for-mh370-why-did-china-take-so-long-to-release-satellite-photos/",news.getUrl());
		System.out.println(news);
	}


}
