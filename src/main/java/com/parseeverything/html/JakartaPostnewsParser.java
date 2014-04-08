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
import org.jsoup.nodes.Element;
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
        Element contentElement = totalElement.select(".span-13").first();
        String content="";
        String contenthtml ="";
        for(Element part:contentElement.children()){
        	if(part.tagName().equals("p")){
        		content+=part.text();
        		contenthtml+="<p>"+part.html()+"</p>";
                  }
        }
		
        Matcher m = datePattern.matcher(url);
        String date = "";
        if (m.find()) {
            date = m.group(1);
        }
		String[] s1=date.split("/");
		date=s1[0]+"-"+s1[1]+"-"+s1[2];
        NewsModel model = new NewsModel(url, NewsProvider.JakartaPost);
        model.setTitle(title);
        model.setContent(content);
        model.setContentHtml(contenthtml);
        model.setPublishDate(date);
        model.setHtml(html);
        return model;
    }

}
