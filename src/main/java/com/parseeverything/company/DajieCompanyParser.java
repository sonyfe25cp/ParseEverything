package com.parseeverything.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parseeverything.result.Company;
import com.parseeverything.utils.CompanyProvider;

public class DajieCompanyParser extends CompanyParser {
    static Logger logger = LoggerFactory.getLogger(DajieCompanyParser.class);
    @Override
    public boolean match(String url) {
        return url.contains("www.dajie.com");
    }

    @Override
    public Company parse(String html, String url) throws Exception {
        Company company = new Company(CompanyProvider.Dajie);
        company.url = url;

        Document doc = Jsoup.parse(html);
        String name = doc.select(".cor-logo h1").text().replaceAll("[校园|职场版]", "");
        company.name = name;
        String desc = doc.select(".J_allIntro").html();
        company.desc = desc;
        Elements trs = doc.select(".cor-table table tr");
        for (int index = 0; index < trs.size(); index = index + 2) {
            Elements ths = trs.get(index).select("th");
//            logger.info("index {}", index);
            for (int ind = 0; ind < ths.size(); ind++) {
                String text = ths.get(ind).text();
                Element element = trs.get(index + 1);
//                logger.info("text, index ind {}, {}", text, ind);
                String value = getValue(element, ind);
                if(value.equals("暂无")||value.equals("")){
                    continue;
                }
                switch (text) {
                    case "行业":
                        company.field = value;
                        break;
                    case "性质":
                        company.category = value;
                        break;
                    case "地区":
                        break;
                    case "规模":
                        company.employee = value;
                        break;
                    case "地址":

                        break;
                    case "网址":
                        company.web = value;
                        break;
                    case "别名":
                        company.alias = value;
                        break;
                    default:
                        break;
                }
            }
        }
        return company;

    }

    private String getValue(Element tr, int ind) {
        Elements tds = tr.select("td");
        if (tds.size() >= ind) {
            return tds.get(ind).text();
        } else return null;
    }

}
