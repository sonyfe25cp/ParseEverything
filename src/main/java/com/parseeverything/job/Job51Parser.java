package com.parseeverything.job;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parseeverything.result.Job;
import com.parseeverything.utils.JobProvider;

/**
 * Created by feng on 2/11/14.
 */
public class Job51Parser extends JobParser {
    static Logger logger = LoggerFactory.getLogger(Job51Parser.class);
    @Override
    public boolean match(String url) {
        return url.contains("51job.com");
    }

    @Override
    public Job parse(String html, String url) {
        Job j = new Job(JobProvider.Job51);
        Document doc = Jsoup.parse(html);

        // Job 标题
        j.title = doc.select(".sr_bt").text();
        String companyName = doc.select("td[width=70%] a[href^=http]").text();
        j.company = companyName;
        j.ownerCompany.name = companyName;
        j.url = url;
        //公司信息
        Elements companyParts = doc.select(".jobs_1 td").eq(4);
        if(companyParts != null){
            String companyText = companyParts.text();
            if(companyText.contains("公司行业") || companyText.contains("公司性质") || companyText.contains("公司规模")){
                String[] tmp = companyText.split(" ");
                for(int index = 0; index < tmp.length ; index=index+2){
                    switch(tmp[index]){
                        case "公司行业：":
                            j.ownerCompany.field = tmp[index+1];
                            break;
                        case "公司性质：":
                            j.ownerCompany.category = tmp[index+1];
                            break;
                        case "公司规模：":
                            j.ownerCompany.employee = tmp[index+1];
                            break;
                    }
                }
            }
        }
        
        Elements ps = doc.select(".jobs_com").eq(1);
        if(ps != null){
            j.ownerCompany.desc = ps.text();
        }
        
        // 职位信息
        Elements jobInfo = doc.select("table[width^=98] tr");
        if(jobInfo.size() > 1){
            for (int index = 0; index < 2; index++) {//只要前两行
                Element tr = jobInfo.get(index);
                if(tr !=null){
                    extractJobInfo(tr, j);
                }
            }
        }

        for (Element detail : doc.select(".job_detail")) {
            String text = detail.text();
            if (text.contains("职位标签") || text.contains("职位职能")) {
                detail.select("strong").remove();
                j.responsibility += detail.text().trim();
                j.responsibility += " ";
            } else if (text.contains("职位描述")) {
                j.desc = detail.select(">div").text();
            }
        }

        return j;
    }

    // 发布日期：	2014-02-11	工作地点：	深圳-南山区	招聘人数：	2
    // 工作年限：	三年以上	学    历：	高中
    private void extractJobInfo(Element tr, Job j) {
        Elements tds = tr.children();
        for (int i = 0; i < tds.size(); i += 2) {
            String text = tds.get(i).text();
            if (text.contains("发布日期：")) {//最终页的都是更新日期
                j.updateDate = tds.get(i + 1).text();
            } else if (text.contains("工作地点：")) {
                j.place = tds.get(i + 1).text();
            } else if (text.contains("工作年限")) {
                j.experience = tds.get(i + 1).text();
            } else if (text.contains("学&nbsp;&nbsp;&nbsp;&nbsp;历")) {
                j.degree = tds.get(i + 1).text();
            } else if (text.contains("招聘人数：")) {

            }
        }
    }
}
