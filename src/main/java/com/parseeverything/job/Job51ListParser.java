package com.parseeverything.job;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.parseeverything.result.Job;
import com.parseeverything.utils.JobProvider;


public class Job51ListParser extends JobListParser{

    @Override
    public boolean match(String url) {
        return url.contains("51job.com");
    }

    private boolean firstOrUpdate = false;
    
    @Override
    public List<Job> parse(String html, String url) {
        List<Job> jobs = null;
        
        Document document = Jsoup.parse(html);
        
        Elements trs = document.select("#resultList tr");
        if(trs.size() > 0){
            jobs = new ArrayList<>();
            for(Element tr : trs){
                Elements tds = tr.select("td");
                if(tds.size() == 6){
                    Job job = new Job(JobProvider.Job51);
                    job.url = tds.get(1).select("a").attr("href");
                    String text = tds.get(4).text();
                    job.tmpDate = text;
                    if(text.equals("首发日")){
                        firstOrUpdate = true;
                        continue;
                    }
                    if(text.equals("更新日")){
                        firstOrUpdate = false;
                        continue;
                    }
                    jobs.add(job);
                }
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
