package com.parseeverything.html;
/**
 * 解析马来西亚国家通讯社business news
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

public class BeritaNationalMalaysia extends NewsPageParser{
	
	   static Logger logger = LoggerFactory.getLogger(BeritaNationalMalaysia.class);
	    
	 @Override
	 public boolean match(String url){
		 return url.contains("bernama.com/bernama/v7/bu/newsbusiness.php?");
	 }
	 
	 @Override
	 public NewsModel parse(String url, String html){
	        Document doc = Jsoup.parse(html);
	        Elements titlElemt = doc.select("#left");
	        String title=titlElemt.select("h1").text();
	        Elements contentElement = titlElemt.select(".NewsText");
	        String content = contentElement.text();
	        String contentHtml = contentElement.html();
	     
	        NewsModel model = new NewsModel(url, NewsProvider.BeritaNational);
	        model.setTitle(title);
	        model.setContent(content);
	        model.setContentHtml(contentHtml);
	        model.setHtml(html);
	        return model;
	 }

}
