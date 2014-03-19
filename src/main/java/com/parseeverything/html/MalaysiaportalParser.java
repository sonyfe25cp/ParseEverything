/**
 * 
 */
package com.parseeverything.html;

/**
 *解析马来西亚新闻门户
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

public class MalaysiaportalParser extends NewsPageParser{
	
	static Logger logger=LoggerFactory.getLogger(MalaysiaportalParser.class);
	
	static Pattern datePattern=Pattern.compile("(\\w+\\s*\\d{1,2}\\,\\s*\\d{4})");
	
	@Override
    public boolean match(String url){
    	return url.contains("malaysia-today.net");
    }
    
	@Override
    public NewsModel parse(String url, String html){
    	Document doc=Jsoup.parse(html);
    	String title=doc.select(".content-article-title").text().toUpperCase();
    	Elements ContentElement=doc.select(".shortcode-content");
    	String content=ContentElement.text();
    	String contenthtml=ContentElement.html();
    	String datetotal=doc.select(".date").text();
    	Matcher m=datePattern.matcher(datetotal);
    	String date="";
    	if(m.find()){
    		date=m.group(1);
    	
    	}
        String[] s1=date.split(" ");
        String month=s1[0];
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
        String day=s1[1].substring(0, 2);
        date=s1[2]+"-"+month+"-"+day;
    	NewsModel model=new NewsModel(url,NewsProvider.Malaysiaportal);
    	model.setTitle(title);
    	model.setContent(content);
    	model.setContentHtml(contenthtml);
    	model.setDate(date);
    	model.setHtml(html);
    	return model;
    	
    }

}
