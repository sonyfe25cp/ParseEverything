package com.parseeverything.job;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parseeverything.result.Job;
import com.parseeverything.utils.JobProvider;

/**
 * 
 * @author ChenJie
 * @date 21 Feb, 2014
 */
public class LiepinListParser extends JobListParser {

    static Logger logger = LoggerFactory.getLogger(LiepinParser.class);

    @Override
    public boolean match(String url) {
        return url.contains("liepin.com");
    }

    @Override
    public List<Job> parse(String html, String url){
        Document doc = Jsoup.parse(html);
        
        Elements lis = doc.select(".job-list > ul > li");
        List<Job> jobList = null;
        if(lis.size() > 0){
            jobList = new ArrayList<>();
            for(Element li : lis){
                Job job = new Job(JobProvider.Liepin);
                job.url = li.select("a").attr("href");
                Elements tmpLis = li.select("ul > li");
                for(Element element : tmpLis){
                    String tmpText = element.html();
                    if(tmpText.contains("发布日期")){
                        String date = tmpText.substring(tmpText.lastIndexOf("span>")+5);
                        job.tmpDate = date;
                        break;
                    }
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

}
