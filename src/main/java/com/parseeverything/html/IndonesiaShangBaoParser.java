/**
 * 
 */
package com.parseeverything.html;

/**
 *解析印度尼西亚商报
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

public class IndonesiaShangBaoParser extends NewsPageParser {

	static Logger logger = LoggerFactory
			.getLogger(IndonesiaShangBaoParser.class);

	static Pattern datePattern = Pattern
			.compile("(\\w+\\s*\\d{1,2}\\w+\\,\\s*\\d{4})");

	@Override
	public boolean match(String url) {
		return url.contains("shangbaoindonesia.com");
	}

	@Override
	public NewsModel parse(String url, String html) {
		Document doc = Jsoup.parse(html);
		String title = doc.select(".singlePageTitle").text();
		Element contentElement = doc.select(".post").first();
		String content = "";
		String contentHtml = "";
		for (Element part : contentElement.children()) {
			if (part.tagName().equals("p")) {
				content += part.text();
				contentHtml +="<p>"+ part.html()+"</p>";
			}
		}

		Elements select = contentElement.select(".postDate");

		String datetotal = select.text();
		Matcher m = datePattern.matcher(datetotal);
		if (m.find()) {
			datetotal = m.group(1);
		}
		String[] s1 = datetotal.split(" ");

		String year = s1[2];
		String month = s1[0];
		String day = s1[1].substring(0, 2);
		switch (month) {
		case "January":
			month = "01";
			break;
		case "February":
			month = "02";
			break;
		case "March":
			month = "03";
			break;
		case "April":
			month = "04";
			break;
		case "May":
			month = "05";
			break;
		case "June":
			month = "06";
			break;
		case "July":
			month = "07";
			break;
		case "August":
			month = "08";
			break;
		case "September":
			month = "09";
			break;
		case "October":
			month = "10";
			break;
		case "November":
			month = "11";
			break;
		default:
			month = "12";
			break;
		}
		String date = year + "-" + month + "-" + day;

		NewsModel model = new NewsModel(url, NewsProvider.IndonesiShangBao);
		model.setTitle(title);
		model.setContent(content);
		model.setContentHtml(contentHtml);
		model.setPublishDate(date);
		model.setHtml(html);
		return model;
	}

}
