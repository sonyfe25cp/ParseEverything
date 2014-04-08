/**
 * 
 */
package com.parseeverything.html;

/**
 *解析新海峡时报
 * @author Mi Jing
 * @date 2014-3-18
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

public class NewStraitsTimesParser extends NewsPageParser{
	
    static Logger logger = LoggerFactory.getLogger(NewStraitsTimesParser.class);

    static Pattern datePattern = Pattern.compile("(\\d{1,2}\\s*\\w+\\s*\\d{4})");

    @Override
    public boolean match(String url) {
        return url.contains("nst.com.my");
    }

    @Override
    public NewsModel parse(String url, String html) {
        Document doc = Jsoup.parse(html);
        Elements titleElemt=doc.select(".article-header");
        String title = titleElemt.select("h1").text();
        String datetotal=doc.select(".article-date").select("span").text();
        Element contentElement = doc.select(".news-article").first();
        String content="";
        String contenthtml = "";
        for(Element part:contentElement.children()){
        	if(part.tagName().equals("h2")){
        		content+=part.text();
        		contenthtml+="<h2>"+part.html()+"</h2>";
        	}
        	else if(part.tagName().equals("p")){
        		content+=part.text();
        		contenthtml+="<p>"+part.html()+"</p>";
        	}
        	
        }
        Matcher m = datePattern.matcher(datetotal);
        String date = "";
        if (m.find()) {
            date = m.group(1);
        }
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
        NewsModel model = new NewsModel(url, NewsProvider.NewStraitsTimes);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contenthtml);
        model.setPublishDate(date);
        model.setHtml(html);
        return model;
    }


}
