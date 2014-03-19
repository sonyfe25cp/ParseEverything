/**
 * 
 */
package com.parseeverything.html;

/**
 *解析马新社中文网站的news
 * @author Mi Jing
 * @date 2014-3-15
 */


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parseeverything.result.NewsModel;
import com.parseeverything.utils.NewsProvider;

public class MarChinaWebParser extends NewsPageParser{
	
	static Logger logger=LoggerFactory.getLogger(MarChinaWebParser.class);
	
	@Override
	public boolean match(String url){
		return url.contains("http://mandarin.bernama.com/v3/index.php?sid=news&");
	}
	
	@Override
	public NewsModel parse(String url, String html){
		Document doc=Jsoup.parse(html);
		Elements elemt=doc.select("#table3");
		String title=elemt.select("font[color=#1A62DB]").text();
		Elements contelemt=elemt.select("p[align=left]");
		String content="";
		String contenthtml="";
		if(contelemt.size()>0){
			Elements contelemt2=contelemt.select(("font[size=2]"));
			if(contelemt2.size()>0)
				content=contelemt2.text();
			    contenthtml=contelemt2.html();
		}

		NewsModel model=new NewsModel(url,NewsProvider.MarChinaWeb);
		model.setTitle(title);
		model.setContent(content);
		model.setContentHtml(contenthtml);
		model.setHtml(html);
		return model;
		
	}

}
