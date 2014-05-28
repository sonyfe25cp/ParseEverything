package com.parseeverything.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parseeverything.result.NewsModel;
import com.parseeverything.utils.NewsProvider;

public class InfzmNewsParser extends NewsPageParser {

    static Logger logger = LoggerFactory.getLogger(InfzmNewsParser.class);

    static Pattern datePattern = Pattern.compile("(\\d+-\\d+-\\d+)"); 
    
    @Override
    public boolean match(String url) {
        return url.contains("http://www.infzm.com/");
    }

    @Override
    public NewsModel parse(String url, String html) {
        Document doc = Jsoup.parse(html);
        String title = doc.select("h1").text();
        Elements contentElement = doc.select("#articleContent");
        String content = contentElement.text();
        String contentHtml = contentElement.html();
        String date = doc.select(".pubTime").text();
        Matcher m = datePattern.matcher(date);
        if(m.find()){
        	date = m.group(1);
        }
        NewsModel model = new NewsModel(url, NewsProvider.Infzm);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contentHtml);
        model.setPublishDate(date);
        model.setHtml(html);
        return model;
    }

}
