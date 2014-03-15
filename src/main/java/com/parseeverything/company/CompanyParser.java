package com.parseeverything.company;

import com.parseeverything.god.HtmlParser;
import com.parseeverything.result.Company;

/**
 * 解析公司页面
 * @author Chen Jie
 * @date 15 Mar, 2014
 */
public abstract class CompanyParser implements HtmlParser {

    public abstract Company parse(String html, String url) throws Exception;
}
