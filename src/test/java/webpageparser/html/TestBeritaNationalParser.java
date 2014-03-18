/**
 * 
 */
package webpageparser.html;

/**
 *
 * @author Mi Jing
 * @date 2014-3-13
 */

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webpageparser.utils.Utils;

import com.parseeverything.html.BeritaNationalMalaysia;
import com.parseeverything.result.NewsModel;


public class TestBeritaNationalParser extends TestCase {
	static Logger logger = LoggerFactory.getLogger(TestBeritaNationalParser.class);
	
    @Test
    public void testParse() {
        BeritaNationalMalaysia BNM = new BeritaNationalMalaysia();
        NewsModel news = BNM.parse("http://www.bernama.com/bernama/v7/bu/newsbusiness.php?id=1021635",
                Utils.getResouce("BeritaNationalMalysia-001.html"));
        Assert.assertEquals("Melaka Biotech, Prathista To Undertake RM40 Million Joint-Venture Research Project", news.getTitle());
        Assert.assertEquals("http://www.bernama.com/bernama/v7/bu/newsbusiness.php?id=1021635", news.getUrl());
        System.out.println(news);
    }
    
    @Test
    public void testParse2() {
        BeritaNationalMalaysia BNM = new BeritaNationalMalaysia();
        NewsModel news = BNM.parse("http://www.bernama.com/bernama/v7/bu/newsbusiness.php?id=1021940",
                Utils.getResouce("BeritaNationalMalysia-002.html"));
        Assert.assertEquals("Sime Darby Acquires 70 Per Cent Stake In TFP Engineering For RM9.5 Million", news.getTitle());
        Assert.assertEquals("http://www.bernama.com/bernama/v7/bu/newsbusiness.php?id=1021940", news.getUrl());
        System.out.println(news);
    }

}
