package com.parseeverything.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.parseeverything.utils.JobProvider;

public class Job {

    public int id;

    // 名字
    public String title;

    //智联的一堆招聘细节
    public String details;

    // 原站job ID
    public String tpID;

    // 公司名字
    public String company;
    public Company ownerCompany;//所属公司

    // 原站的 公司ID
    public String tpCompanyID;

    // 从什么地方抓回来的： 51job， lagou ...
    public JobProvider provider;

    // 要求
    public String request;

    // 工作地点
    public String place;
    public Set<String> placeSet;//一个职位解析出多个地点时，需要拆分成多个职位

    // 薪水信息
    public String salary;

    public String salaryYear;

    // 工作年限
    public String experience;

    public Set<String> jobClass = new HashSet<>();//职位 多个职位 --hr填职位的时候，下拉框选的
    public Set<String> industry = new HashSet<>();//行业 一个行业 --hr填职位的时候，只能选一个行业

    // 职位职能 职位标签
    public String responsibility;

    // 职位描述
    public String desc;

    // 发布日期
    public String publishDate;
    public String updateDate;//更新日期
    public String tmpDate;//解析出来还没归属的日期

    public String url;

    // 学历，学校要求
    public String degree;

    public String welfare;//猎聘-福利待遇

    public String jobRequirement; //猎聘-岗位要求

    public boolean fromSeller;//标示来自销售
    public boolean fromLietou;//标示是猎头提供职位

    public static void mergeIndustryAndJobClass(Job old, Job newJob) {
        if (newJob.jobClass.size() > 0) {
            old.jobClass.addAll(newJob.jobClass);
        }

        if (newJob.industry.size() > 0) {
            old.jobClass.addAll(newJob.industry);
        }
    }

    public void addJobClass(String fieldName) {
        this.jobClass.add(fieldName);
    }

    public String toJobClass() {
        return set2String(this.jobClass);
    }

    public String toIndustry() {
        return set2String(this.industry);
    }

    private String set2String(Set<String> set) {
        if (set.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String tmp : set) {
                sb.append(tmp);
                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
            return sb.toString();
        } else {
            return "";
        }
    }


    public Job() {
        this.ownerCompany = new Company();
    }

    public Job(JobProvider provider) {
        this();
        this.provider = provider;
    }

    public String toListString() {
        StringBuilder sb = new StringBuilder();
        sb.append("tmpDate:" + tmpDate);
        sb.append("\n");
        sb.append("url:" + url);
        sb.append("\n");
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("title: " + title);
        sb.append("\n");
        sb.append("company: " + company);
        sb.append("\n");
        sb.append("welfare: " + welfare);
        sb.append("\n");
        sb.append("details: " + details);
        sb.append("\n");
        sb.append("desc:" + desc);
        sb.append("\n");
        sb.append("salaryYear:" + salaryYear);
        sb.append("\n");
        sb.append("salary:" + salary);
        sb.append("\n");
        sb.append("experience:" + experience);
        sb.append("\n");
        sb.append("degree:" + degree);
        sb.append("\n");
        sb.append("place:" + place);
        sb.append("\n");
        sb.append("publishDate:" + publishDate);
        sb.append("\n");
        sb.append("updateDate:" + updateDate);
        sb.append("\n");
        sb.append("tmpDate:" + tmpDate);
        sb.append("\n");
        sb.append("jobRequirement:" + jobRequirement);
        sb.append("\n");
        sb.append("jobProvider:" + provider);
        sb.append("\n");
        sb.append("url:" + url);
        sb.append("\n");
        sb.append(this.ownerCompany.toString());
        return sb.toString();
    }

    public static List<Job> fromList(List<Map<String, String>> list) {
        List<Job> jobs = new ArrayList<Job>();
        for (Map<String, String> map : list) {
            Job job = fromMap(map);
            jobs.add(job);
        }
        return jobs;
    }

    /*
     * 从智联招聘解析模板返回数据
     */
    public static Job fromMap(Map<String, String> map) {
        String title = map.get("title");
        String company = map.get("company");
        String publishDate = map.get("publishDate");
        String details = map.get("details");
        String desc = map.get("description");
        String url = map.get("url");

        Job job = new Job(title, company, desc, publishDate, url, details);
        deepParseDetailsForZhiLian(job, details);
        return job;
    }

    /**
     * 解析智联的招聘要求信息
     *
     * @param job
     * @param details
     */
    private static void deepParseDetailsForZhiLian(Job job, String details) {
        details = details.replace("正在加载更多城市", "");//垃圾数据
        String[] tmpArray = details.split(" ");
        if (tmpArray.length == 0) {
            return;
        }
        for (int index = 0; index < tmpArray.length; index++) {
            String current = tmpArray[index];
            switch (current) {
                case "工作经验：":
                    job.experience = tmpArray[index + 1];
                    break;
                case "最低学历：":
                    job.degree = tmpArray[index + 1];
                    break;
                case "职位月薪：":
                    job.salary = tmpArray[index + 1];
                    break;
                case "工作地点：":
                    job.place = tmpArray[index + 1];
                    break;
                case "职位类别：":
                    break;
            }
        }
    }

    /*
     * {fromMap} 方法 专用
     */
    public Job(String title, String company, String desc, String publishDate, String url,
               String details) {
        super();
        this.title = title;
        this.company = company;
        this.desc = desc;
        this.publishDate = publishDate;
        this.details = details;
        this.url = url;
    }

    /*
     * 从数据库转换使用
     */
    public static List<Job> parseFromRs(ResultSet rs) {
        List<Job> jobs = null;
        Job job = null;
        try {
            while (rs.next()) {
                if (jobs == null) {
                    jobs = new ArrayList<Job>();
                }
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String company = rs.getString("company");
                String publishTime = rs.getString("publishDate");
                String updateTime = rs.getString("updateDate");
                String place = rs.getString("place");
                String details = rs.getString("details");
                String url = rs.getString("url");
                String description = rs.getString("description");
                String salary = rs.getString("salary");
                String salaryYear = rs.getString("salaryYear");
                String experience = rs.getString("experience");
                String degree = rs.getString("degree");
                String welfare = rs.getString("welfare");
                String jobRequirement = rs.getString("jobRequirement");
                String provider = rs.getString("provider");
                JobProvider jp = Enum.valueOf(JobProvider.class, provider);
                String industry = rs.getString("industry");
                String jobClassStr = rs.getString("jobClass");
                Set<String> jobClassArray = toSet(jobClassStr);
                Set<String> industrySet = toSet(industry);
                job = new Job(id, company, title, details, description, publishTime, updateTime, url, place,
                        salary, salaryYear, experience, degree, welfare, jobRequirement, jp, industrySet, jobClassArray);
                jobs.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }

    private static Set<String> toSet(String jobClassStr) {
        Set<String> jobClassArray = new HashSet<>();

        if (jobClassStr != null && jobClassStr.length() > 0) {
            String[] tmp = jobClassStr.split(",");
            Collections.addAll(jobClassArray, tmp);
        }
        return jobClassArray;
    }

    /*
     * {parseFromRs} 专用
     */
    public Job(int id, String company, String title, String details, String desc,
               String publishDate, String updateDate, String url, String place, String salary, String salaryYear, String experience,
               String degree, String welfare, String jobRequirement, JobProvider jp, Set<String> industry, Set<String> jobClassArray) {
        super();
        this.id = id;
        this.title = title;
        this.company = company;
        this.details = details;
        this.desc = desc;
        this.publishDate = publishDate;
        this.updateDate = updateDate;
        this.url = url;
        this.place = place;
        this.salary = salary;
        this.salaryYear = salaryYear;
        this.experience = experience;
        this.degree = degree;
        this.welfare = welfare;
        this.jobRequirement = jobRequirement;
        this.provider = jp;
        this.industry = industry;
        this.jobClass = jobClassArray;
    }
}
