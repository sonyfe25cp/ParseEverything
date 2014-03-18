/**
 * 
 */
package com.parseeverything.html;

/**
 *解析国民电视新闻
 * @author Mi Jing
 * @date 2014-3-16
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

public class Ntv7NewsParser extends NewsPageParser{
	
	static Logger logger=LoggerFactory.getLogger(Ntv7NewsParser.class);
	
	static Pattern datePattern=Pattern.compile("(\\d{1,2}\\s*\\w+\\s*\\d{4})");
	
	@Override
	public boolean match(String url){
		return url.equals("ntv7.com.my/7edition");
	}
	
	@Override
	public NewsModel parse(String url,String html){
		Document doc=Jsoup.parse(html);
		String title=doc.select(".storyheadline").text();
		Elements contentelement=doc.select(".article-story");
		String content=contentelement.text();
		String contenthtml=contentelement.html();
		String datetotal=doc.select(".post-details").text();
		String date="";
		Matcher m=datePattern.matcher(datetotal);
		if(m.find()){
			date=m.group(1);
		}
        String[] s1=date.split(" ");
        String month=s1[1];
        switch(month){
            case "Jan" :
        	    month="01";
        	    break;
            case "Feb" :
                month="02";
                break;
            case "Mar" :
                month="03";
                break;
            case "Apr" :
                month="04";
                break;
            case "May" :
                month="05";
                break;
            case "Jun" :
                month="06";
                break;
            case "Jul" :
                month="07";
                break;
            case "Aug" :
                month="08";
                break;
            case "Sep" :
                month="09";
                break;
            case "Oct" :
                month="10";
                break;
            case "Nov" :
                month="11";
                break;
            default:
                month="12";
                break;
        }
        if(s1[0].length()<2) 
        {
        	String day=s1[0];
        	s1[0]="0"+day;
        }
        date=s1[2]+"-"+month+"-"+s1[0];
		NewsModel model=new NewsModel(url,NewsProvider.Ntv7);
		model.setTitle(title);
		model.setContent(content);
		model.setContentHtml(contenthtml);
		model.setDate(date);
		model.setHtml(html);
		return model;
	}

}
