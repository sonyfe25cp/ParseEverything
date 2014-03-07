package com.parseeverything.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.parseeverything.result.Company;
import com.parseeverything.result.Job;
import com.parseeverything.utils.JobProvider;

/**
 * Created with IntelliJ IDEA. User: feng Date: 2/10/14 Time: 11:37 AM
 */
public class LagouParser extends JobParser {

    static Pattern ID = Pattern.compile(".*jobs/(\\d+).html");

    static Pattern CID = Pattern.compile(".*c/(\\d+).html");

    static Pattern DateRegex = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");//2014-02-07

    private Company company(String html, String url) {
        Company c = new Company();

        Document doc = Jsoup.parse(html);

        c.name = doc.select("h2").text();
        c.fullname = doc.select("h1").text();

        // 地点	深圳. 领域	移动互联网,智能家居
        Elements ctags = doc.select(".c_tags tr");
        for (Element ctag : ctags) {
            String t = ctag.child(0).text().trim();
            switch (t) {
                case "地点":
                    c.city = ctag.child(1).text();
                case "领域":
                    c.field = ctag.child(1).text();
                case "规模":
                    c.employee = ctag.child(1).text();
                case "主页":
                    c.web = ctag.child(1).select("a").attr("href");
            }
        }

        Elements labels = doc.select("#hasLabels > li");
        c.labels = new ArrayList<String>(labels.size());
        for (Element label : labels) {
            c.labels.add(label.text());
        }
        Matcher m = CID.matcher(url);
        if (m.find()) {
            c.tpID = m.group(1);
        }

        c.desc = doc.select(".c_intro").html();

        return c;
    }

    @Override
    public boolean match(String url) {
        return url.contains("lagou.com/jobs");
    }

    @Override
    public Job parse(String html, String url) {
        Job job = new Job();
        job.url = url;
        Document doc = Jsoup.parse(html);

        Matcher m = ID.matcher(url);
        if (m.find()) {
            job.tpID = m.group(1);
        }

        m = CID.matcher(doc.select(".job_company > dt > a").attr("href"));
        if (m.find()) {
            job.tpCompanyID = m.group(1);
        }

        Elements h1 = doc.select("h1");

        job.title = h1.attr("title");
        job.provider = JobProvider.Lagou;
        job.company = doc.select("h1>div").text();
        job.request = doc.select(".job_request").html();

        String[] parts = job.request.split("/");
        if (parts.length > 0) job.place = parts[0].trim();
        if (parts.length > 2) {
            job.salary = parts[2].trim();
            job.salaryYear = parts[2].trim();
        }
        if (parts.length > 3) job.experience = parts[3].trim();
        if (parts.length > 4) job.degree = parts[4].trim();
        if (parts.length > 5) {
            String tmpHtml = parts[5];
            if (tmpHtml.contains("<br")) {
                Matcher md = DateRegex.matcher(tmpHtml);
                if (md.find()) {
                    job.updateDate = md.group(1);
                }else{
                    if(tmpHtml.contains("天前发布")){
                        String substring = tmpHtml.substring(0, tmpHtml.indexOf("天")).trim();
                        int day = Integer.parseInt(substring);
                        job.updateDate = servalDaysBefore(day);
                    }else if(tmpHtml.contains("发布")){
                        job.updateDate = sdf.format(new Date());
                    }
                }
            } else {
                job.updateDate = parts[5].trim();
            }
        }

        job.desc = doc.select(".job_bt p").html();
        
        job.ownerCompany = company(html, url);
        return job;
    }
    
    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private String servalDaysBefore(int num){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE)-num);
        Date date = cal.getTime();
        return sdf.format(date);
    }
}
