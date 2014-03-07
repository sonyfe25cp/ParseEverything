package com.parseeverything.html;

import com.parseeverything.god.HtmlParser;
import com.parseeverything.result.NewsModel;


public abstract class NewsPageParser implements HtmlParser {

    public abstract boolean match(String url);
    
    public abstract NewsModel parse(String url, String html);

}
