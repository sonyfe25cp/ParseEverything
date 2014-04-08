/**
 * 
 */
package com.parseeverything.html;

/**
 *解析印尼驻华使馆新闻
 * @author Mi Jing
 * @date 2014-3-19
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

public class IndonesianembassyChinaParser extends NewsPageParser{
	
    static Logger logger = LoggerFactory.getLogger(IndonesianembassyChinaParser.class);

    static Pattern datePattern = Pattern.compile("(\\d{4}\\/\\d{2}\\/\\d{2})");

    @Override
    public boolean match(String url) {
        return url.contains("http://indonesianembassy-china.org/");
    }

    @Override
    public NewsModel parse(String url, String html) {
        Document doc = Jsoup.parse(html);
        String title = doc.select("#content").select("h2").text();
        Elements contentElement = doc.select(".entry");
        String content = contentElement.text();
        String contentHtml = contentElement.html();
        Matcher m = datePattern.matcher(url);
        String date = "";
        if (m.find()) {
            date = m.group(1);
        }
        String[] s1=date.split("/");
        date=s1[0]+"-"+s1[1]+"-"+s1[2];
        NewsModel model = new NewsModel(url, NewsProvider.SinaNews);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contentHtml);
        model.setPublishDate(date);
        model.setHtml(html);
        return model;
    }


}
