/**
 * 
 */
package com.parseeverything.html;

/**
 *解析马来西亚第三电台happenings
 * @author Mi Jing
 * @date 2014-3-16
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

public class MalaTv3NewsParser extends NewsPageParser{
	
	static Logger logger=LoggerFactory.getLogger(MalaTv3NewsParser.class);
	
	static Pattern datePattern=Pattern.compile("(\\d{1,2}\\/\\d{1,2}\\/\\d{4})");
	
	@Override
	public boolean match(String url){
		return url.equals("tv3.com.my/happenings");
	}
	
	@Override
	public NewsModel parse(String url,String html){
		Document doc=Jsoup.parse(html);
		Elements contentelement=doc.select(".holder");
		Elements contenth2=contentelement.select("h2");
		String title="";
		if(contenth2.size()>1){
			Element contentfirst=contenth2.first();
			title+=contentfirst.text();
		}
		else
			title+=contenth2.text();
		String content="";
		String contenthtml="";
		Elements contentele=contentelement.select("p");
		int size=contentele.size();
		int i=0;
		while(size>0){
			Element contentpart=contentele.get(i++);
				content+=contentpart.text();
				contenthtml+=contentpart.html();     //问题：怎么将内容的标签p输出来(查看原网页)
			size--;
		}
		String datetotal=doc.select(".date").text();
		String date="";
		Matcher m=datePattern.matcher(datetotal);
		if(m.find()){
			date=m.group(1);
		}
		NewsModel model=new NewsModel(url,NewsProvider.MalaTv3);
		model.setTitle(title);
		model.setContent(content);
		model.setContentHtml(contenthtml);
		model.setDate(date);
		model.setHtml(html);
		return model;
	}

}
