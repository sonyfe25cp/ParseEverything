package com.parseeverthing.sample;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parseeverthing.pages.PageStoreReader;
import com.parseeverthing.pages.StoredPage;

/**
 * 解析.pages文件
 * @author Chen Jie
 * @date 31 Mar, 2014
 */
public class ParsePages {
    static Logger logger = LoggerFactory.getLogger(ParsePages.class);

    public static void main(String[] args) {
        String filePath = "";//文件绝对路径
        File file = new File(filePath);
        PageStoreReader psr = new PageStoreReader(file);
        ArrayList<StoredPage> list = psr.loadAll();
        for (StoredPage page : list) {
            String url = page.getHeader("URL");
            String html = page.getContent();
            
            logger.info("url: {}", url);
            logger.info("html: {}", html);
        }
        psr.close();
    }
}
