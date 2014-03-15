package com.parseeverything.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.parseeverything.utils.CompanyProvider;


public class Company implements java.io.Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public int id;

    public String name;

    public String fullname;
    
    public String alias;//别名

    public String tpID;

    public String city;

    public String field;//行业

    public String employee;//雇员规模

    public String web;//网站

    public String category;//类型 -- 股份制

    public List<String> labels;

    public String desc;
    
    public CompanyProvider provider;
    
    public String url; //来源网址

    

    public Company() {
        super();
    }

    public Company(CompanyProvider provider) {
        super();
        this.provider = provider;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-------company-------");
        sb.append("\n");
        sb.append("name:" + name);
        sb.append("\n");
        sb.append("fullname:" + fullname);
        sb.append("\n");
        sb.append("field:" + field);
        sb.append("\n");
        sb.append("employee:" + employee);
        sb.append("\n");
        sb.append("category:" + category);
        sb.append("\n");
        sb.append("desc:" + desc);
        return sb.toString();
    }

    public static Company fromResultSet(ResultSet rs) {
        try {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Company company = new Company(id, name);
            return company;
        } catch (SQLException e) {
            return null;
        }
    }

    public Company(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

}
