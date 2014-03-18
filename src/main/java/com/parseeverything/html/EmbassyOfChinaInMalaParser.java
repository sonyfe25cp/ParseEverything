/**
 * 
 */
package com.parseeverything.html;

/**
 *解析中华人民共和国驻马来西亚大使馆新闻
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

public class EmbassyOfChinaInMalaParser extends NewsPageParser{
	
	static Logger logger=LoggerFactory.getLogger(EmbassyOfChinaInMalaParser.class);
	
	static Pattern datePattern=Pattern.compile("(\\d{4}\\/\\d{1,2}\\/\\d{1,2})");
	
	@Override
	public boolean match(String url){
		return url.equals("my.china-embassy.org/chn");
	}
	
	@Override
	public NewsModel parse(String url,String html){
		Document doc=Jsoup.parse(html);
		String title=doc.select(".bigtitle").text();
		Elements contentelement=doc.select("#content");
		String content=contentelement.text();
		String contenthtml=contentelement.html();
		String datetotal=doc.select(".time").text();
		String date="";
		Matcher m=datePattern.matcher(datetotal);
		if(m.find()){
			date=m.group(1);
		}
		String[] s1=date.split("/");
		date=s1[0]+"-"+s1[1]+"-"+s1[2];
		NewsModel model=new NewsModel(url,NewsProvider.EmbassyOfChinaInMala);
		model.setTitle(title);
		model.setContent(content);
		model.setContentHtml(contenthtml);
		model.setDate(date);
		model.setHtml(html);
		return model;
		
	}

}
