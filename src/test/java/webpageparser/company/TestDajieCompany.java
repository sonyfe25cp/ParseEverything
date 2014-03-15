package webpageparser.company;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import webpageparser.utils.Utils;

import com.parseeverything.company.DajieCompanyParser;
import com.parseeverything.result.Company;

public class TestDajieCompany extends TestCase{

    @Test
    public void testParse1() throws Exception{
        DajieCompanyParser dcp = new DajieCompanyParser();
            Company company = dcp.parse(Utils.getResouce("company/dajie-01.html"), "http://www.dajie.com/corp/1000001");
            Assert.assertEquals("中国海洋石油总公司", company.name);
            Assert.assertEquals("原油/能源", company.field);
            Assert.assertEquals("国有企业", company.category);
            Assert.assertEquals(null, company.employee);
            Assert.assertEquals("中国海油, 中国海洋石油总公司, 中海油", company.alias);
    }
    @Test
    public void testParse2() throws Exception{
        DajieCompanyParser dcp = new DajieCompanyParser();
            Company company = dcp.parse(Utils.getResouce("company/dajie-02.html"), "http://www.dajie.com/corp/1037612");
            Assert.assertEquals("(美国)英策・博斯商务投资", company.name);
            Assert.assertEquals(null, company.field);
            Assert.assertEquals("外资·合资", company.category);
            Assert.assertEquals("500 - 999人", company.employee);
    }
}
