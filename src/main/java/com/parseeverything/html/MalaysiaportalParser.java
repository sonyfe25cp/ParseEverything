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
    	return url.equals("malaysia-today.net");
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
    	NewsModel model=new NewsModel(url,NewsProvider.Malaysiaportal);
    	model.setTitle(title);
    	model.setContent(content);
    	model.setContentHtml(contenthtml);
    	model.setDate(date);
    	model.setHtml(html);
    	return model;
    	
    }

}
