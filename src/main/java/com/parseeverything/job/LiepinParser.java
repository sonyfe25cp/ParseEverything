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
 * 猎聘的职位页面解析
 * 
 * @author ChenJie
 * @date 15 Feb, 2014
 */
public class LiepinParser extends JobParser {
    static Logger logger = LoggerFactory.getLogger(LiepinParser.class);
    @Override
    public boolean match(String url) {
        return url.contains("liepin.com");
    }

    private String mainView = ".main-view"; //正文内容
    public Job parse(String html, String url) {
        Job job = parseByCompany(html, url);
        return job;
    }
    
    public Job parseByCompany(String html, String url) {
        Document doc = Jsoup.parse(html);
        Elements select = doc.select(mainView);
        if(select .size() == 0){
            logger.error("解析错误, not found {} , {}", mainView, url);
            return new Job(JobProvider.Liepin);
        }
        Element root = select.get(0);
        Elements children = root.children();
        Job job = new Job(JobProvider.Liepin);
        job.url = url;
        for (int index = 0; index < children.size(); index++) {
            Element element = children.get(index);
            String label = element.tagName();
            switch (label) {
                case "h1":
                    String title = element.text();
                    job.title = title;
                    parseMeta(job, children.get(index + 1), children.get(index + 2));
                    break;
                case "h3":
                    String content = element.text();
                    switch (content) {
                        case "基本信息：":
                            String details = parseNext(children.get(index + 1));
                            job.details = details;
                            deepParseDetails(job, children.get(index + 1));
                            break;
                        case "职位描述：":
                            String desc = parseNext(children.get(index + 1));
                            job.desc = desc;
                            break;
                        case "岗位职责：":
                            job.desc = parseNext(children.get(index + 1));
                            break;
                        case "岗位要求：":
                            parseRequirement(job, children.get(index + 1));
                            break;
                        case "企业介绍：":
                            String companyDesc = parseNext(children.get(index + 1));
                            job.ownerCompany.desc = companyDesc;
                            break;
                        case "薪酬福利：":
                            String welfare = children.get(index +1).select("li").text();
                            job.welfare = welfare;
                            break;
                        case "任职资格的具体描述：":
                            job.jobRequirement = children.get(index +1).text();
                            break;
                    }
                    break;
            }
        }
        return job;
    }

    private void parseMeta(Job job, Element... es) {
        for (Element e : es) {
            String classValue = e.attr("class");
            String nodeName = e.tagName();
            switch (classValue) {
                case "job-title":
                    String company = e.select("p > a").text();
                    job.company = company;
                    job.ownerCompany.name = company;
                    String salaryYear = e.select("strong.big").text();
                    job.salaryYear = salaryYear;
                    job.salary = salaryYear;
                    break;
                case "tag-list clearfix":
                    String welfare = e.text();
                    job.welfare = welfare;
                    break;
                case "":
                    break;
                case "a-content"://猎头页面
                    String salaryYearTmp = e.select(".btn-apply em").html();
                    job.salaryYear = salaryYearTmp; 
                    job.salary = salaryYearTmp;
                    Elements lis = e.select("li");
                    for(Element li : lis){
                        String tmp[] = li.text().split("：");
                        if(tmp.length != 2){
                            continue;
                        }
                        switch(tmp[0]){
                            case "汇报对象":
                                break;
                            case "下属人数":
                                break;
                            case "所属部门":
                                break;
                            case "企业性质":
                                job.ownerCompany.category = tmp[1];
                                break;
                            case "企业规模":
                                job.ownerCompany.employee = tmp[1];
                                break;
                            case "工作地点":
                                job.place = tmp[1];
                                break;
                            case "发布日期":
                                job.updateDate = tmp[1];
                                break;
                        }
                    }
                    break;
                default:
                    break;
            }
            if(nodeName.equals("h2")){
                String tmp = e.html().substring(e.html().indexOf("：")+1, e.html().indexOf("<sup"));
//                logger.info("tmp : "+ tmp);
                job.fromLietou = true;
            }
        }
    }

    private String parseNext(Element e1) {
        String text = e1.html();
        return text;
    }
    
    private void parseRequirement(Job job, Element element){
        deepParseDetails(job, element);
    }

    private void deepParseDetails(Job job, Element element) {

        Elements lis = element.select("li");
        for (int index = 0; index < lis.size(); index++) {
            Element li = lis.get(index);
            String text = li.text();
            String[] array = text.split("：");
            String current = array[0];
            if(array.length == 2){
                switch (current) {
                    case "汇报对象":
                        break;
                    case "工作地点":
                        job.place = array[1];
                        break;
                    case "下属人数":
                        break;
                    case "所属行业:":
                        job.ownerCompany.field = array[1];
                        break;
                    case "所属部门":
                        break;
                    case "企业性质":
                        job.ownerCompany.category = array[1];
                        break;
                    case "企业规模":
                        job.ownerCompany.employee = array[1];
                        break;
                    case "发布日期":
                        job.updateDate = array[1];
                        break;
                    case "学历要求":
                        job.degree = array[1];
                        break;
                    case "性别要求":
                        break;
                    case "语言要求":
                        break;
                    case "年龄要求":
                        break;
                    case "专业要求":
                        break;
                    case "工作年限":
                        job.experience = array[1];
                        break;
                }
            }
        }
    }
}
