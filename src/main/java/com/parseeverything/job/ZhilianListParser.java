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

public class ZhilianListParser extends JobListParser {

    static Logger logger = LoggerFactory.getLogger(ZhilianParser.class);

    @Override
    public boolean match(String url) {
        return url.contains("zhaopin.com");
    }
    private boolean firstOrUpdate = false;
    @Override
    public List<Job> parse(String html, String url) {

        Document doc = Jsoup.parse(html);

        String aText = doc.select("a.modeof3current").text();
        if(aText.equals("发布日期")){
            firstOrUpdate = false;
        }
        if(aText.equals("首发日")){
            firstOrUpdate = true;
        }
        Elements tables = doc.select(".search-result-cont > table");
        List<Job> jobs = null;
        if(tables.size() > 0){
            jobs = new ArrayList<>();
            for(Element table : tables){
                Job job = new Job(JobProvider.Zhilian);
                Elements tds = table.select("td");
                for(Element td : tds){
                    String attr = td.attr("class");
                    switch(attr){
                        case "Jobname tabCol6":
                            job.url = td.select("a").attr("href");
                            break;
                        case "releasetime tabCol7":
                            job.tmpDate = "20"+td.text();
                            break;
                    }
                }
                jobs.add(job);
            }
            if(firstOrUpdate){//首发
                for(Job job : jobs){
                    job.publishDate = job.tmpDate;
                    job.updateDate = null;
                }
            }else{//更新
                for(Job job : jobs){
                    job.updateDate = job.tmpDate;
                    job.publishDate = null;
                }
            }
        }
        return jobs;
    }

}
