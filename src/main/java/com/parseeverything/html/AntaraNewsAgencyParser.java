/**
 * 
 */
package com.parseeverything.html;

/**
 *解析印尼安塔拉通讯社新闻
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

public class AntaraNewsAgencyParser extends NewsPageParser{
	
    static Logger logger = LoggerFactory.getLogger(AntaraNewsAgencyParser.class);

    static Pattern datePattern = Pattern.compile("(\\w+\\,\\s*\\w+\\s*\\d{2}\\s*\\d{4})");

    @Override
    public boolean match(String url) {
        return url.contains("antaranews.com/en/news");
    }

    @Override
    public NewsModel parse(String url, String html) {
        Document doc = Jsoup.parse(html);
        String title = doc.select(".news-title").text();
        String datetotal=doc.select(".datetime").text();
        String content = "";
        String contentHtml ="";
        Elements contentElement = doc.select(".post-content");
        contentElement.select("#box_newsLeft").remove();
        contentElement.select("p").remove();
        contentElement.select("div").remove();
        contentElement.select("script").remove();
        content=contentElement.text();
        contentHtml=contentElement.html();
        Matcher m = datePattern.matcher(datetotal);
        String date = "";
        if (m.find()) {
            datetotal = m.group(1);
        }
        String[] s1=datetotal.split(" ");
        String year=s1[3];
        String month=s1[1];
        String day=s1[2];
        switch(month){
        case "January" :
    	    month="01";
    	    break;
        case "February" :
            month="02";
            break;
        case "March" :
            month="03";
            break;
        case "April" :
            month="04";
            break;
        case "May" :
            month="05";
            break;
        case "June" :
            month="06";
            break;
        case "July" :
            month="07";
            break;
        case "August" :
            month="08";
            break;
        case "September" :
            month="09";
            break;
        case "October" :
            month="10";
            break;
        case "November" :
            month="11";
            break;
        default:
            month="12";
            break;
    }
        date=year+"-"+month+"-"+day;
        NewsModel model = new NewsModel(url, NewsProvider.AntaraNewsAgency);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contentHtml);
        model.setDate(date);
        model.setHtml(html);
        return model;
    }
    

}
