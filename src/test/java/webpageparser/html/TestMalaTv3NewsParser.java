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

import com.parseeverything.html.MalaTv3NewsParser;
import com.parseeverything.result.NewsModel;

public class TestMalaTv3NewsParser extends TestCase{
	
	static Logger logger=LoggerFactory.getLogger(TestMalaTv3NewsParser.class);
	
	//测试press_release
	@Test
	public void testParse(){
		MalaTv3NewsParser m3np=new MalaTv3NewsParser();
		NewsModel news=m3np.parse("http://www.tv3.com.my/happenings/press_release/Siaran_Media_Jelajah_AJL28.html",
				  Utils.getResouce("MalaTv3News-001.html"));
		System.out.println(news.getTitle()+"hello");
		Assert.assertEquals("Siaran Media: Jelajah AJL28", news.getTitle());
		Assert.assertEquals("2014-01-13", news.getDate());
		Assert.assertEquals("http://www.tv3.com.my/happenings/press_release/Siaran_Media_Jelajah_AJL28.html", news.getUrl());
		System.out.println(news);
	}
	
	//测试events
	@Test
	public void testParse2(){
		MalaTv3NewsParser m3np=new MalaTv3NewsParser();
		NewsModel news=m3np.parse("http://www.tv3.com.my/happenings/events/Perubahan_Jadual_TV3_Tahun_2014.html",
				  Utils.getResouce("MalaTv3News-002.html"));
		System.out.println(news.getTitle()+"hello");
		Assert.assertEquals("Perubahan Jadual TV3 Tahun 2014", news.getTitle());
		Assert.assertEquals("2014-01-02", news.getDate());
		Assert.assertEquals("http://www.tv3.com.my/happenings/events/Perubahan_Jadual_TV3_Tahun_2014.html", news.getUrl());
		System.out.println(news);
	}

}
