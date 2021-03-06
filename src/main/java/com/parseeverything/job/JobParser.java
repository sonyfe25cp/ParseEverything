package com.parseeverything.job;

import com.parseeverything.god.HtmlParser;
import com.parseeverything.result.Job;

/**
 * 职位最终页解析
 * Created by feng on 2/13/14.
 */
public abstract class JobParser implements HtmlParser{

    public abstract Job parse(String html, String url) throws Exception;
    
}
