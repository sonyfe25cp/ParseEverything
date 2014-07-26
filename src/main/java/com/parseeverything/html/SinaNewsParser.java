package com.parseeverything.html;

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

public class SinaNewsParser extends NewsPageParser {

    static Logger logger = LoggerFactory.getLogger(SinaNewsParser.class);

    static Pattern datePattern = Pattern.compile("(\\d+-\\d+-\\d+)");

    @Override
    public boolean match(String url) {
        return url.contains("sina.com.cn");
    }

    @Override
    public NewsModel parse(String url, String html) {
        Document doc = Jsoup.parse(html);
        String title = doc.select("#artibodyTitle").text();
        Elements contentElement = doc.select("#artibody");
        String content = "";
        String contentHtml ="";
        if(contentElement != null){
            Element first = contentElement.first();
            first.select(".otherContent_01").remove();
            content = first.text();
            contentHtml = first.html();
        }
        Matcher m = datePattern.matcher(url);
        String date = "";
        if (m.find()) {
            date = m.group(1);
        }
        if(date.length() == 0){
            Elements dateEle = doc.select("#pub_date");
            if(dateEle!=null){
                String text = dateEle.text();
                if(text.contains("日")){
                    text = text.substring(0, text.indexOf("日"));
                    System.out.println(text);
                    date = text.replaceAll("[年月]", "-");
                }
            }
        }
        NewsModel model = new NewsModel(url, NewsProvider.SinaNews);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contentHtml);
        model.setPublishDate(date);
        model.setHtml(html);
        return model;
    }

}
