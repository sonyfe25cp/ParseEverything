package com.parseeverything.job;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.parseeverything.result.Job;
import com.parseeverything.utils.JobProvider;

/**
 * chinaHr的职位页面解析
 * 
 * @author ChenJie
 * @date 16 Feb, 2014
 */
public class ChinaHrParser extends JobParser {

    private String mainView = ".nowjobbox-left";

    public Job parse(String html, String url) {
        Job job = new Job();
        job.provider = JobProvider.ChinaHr;
        job.url = url;
        Document doc = Jsoup.parse(html);
        Element root = doc.select(mainView).get(0);

        Element titleAndCompany = root.select(".nowjobbox-left-title").get(0);
        String title = titleAndCompany.select(".nowjobbox-name").text();
        String company = titleAndCompany.select(".nowjobbox-name_c").text();

        job.title = title;
        job.company = company;
        job.ownerCompany.name = company;

        Elements companyDesc = root.select(".nowjobbox-bg > nowjobbox-comlist span");
        for (Element tmpDesc : companyDesc) {
            String text = tmpDesc.text();
            String[] array = text.split("：");
            if (array.length != 2) {
                continue;
            }
            switch (array[0]) {
                case "公司规模":
                    job.ownerCompany.employee = array[1];
                    break;
                case "公司类型":
                    job.ownerCompany.category = array[1];
                    break;
                case "公司行业":
                    job.ownerCompany.field = array[1];
                    break;
            }
        }

        Elements jobRequirements = root.select(".nowjobbox-bg .nowjobbox-main-box li");
        for (Element tmpReq : jobRequirements) {
            String text = tmpReq.text();
            String[] array = text.split("：");
            if (array.length != 2) {
                continue;
            }
            switch (array[0]) {
                case "性别要求":
                    break;
                case "年龄要求":
                    break;
                case "雇用形式":
                    break;
                case "截止日期":
                    break;
                case "学历要求":
                    job.degree = array[1];
                    break;
                case "薪资待遇":
                    job.salary = array[1];
                    break;
                case "工作经验":
                    job.experience = array[1];
                    break;
                case "工作地点":
                    job.place = array[1];
                    break;
            }
        }

        Elements descs = root.select(".nowjobbox-bg .nowjobbox-main-listbox");
        for (Element someDesc : descs) {
            String text = someDesc.select(".main-listbox-title").text();
            String partDesc = someDesc.select(".main-listbox-jobtxt").text();
            switch (text) {
                case "职位描述":
                    job.desc = partDesc;
                    break;
                case "任职条件":
                    job.request = partDesc;
                    break;
                case "相关福利：":
                    job.welfare = partDesc;
                    break;
            }
        }
        return job;
    }

    @Override
    public boolean match(String url) {
        return url.contains("chinahr.com");
    }

}
