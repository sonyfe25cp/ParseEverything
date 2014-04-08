/**
 * 
 */
package com.parseeverything.html;

/**
 *解析商业时报business news
 * @author Mi Jing
 * @date 2014-3-15
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

public class CommercialTimesParser  extends NewsPageParser{
	
    static Pattern datePattern = Pattern.compile("(\\d+\\s*\\w+\\s*\\d{4})");
    static Logger logger = LoggerFactory.getLogger(CommercialTimesParser.class);

    @Override
    public boolean match(String url){
    	return url.contains("nst.com.my/business");
    }
    @Override
    public NewsModel parse(String url, String html){
        Document doc = Jsoup.parse(html);
        Elements header=doc.select(".article-header");
        String title = header.select("h1").text();
        Elements contentElement = doc.select(".news-article");
        String content = contentElement.text();
        String contentHtml = contentElement.html();
        String datetotal =doc.select(".article-date").text();
        Matcher m=datePattern.matcher(datetotal);
        String date = "";
        if (m.find()) {
            date = m.group(1);
        }
        else
        	System.out.println("cannot find the date");
        String[] s1=date.split(" ");
        String month=s1[1];
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
        date=s1[2]+"-"+month+"-"+s1[0];
        

        NewsModel model = new NewsModel(url, NewsProvider.CommercialTimes);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contentHtml);
        model.setPublishDate(date);
        model.setHtml(html);
        return model;
    }

}
