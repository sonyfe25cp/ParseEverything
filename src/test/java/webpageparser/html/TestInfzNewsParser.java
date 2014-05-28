package webpageparser.html;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import webpageparser.utils.Utils;

import com.parseeverything.html.InfzmNewsParser;
import com.parseeverything.result.NewsModel;

public class TestInfzNewsParser extends TestCase {

	@Test
	public void testParse() {

		InfzmNewsParser snp = new InfzmNewsParser();
		NewsModel news = snp.parse("http://www.infzm.com/content/100668",
				Utils.getResouce("infzm-news-001.html"));
		Assert.assertEquals("宜宾爆燃公交的最后半小时", news.getTitle());
		Assert.assertEquals("2014-05-15", news.getPublishDate());
		Assert.assertEquals("http://www.infzm.com/content/100668",
				news.getUrl());
		System.out.println(news);

	}

	@Test
	public void testParse2() {

		InfzmNewsParser snp = new InfzmNewsParser();
		NewsModel news = snp.parse("http://www.infzm.com/content/100675",
				Utils.getResouce("infzm-news-002.html"));
		Assert.assertEquals("“全球见证”创始人查米恩·古奇谈国际合作反腐", news.getTitle());
		Assert.assertEquals("2014-05-15", news.getPublishDate());
		Assert.assertEquals("http://www.infzm.com/content/100675",
				news.getUrl());
		System.out.println(news);

	}

}
