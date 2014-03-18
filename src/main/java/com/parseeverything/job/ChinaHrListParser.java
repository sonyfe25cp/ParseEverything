package com.parseeverything.job;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.parseeverything.result.Job;
import com.parseeverything.utils.JobProvider;


public class ChinaHrListParser extends JobListParser{

    @Override
    public boolean match(String url) {
        return url.contains("chinahr.com");
    }

    @Override
    public List<Job> parse(String html, String url) {
        List<Job> jobs = null;
        Document document = Jsoup.parse(html);
        Elements tables = document.select(".joblist_b_border table");
        if(tables.size()> 0){
            jobs = new ArrayList<Job>();
            for(Element table : tables){
                Element firstTr = table.select("tr").first();
                Elements tds = firstTr.select("td");
                if(tds.size() == 5){
                    Job job = new Job(JobProvider.ChinaHr);
                    job.url = tds.get(1).select("a").attr("href");
                    job.tmpDate = tds.get(4).text();
                    jobs.add(job);
                }
            }
        }
        return jobs;
    }

}
