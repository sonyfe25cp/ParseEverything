package test;

import junit.framework.TestCase;

import org.junit.Test;

import webpageparser.utils.Utils;

import com.parseeverything.html.SinaNewsParser;
import com.parseeverything.result.NewsModel;


public class TestSinaNewsParser extends TestCase{

    @Test
    public void testChanjing(){
        String html = Utils.getResouce("sina/sina_company_1.html");
        SinaNewsParser snp = new SinaNewsParser();
        String url = "http://finance.sina.com.cn/roll/20080930/21265353746.shtml";
        NewsModel sina = snp.parse(url, html);
        assertEquals("联合利华在港澳召回4个批次立顿奶茶", sina.getTitle());
        assertEquals("2008-09-30", sina.getPublishDate());
        System.out.println(sina);
    }
    @Test
    public void testChanjing2(){
        String html = Utils.getResouce("sina/sina_company_2.html");
        SinaNewsParser snp = new SinaNewsParser();
        String url = "http://finance.sina.com.cn/roll/20080930/21265353746.shtml";
        NewsModel sina = snp.parse(url, html);
        assertEquals("金百利菠萝啤是碳酸饮料 被指标识不清误导消费", sina.getTitle());
        assertEquals("2009-07-16", sina.getPublishDate());
        System.out.println(sina);
    }
    
}
