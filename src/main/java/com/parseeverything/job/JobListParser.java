package com.parseeverything.job;

import java.util.List;

import com.parseeverything.result.Job;

/**
 * 解析列表页
 * @author ChenJie
 * @date 21 Feb, 2014
 */
public abstract class JobListParser implements Parser {

    public abstract List<Job> parse(String html, String url);

}
