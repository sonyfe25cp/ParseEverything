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
 * 智联招聘职位解析
 * 
 * @author ChenJie
 * @date 17 Feb, 2014
 */
public class ZhilianParser extends JobParser {
    static Logger logger = LoggerFactory.getLogger(ZhilianParser.class);
    @Override
    public boolean match(String url) {
        return url.contains("zhaopin.com");
    }

    @Override
    public Job parse(String html, String url){
        Job job = new Job();
        job.provider = JobProvider.Zhilian;
        job.url = url;
        Document doc = Jsoup.parse(html);
        Elements root = doc.select(".terminalpage-left");
        Elements mainEle = root.select(".terminalpage-left-top");
        String date = mainEle.select(".top-right span").text();
        job.updateDate = date;
        Elements trs = mainEle.select(".top-left tr");
        for(Element tr : trs){
            Elements tds = tr.select("td");
            switch(tds.size()){
                case 1:
                    Elements h1s = tds.select("h1");
                    if(h1s.size() == 1){
                        job.title = tds.text();
                    }
                    Elements h2s = tds.select("h2");
                    if(h2s.size() == 1){
                        job.company = tds.text();
                        job.ownerCompany.name = tds.text();
                    }
                    break;
                case 2:
                    String td1 = tds.get(0).text();
                    String value = tds.get(1).text();
                    switch(td1){
                        case "公司规模：":
                            job.ownerCompany.employee = value;
                            break;
                        case "公司性质：":
                            job.ownerCompany.category = value;
                            break;
                        case "公司行业：":
                            job.ownerCompany.field = value;
                            break;
                    }
                    break;
                case 4:
                    for(int tmpIndex = 0 ; tmpIndex < 4; tmpIndex= tmpIndex+2){
                        String tmp1 = tds.get(tmpIndex).text();
                        String tmp2 = tds.get(tmpIndex + 1).text();
                        switch(tmp1){
                            case "工作经验：":
                                job.experience = tmp2;
                                break;
                            case "工作性质：":
                                break;
                            case "最低学历：":
                                job.degree = tmp2;
                                break;
                            case "管理经验：":
                                break;
                            case "职位月薪：":
                                job.salary = tmp2;
                                break;
                            case "招聘人数：":
                                break;
                            case "工作地点：":
                                
                                job.place = tmp2.replace("正在加载更多城市", "");
                                break;
                            case "职位类别：":
                                break;
                        }
                    }
                    break;
            }
        }
        
        Elements kindsDesc = doc.select(".terminalpage-main");
        for(Element kindDesc : kindsDesc){
            Elements titleSpan = kindDesc.select("span.title-name");
            String textTitle = titleSpan.text();
            Elements descContent = kindDesc.select(".terminalpage-content");
            String textDesc = descContent.text();
            switch(textTitle){
                case "职位描述":
                    job.desc = textDesc;
                    break;
                case "公司介绍":
                    job.ownerCompany.desc = textDesc;
                    break;
            }
        }
        return job;
    }


    //旧版本-基于模板的解析
//    String regex = "src/main/resources/zhilian-details";
//    private HtmlParserWithJsoup hpwj = new HtmlParserWithJsoup(regex);
//    public Job parse(String html, String url) {
//        Job job = null;
//        hpwj.parseFromContent(html);
//        List<Map<String, String>> mapList = hpwj.getValueList();
//        List<Job> jobs = Job.fromList(mapList);
//        if (jobs.size() == 1) {
//            job = jobs.get(0);
//            job.url = url;
//            job.provider = JobProvider.Zhilian;
//            return job;
//        } else {
//            return null;
//        }
//    }

}
