package com.parseeverthing.sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/omar/data/zhilian/zhaopin_zunyi_2014-01-29-18-01-22.pages";//文件绝对路径
        File file = new File(filePath);
        PageStoreReader psr = new PageStoreReader(file);
        ArrayList<StoredPage> list = psr.loadAll();
        int count = 33;
        for (StoredPage page : list) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/tmp/data_for_uts/jobs/"+count+".txt"))); 
            String url = page.getHeader("URL");
            String html = page.getContent();
            
            logger.info("url: {}", url);
//            logger.info("html: {}", html);
            count ++;
            bw.write(html);
            bw.close();
        }
        logger.info("size : {}", list.size());
        psr.close();
    }
}
