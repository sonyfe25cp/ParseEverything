/**
 * 
 */
package com.parseeverything.html;

/**
 *解析雅加达邮报新闻
 * @author Mi Jing
 * @date 2014-3-17
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

public class JakartaPostnewsParser extends NewsPageParser{
	
    static Logger logger = LoggerFactory.getLogger(JakartaPostnewsParser.class);

    static Pattern datePattern = Pattern.compile("(\\d+\\/\\d+\\/\\d+)");

    @Override
    public boolean match(String url) {
        return url.contains("thejakartapost.com/news");
    }

    @Override
    public NewsModel parse(String url, String html) {
        Document doc = Jsoup.parse(html);
        Elements totalElement = doc.select(".story");
        String title = totalElement.select("h1").text();
        Elements contentElement = totalElement.select(".span-13");//后面的广告也加进去了，怎么除去？
        String content =contentElement.text();
        String contentHtml = contentElement.html();
        Matcher m = datePattern.matcher(url);
        String date = "";
        if (m.find()) {
            date = m.group(1);
        }
        NewsModel model = new NewsModel(url, NewsProvider.SinaNews);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contentHtml);
        model.setDate(date);
        model.setHtml(html);
        return model;
    }

}
