/**
 * 
 */
package com.parseeverything.html;

/**
 *解析星报新闻
 * @author Mi Jing
 * @date 2014-3-18
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parseeverything.result.NewsModel;
import com.parseeverything.utils.NewsProvider;

public class TheStarNewsParser extends NewsPageParser{

	
    static Logger logger = LoggerFactory.getLogger(TheStarNewsParser.class);

    static Pattern datePattern = Pattern.compile("(\\d+\\/\\d+\\/\\d+)");

    @Override
    public boolean match(String url) {
        return url.contains("thestar.com.my");
    }

    @Override
    public NewsModel parse(String url, String html) {
        Document doc = Jsoup.parse(html);
        String title = doc.select(".headline").text();
        Elements contentElement = doc.select(".story");
        String content = contentElement.text();
        String contentHtml = contentElement.html();
        Matcher m = datePattern.matcher(url);
        String date = "";
        if (m.find()) {
            date = m.group(1);
        }
		String year=date.substring(0,4);
		String month=date.substring(5,7);
		String day=date.substring(8,10);
		date=year+"-"+month+"-"+day;
        NewsModel model = new NewsModel(url, NewsProvider.TheStarNews);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contentHtml);
        model.setPublishDate(date);
        model.setHtml(html);
        return model;
    }

}
