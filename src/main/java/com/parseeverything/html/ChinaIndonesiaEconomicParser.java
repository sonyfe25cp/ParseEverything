/**
 * 
 */
package com.parseeverything.html;

/**
 *解析中国---印尼经贸合作网新闻
 * @author Mi Jing
 * @date 2014-3-19
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parseeverything.result.NewsModel;
import com.parseeverything.utils.NewsProvider;

public class ChinaIndonesiaEconomicParser extends NewsPageParser{
	
    static Logger logger = LoggerFactory.getLogger(ChinaIndonesiaEconomicParser.class);

    static Pattern datePattern = Pattern.compile("(\\d+-\\d+-\\d+)");

    @Override
    public boolean match(String url) {
        return url.contains("cic.mofcom.gov.cn/ciweb/cic/info/Article.jsp?");
    }

    @Override
    public NewsModel parse(String url, String html) {
        Document doc = Jsoup.parse(html);
        String title = doc.select(".title").first().text();
        Elements contentElement = doc.select(".body");
        String content = contentElement.text();
        String contentHtml = contentElement.html();
        Element date1=doc.select("table[width=90%]").first();
        String datetotal=date1.text();
        Matcher m = datePattern.matcher(datetotal);
        String date = "";
        if (m.find()) {
            date = m.group(1);
        }
        NewsModel model = new NewsModel(url, NewsProvider.ChinaIndonesiaEconomic);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contentHtml);
        model.setDate(date);
        model.setHtml(html);
        return model;
    }

}
