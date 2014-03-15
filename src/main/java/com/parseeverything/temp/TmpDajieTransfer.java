package com.parseeverything.temp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.parseeverything.company.DajieCompanyParser;
import com.parseeverything.result.Company;

public class TmpDajieTransfer {

    @Option(name = "-path", usage = "comanies html path")
    private String comaniesPath = "";

    @Option(name = "-t", usage = "aim path")
    private String aimPath = "";

    @Option(name = "-h", usage = "Print help and exits")
    private boolean help = false;
    
    static Logger logger = LoggerFactory.getLogger(TmpDajieTransfer.class);
    
    public void run() throws IOException{
        if(comaniesPath.length() == 0){
            logger.error("请输入公司文件所在路径");
            System.exit(0);
        }
        File folder = new File(comaniesPath);
        DajieCompanyParser dcp = new DajieCompanyParser();
        List<Company> companies = new ArrayList<>();
        for(File file : folder.listFiles()){
            if(file.getName().endsWith("html")){
                try {
                    String html = FileUtils.readFileToString(file);
                    Company company = dcp.parse(html, "");
                    companies.add(company);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Gson gson = new Gson();
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(aimPath)));
        for(Company c : companies){
            String json = gson.toJson(c);
            logger.info("json");
            bw.write(json);
            bw.write("\n");
        }
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        TmpDajieTransfer tt = new TmpDajieTransfer();

        CmdLineParser parser = new CmdLineParser(tt);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
        if (tt.help) {
            System.err.println("java {{cp}} " + TmpDajieTransfer.class.getCanonicalName()
                    + " [options...] arguments...");
            parser.printUsage(System.err);
            System.exit(1);
        } else {
            tt.run();
        }
    }

}
