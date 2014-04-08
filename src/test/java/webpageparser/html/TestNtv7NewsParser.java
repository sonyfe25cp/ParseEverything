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

import com.parseeverything.html.Ntv7NewsParser;
import com.parseeverything.result.NewsModel;

public class TestNtv7NewsParser extends TestCase{
	
	static Logger logger=LoggerFactory.getLogger(TestNtv7NewsParser.class);
	
	//测试business news
	@Test
	public void testParse(){
		Ntv7NewsParser n7vp=new Ntv7NewsParser();
		NewsModel news=n7vp.parse("http://www.ntv7.com.my/7edition/business-en/LOCAL_EN_1393923740.html", 
				      Utils.getResouce("N7tvNewsParser-news-001.html"));
		Assert.assertEquals("SUKUK MURABAHAH : PRASARANA ISSUES RM2 BILLION SUKUK FOR CAPEX AND WORKING CAPITAL",news.getTitle());
		Assert.assertEquals("2014-03-04", news.getPublishDate());
		Assert.assertEquals("http://www.ntv7.com.my/7edition/business-en/LOCAL_EN_1393923740.html", news.getUrl());
		System.out.println(news);
	}
	
	//测试local news
	@Test
	public void testParse2(){
		Ntv7NewsParser n7vp=new Ntv7NewsParser();
		NewsModel news=n7vp.parse("http://www.ntv7.com.my/7edition/local-en/HAZY_DAYS_SCHOOLS_TO_REOPEN_TOMORROW.html", 
				      Utils.getResouce("N7tvNewsParser-news-002.html"));
		Assert.assertEquals("HAZY DAYS : SCHOOLS TO REOPEN TOMORROW",news.getTitle());
		Assert.assertEquals("2014-03-16", news.getPublishDate());
		Assert.assertEquals("http://www.ntv7.com.my/7edition/local-en/HAZY_DAYS_SCHOOLS_TO_REOPEN_TOMORROW.html", news.getUrl());
		System.out.println(news);
	}
	
	//测试international news
	@Test
	public void testParse3(){
		Ntv7NewsParser n7vp=new Ntv7NewsParser();
		NewsModel news=n7vp.parse("http://www.ntv7.com.my/7edition/international-en/TRAGIC_RECRUITMENT_DRIVE_SEVERAL_KILLED_IN_STAMPEDE_AT_RECRUITMENT_EVENT_IN_NATIONAL_STADIUM.html", 
				      Utils.getResouce("N7tvNewsParser-news-003.html"));
		Assert.assertEquals("TRAGIC RECRUITMENT DRIVE : SEVERAL KILLED IN STAMPEDE AT RECRUITMENT EVENT IN NATIONAL STADIUM",news.getTitle());
		Assert.assertEquals("2014-03-16", news.getPublishDate());
		Assert.assertEquals("http://www.ntv7.com.my/7edition/international-en/TRAGIC_RECRUITMENT_DRIVE_SEVERAL_KILLED_IN_STAMPEDE_AT_RECRUITMENT_EVENT_IN_NATIONAL_STADIUM.html", news.getUrl());
		System.out.println(news);
	}

}
