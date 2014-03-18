/**
 * 
 */
package com.parseeverything.html;
/**
 *解析印度尼西亚国际日报新闻
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





public class IndonesialJournalParser extends NewsPageParser{
	
	static Logger logger=LoggerFactory.getLogger(IndonesialJournalParser.class);
	
	static Pattern datePattern=Pattern.compile("(\\/\\d{8}\\/)");
	
	@Override
	public boolean match(String url){
		return url.equals("guojiribao.com/shtml/gjrb");
	}
	
	@Override
	public NewsModel parse(String url,String html){
		Document doc=Jsoup.parse(html);
		Elements titlelement=doc.select(".f-20");
		String title=titlelement.select("strong").text();
		Elements contentElement=doc.select(".f-14");
		String content=contentElement.text();
		String contenthtml=contentElement.html();
		String date="";
		Matcher m=datePattern.matcher(url);
		if(m.find()){
			date=m.group(1);
		}
		String year=date.substring(1,5);
		String month=date.substring(5,7);
		String day=date.substring(7,9);
		date=year+"-"+month+"-"+day;
		NewsModel model=new NewsModel(url,NewsProvider.IndonesialJournal);
		model.setTitle(title);
		model.setContent(content);
		model.setContentHtml(contenthtml);
		model.setDate(date);
		model.setUrl(url);
		return model;
		
	}

}
