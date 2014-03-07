//package com.parseeverything.job;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//public class ResumeParserFor51 extends HtmlParserBasedJsoup {
//
//    private List<Element> tables;
//
//    private Map<TableTitleFor51, Element> tablesWithLabel;
//
//    private List<String> partJsons;
//
//    public ResumeParserFor51() {
//        super();
//    }
//
//    public void clear() {
//        partJsons = new ArrayList<String>();
//        tables = new ArrayList<Element>();
//        tablesWithLabel = new HashMap<TableTitleFor51, Element>();
//    }
//
//    public void parseDocument() {
//        //0.先清空再解析
//        clear();
//        //1.预处理，获取各种table
//        preprocessing();
//        if (tables == null || tables.size() == 0) {
//            System.out.println("该简历无法解析.");
//            return;
//        }
//        //2.判断不同table的归属
//        tableJudge();
//        //3.根据不同的table归属解析其正文
//        tableDetect();
//        //4.聚合最终结果
//        outputJson();
//    }
//
//    private void tableJudge() {
//        for (Element table : tables) {
//            String text = table.text();
//            if (text.contains("居住地") || text.contains("户口")) {
//                tablesWithLabel.put(TableTitleFor51.BasicInformation, table);
//            } else if (text.contains("最近工作")) {
//                tablesWithLabel.put(TableTitleFor51.LastestWork, table);
//            } else if (text.contains("最高学历")) {
//                tablesWithLabel.put(TableTitleFor51.LastestEducation, table);
//            } else if (text.contains("目前年薪") || text.contains("基本工资")) {
//                tablesWithLabel.put(TableTitleFor51.CurrentSalary, table);
//            } else if (text.contains("自我评价")) {
//                tablesWithLabel.put(TableTitleFor51.SelfEvaluation, table);
//            } else if (text.contains("期望月薪") || text.contains("到岗时间")) {
//                tablesWithLabel.put(TableTitleFor51.DesiredPosition, table);
//            } else {
//                Element td = table.parent();
//                Element tr = td.parent();
//                int index = tr.siblingIndex();
//                Element tbody = tr.parent();
//                Elements nodes = tbody.getElementsByIndexEquals(index - 1);//若preprocessing改变，这地方也要改变
//                if (nodes.size() > 1) {
//                    Element nameNode = nodes.get(1);
//                    String nameText = nameNode.text().replace(" ", "");
//                    //					System.out.println("nameNode : " + nameText);
//                    if (nameText.equals("工作经验")) {
//                        tablesWithLabel.put(TableTitleFor51.WorkExpriences, table);
//                    } else if (nameText.equals("教育经历")) {
//                        tablesWithLabel.put(TableTitleFor51.Education, table);
//                    } else if (nameText.equals("培训经历")) {
//                        tablesWithLabel.put(TableTitleFor51.TrainExpriences, table);
//                    } else if (nameText.equals("语言能力")) {
//                        tablesWithLabel.put(TableTitleFor51.Language, table);
//                    } else if (nameText.equals("项目经验")) {
//                        tablesWithLabel.put(TableTitleFor51.ProjectExpriences, table);
//                    } else if (nameText.equals("所获奖项")) {
//                        tablesWithLabel.put(TableTitleFor51.Scores, table);
//                    } else if (nameText.equals("社会经验")) {
//                        tablesWithLabel.put(TableTitleFor51.SocietyExpriences, table);
//                    } else if (nameText.equals("证书")) {
//                        tablesWithLabel.put(TableTitleFor51.Papers, table);
//                    } else if (nameText.equals("IT技能")) {
//                        tablesWithLabel.put(TableTitleFor51.ITSkills, table);
//                    } else {
//                        //						throw new SomethingUnknownException();
//                        //						System.out.println("没探测出来这是啥内容....=.=");
//                        //						System.out.println("************************");
//                        //						System.out.println(text);
//                        //						System.out.println("************************");
//                    }
//                }
//            }
//        }
//    }
//
//    void basicInformationDetect(Element table) {
//        String categoryName = "基本信息";
//        Elements trs = table.select("tr");
//        Element basicTr = trs.get(0);
//        String[] infos = basicTr.text().split("\\|");
//        List<String> jsonArray = new ArrayList<String>();
//        for (String tmp : infos) {
//            if (tmp.equals("年")) {
//                jsonArray.add(json("经验年限", tmp));
//            } else if (tmp.equals("男") || tmp.equals("女") || tmp.equals("保密")) {
//                jsonArray.add(json("性别", tmp));
//            } else if (tmp.equals("岁")) {
//                if (tmp.contains("（") && tmp.contains("）")) {
//                    String birthday = tmp.substring(tmp.indexOf("（"), tmp.indexOf("）"));
//                    String age = tmp.substring(0, tmp.indexOf("岁"));
//                    jsonArray.add(json("年龄", age));
//                    jsonArray.add(json("出生年月", birthday));
//                }
//            } else if (tmp.contains("婚")) {
//                jsonArray.add(json("婚姻状况", tmp));
//            } else if (tmp.contains("cm")) {
//                jsonArray.add(json("身高", tmp));
//            } else if (tmp.contains("ID:")) {
//                String id = tmp.substring(tmp.lastIndexOf(":") + 1, tmp.lastIndexOf("") - 1);
//                partJsons.add(json("id", id));
//            }
//        }
//        Element otherTr = trs.get(1);
//        Elements others = otherTr.select("td");
//        if (others.size() > 0) {
//            int i = 1;
//            String tmpText = "";
//            for (Element e : others) {
//                String value = e.text().replace("　", "");
//                if (i % 2 != 0) {
//                    tmpText = value;
//                } else {
//                    if (tmpText.length() > 1 && value.length() > 1) {
//                        jsonArray.add(json(tmpText, value));
//                    }
//                }
//                i++;
//            }
//        }
//        String partJson = json(categoryName, jsonArray);
//        partJsons.add(partJson);
//    }
//
//    private void lastestWorkDetect(Element table) {
//        String categoryName = "最近工作";
//        List<String> jsonArray = new ArrayList<String>();
//        for (Element tr : table.select("tr")) {
//            Elements tds = tr.select("td");
//            if (tds.size() == 1) {
//                Element td = tds.get(0);
//                Elements spans = td.select("span");
//                if (spans.size() == 2) {
//                    Element tmp = td.select("span").get(1);
//                    if (tmp != null) {
//                        String range = tmp.text();
//                        jsonArray.add(json("工作时间", range));
//                    }
//                }
//            } else {
//                if (tds.size() > 0) {
//                    int i = 1;
//                    String tmpText = "";
//                    for (Element e : tds) {
//                        String value = e.text().replace("　", "").replace("：", "").replace("[", "")
//                                .replace("]", "");
//                        if (i % 2 != 0) {
//                            tmpText = value;
//                        } else {
//                            jsonArray.add(json(tmpText, value));
//                        }
//                        i++;
//                    }
//                }
//            }
//        }
//        String partJson = json(categoryName, jsonArray);
//        partJsons.add(partJson);
//    }
//
//    private void lastestEducation(Element table) {
//        String categoryName = "最高学历";
//        List<String> jsonArray = new ArrayList<String>();
//        for (Element tr : table.select("tr")) {
//            Elements tds = tr.select("td");
//            if (tds.size() == 1) {} else {
//                if (tds.size() > 0) {
//                    int i = 1;
//                    String tmpText = "";
//                    for (Element e : tds) {
//                        String value = e.text().replace("　", "").replace("：", "").replace("[", "")
//                                .replace("]", "");
//                        if (i % 2 != 0) {
//                            tmpText = value;
//                        } else {
//                            jsonArray.add(json(tmpText, value));
//                        }
//                        i++;
//                    }
//                }
//            }
//        }
//        String partJson = json(categoryName, jsonArray);
//        partJsons.add(partJson);
//    }
//
//    private void currentSalaryDetect(Element table) {
//        String categoryName = "当前工资";
//        List<String> jsonArray = new ArrayList<String>();
//        for (Element tr : table.select("tr")) {
//            Elements tds = tr.select("td");
//            if (tds.size() == 1) {} else {
//                if (tds.size() > 0) {
//                    int i = 1;
//                    String tmpText = "";
//                    for (Element e : tds) {
//                        String value = e.text().replace("　", "").replace("：", "").replace("[", "")
//                                .replace("]", "");
//                        if (value.length() > 0) {
//                            if (i % 2 != 0) {
//                                tmpText = value;
//                            } else {
//                                jsonArray.add(json(tmpText, value));
//                            }
//                            i++;
//                        }
//                    }
//                }
//            }
//        }
//        String partJson = json(categoryName, jsonArray);
//        partJsons.add(partJson);
//    }
//
//    private void selfEvaluationDetect(Element table) {
//        String categoryName = "自我评价";
//        String s = "";
//        for (Element td : table.select("td")) {
//            String text = td.text();
//            if (text.trim().length() > 0) {
//                if (!text.equals("自我评价")) {
//                    s += text;
//                }
//            }
//        }
//        partJsons.add(json(categoryName, s));
//    }
//
//    private void desiredPositionDetect(Element table) {
//        String categoryName = "求职意向";
//        List<String> jsonArray = new ArrayList<String>();
//        for (Element td : table.select("td")) {
//            String raw = td.text().replace("\"", "");
//            if (raw.contains("：")) {
//                String[] raws = raw.split("：");
//                if (raws.length == 2) {
//                    jsonArray.add(json(raws[0], raws[1]));
//                }
//            }
//        }
//        String partJson = json(categoryName, jsonArray);
//        partJsons.add(partJson);
//    }
//
//    private void educationDetect(Element table) {
//        String categoryName = "教育经历";
//        List<String> jsonArray = new ArrayList<String>();
//        for (Element tr : table.select("tr")) {
//            Elements tds = tr.select("td");
//            if (tds.size() == 4) {
//                String range = tds.get(0).text();
//                jsonArray.add(json("学习时间", range));
//                String university = tds.get(1).text();
//                jsonArray.add(json("学校", university));
//                String pro = tds.get(2).text();
//                jsonArray.add(json("专业", pro));
//                String level = tds.get(3).text();
//                jsonArray.add(json("学历", level));
//            }
//        }
//        String partJson = json(categoryName, jsonArray);
//        partJsons.add(partJson);
//    }
//
//    private void workExpriencesDetect(Element table) {
//        String categoryName = "工作经验";
//        List<List<String>> jsonArray = new ArrayList<List<String>>();
//        Elements trs = table.select("tr");
//        List<String> subJsonArray = null;
//        for (int index = 0; index < trs.size(); index++) {//保证顺序读取
//            Elements tds = trs.get(index).select("td");
//            Elements spans = trs.get(index).select("span");
//            if (tds.size() == 1 && spans.size() == 1) {
//                if (subJsonArray != null) {
//                    jsonArray.add(subJsonArray);
//                }
//                subJsonArray = new ArrayList<String>();
//                String rawText = tds.text();
//                if (rawText.contains("[") && rawText.contains("]")) {
//                    String range = rawText
//                            .substring(rawText.indexOf("[") + 1, rawText.indexOf("]"));
//                    range = range.replace(" ", "");
//                    String timeAndCompany = rawText.substring(0, rawText.indexOf("["));
//                    String[] tmpArray = timeAndCompany.split("：");
//                    if (tmpArray.length == 2) {
//                        String timeRange = tmpArray[0].replace(" ", "");
//                        String company = tmpArray[1];
//                        subJsonArray.add(json("工作时间", timeRange));
//                        if (company.contains("(") && company.contains(")")) {
//                            String size = company.substring(company.lastIndexOf("(") + 1,
//                                    company.lastIndexOf(")"));
//                            String companyName = company.substring(0, company.lastIndexOf("("));
//                            subJsonArray.add(json("工作单位", companyName));
//                            subJsonArray.add(json("单位规模", size));
//                        } else {
//                            subJsonArray.add(json("工作单位", company));
//                        }
//                    }
//                    subJsonArray.add(json("工作年限", range));
//                } else {
//                    System.out.println("这行不是工作经验第一行");
//                    continue;
//                }
//            } else {
//                if (subJsonArray == null) {
//                    System.out.println("没有识别该条对应的工作经验第一行");
//                    continue;
//                } else {
//                    if (tds.size() == 2) {
//                        int i = 1;
//                        String tmpText = "";
//                        for (Element e : tds) {
//                            String value = e.text().replace("　", "").replace("：", "")
//                                    .replace("[", "").replace("]", "");
//                            if (value.length() > 0) {
//                                if (i % 2 != 0) {
//                                    tmpText = value;
//                                } else {
//                                    subJsonArray.add(json(tmpText, value));
//                                }
//                                i++;
//                            }
//                        }
//                    } else if (tds.size() == 1) {
//                        String text = tds.text();
//                        subJsonArray.add(json("工作描述", text));
//                    }
//                }
//            }
//        }
//        jsonArray.add(subJsonArray);
//        String partJson = json(jsonArray, categoryName);
//        partJsons.add(partJson);
//    }
//
//    private void trainExpriencesDetect(Element table) {
//        String categoryName = "培训经历";
//        List<List<String>> jsonArray = new ArrayList<List<String>>();
//        Elements trs = table.select("tr");
//        List<String> subJsonArray = null;
//        for (int index = 0; index < trs.size(); index++) {//保证顺序读取
//            Elements tds = trs.get(index).select("td");
//            if (tds.size() == 4) {
//                if (subJsonArray != null) {
//                    jsonArray.add(subJsonArray);
//                }
//                subJsonArray = new ArrayList<String>();
//                String time = tds.get(0).text().replace(" ", "");
//                String unit = tds.get(1).text().replace(" ", "");
//                String kecheng = tds.get(2).text().replace(" ", "");
//                String zhengshu = tds.get(3).text().replace(" ", "");
//                subJsonArray.add(json("培训时间", time));
//                subJsonArray.add(json("培训单位", unit));
//                if (kecheng.length() > 0) subJsonArray.add(json("培训课程", kecheng));
//                if (zhengshu.length() > 0) subJsonArray.add(json("培训证书", zhengshu));
//            } else if (tds.size() == 1) {
//                String content = tds.text().replace(" ", "");
//                if (content.length() > 0) subJsonArray.add(json("培训内容", content));
//            }
//        }
//        jsonArray.add(subJsonArray);
//        String partJson = json(jsonArray, categoryName);
//        partJsons.add(partJson);
//    }
//
//    private void projectExpriencesDetect(Element table) {
//        String categoryName = "项目经验";
//        List<List<String>> jsonArray = new ArrayList<List<String>>();
//        Elements trs = table.select("tr");
//        List<String> subJsonArray = null;
//        for (int index = 0; index < trs.size(); index++) {//保证顺序读取
//            Elements tds = trs.get(index).select("td");
//            if (tds.size() == 1) {
//                if (subJsonArray != null) {
//                    jsonArray.add(subJsonArray);
//                }
//                subJsonArray = new ArrayList<String>();
//                String rawText = tds.text();
//                String[] tmpArray = rawText.split("：");
//                if (tmpArray.length == 2) {
//                    String time = tmpArray[0];
//                    String project = tmpArray[1];
//                    subJsonArray.add(json("项目时间", time));
//                    subJsonArray.add(json("项目名称", project));
//                }
//            } else {
//                if (subJsonArray == null) {
//                    System.out.println("没有识别出来项目经验中的第一行.");
//                    continue;
//                } else {
//                    if (tds.size() == 2) {
//                        String key = tds.get(0).text().replace("：", "");
//                        String value = tds.get(1).text();
//                        subJsonArray.add(json(key, value));
//                    }
//                }
//            }
//        }
//        jsonArray.add(subJsonArray);
//        String partJson = json(jsonArray, categoryName);
//        partJsons.add(partJson);
//    }
//
//    private void languageDetect(Element table) {
//        String categoryName = "语言能力";
//        List<String> jsonArray = new ArrayList<String>();
//        String subCategoryName = "语言列表";
//        List<List<String>> languageArray = new ArrayList<List<String>>();
//        List<String> subJsonArray = null;
//        for (Element tr : table.select("tr")) {
//            Elements tds = tr.select("td");
//            String rawText = tds.text();
//            if (rawText.contains("（") && rawText.contains("）")) {
//                if (subJsonArray != null) {
//                    languageArray.add(subJsonArray);
//                }
//                subJsonArray = new ArrayList<String>();
//                String languageLevel = tds.get(0).text();
//                if (languageLevel.contains("（") && languageLevel.contains("）")) {
//                    String language = languageLevel.substring(0, languageLevel.indexOf("（"));
//                    String level = languageLevel.substring(languageLevel.indexOf("（") + 1,
//                            languageLevel.indexOf("）"));
//                    subJsonArray.add(json("语言", language));
//                    subJsonArray.add(json("能力", level));
//                }
//                String detailsLevel = tds.get(1).text();
//                if (detailsLevel.contains("，")) {
//                    String[] groups = detailsLevel.split("，");
//                    for (String group : groups) {
//                        if (group.contains("（") && group.contains("）")) {
//                            String feature = group.substring(0, group.indexOf("（"));
//                            String level = group.substring(group.indexOf("（") + 1,
//                                    group.indexOf("）"));
//                            subJsonArray.add(json(feature, level));
//                        }
//                    }
//                }
//            } else {
//                String key = tds.get(0).text().replace("：", "");
//                String value = tds.get(1).text();
//                jsonArray.add(json(key, value));
//            }
//        }
//        languageArray.add(subJsonArray);
//        String languageJson = json(languageArray, subCategoryName);
//        jsonArray.add(languageJson);
//        String partJson = json(categoryName, jsonArray);
//        partJsons.add(partJson);
//    }
//
//    private void iTSkillsDetect(Element table) {
//        String categoryName = "IT技能";
//        List<List<String>> languageArray = new ArrayList<List<String>>();
//        for (Element tr : table.select("tr")) {
//            Elements tds = tr.select("td");
//            String text = tds.text();
//            if (text.contains("技能名称") && text.contains("熟练程度")) {
//                continue;
//            } else {
//                if (tds.size() == 3) {
//                    List<String> subLanguage = new ArrayList<String>();
//                    String name = tds.get(0).text();
//                    String level = tds.get(1).text();
//                    String last = tds.get(2).text();
//                    subLanguage.add(json("技能名称", name));
//                    subLanguage.add(json("熟练程度", level));
//                    subLanguage.add(json("使用时间", last));
//                    languageArray.add(subLanguage);
//                }
//            }
//        }
//        String partJson = json(languageArray, categoryName);
//        partJsons.add(partJson);
//    }
//
//    private void papersDetect(Element table) {
//        String categoryName = "证书";
//        List<List<String>> languageArray = new ArrayList<List<String>>();
//        for (Element tr : table.select("tr")) {
//            Elements tds = tr.select("td");
//            if (tds.size() == 3) {
//                List<String> subLanguage = new ArrayList<String>();
//                String name = tds.get(0).text();
//                String level = tds.get(1).text();
//                String last = tds.get(2).text();
//                subLanguage.add(json("获得时间", name));
//                subLanguage.add(json("证书名称", level));
//                if (last.length() > 0) subLanguage.add(json("证书等级", last));
//                languageArray.add(subLanguage);
//            }
//        }
//        String partJson = json(languageArray, categoryName);
//        partJsons.add(partJson);
//    }
//
//    private void scoresDetect(Element table) {
//        String categoryName = "所获奖项";
//        List<List<String>> languageArray = new ArrayList<List<String>>();
//        for (Element tr : table.select("tr")) {
//            Elements tds = tr.select("td");
//            if (tds.size() == 3) {
//                List<String> subLanguage = new ArrayList<String>();
//                String name = tds.get(0).text();
//                String level = tds.get(1).text();
//                String desc = tds.get(2).text();
//                subLanguage.add(json("获得时间", name));
//                subLanguage.add(json("奖项名称", level));
//                if (desc.length() > 0) subLanguage.add(json("奖项说明", desc));
//                languageArray.add(subLanguage);
//            }
//        }
//        String partJson = json(languageArray, categoryName);
//        partJsons.add(partJson);
//    }
//
//    private void othersDetect(Element table) {
//        String categoryName = "其他信息";
//        List<List<String>> languageArray = new ArrayList<List<String>>();
//        for (Element tr : table.select("tr")) {
//            Elements tds = tr.select("td");
//            if (tds.size() == 2) {
//                List<String> subLanguage = new ArrayList<String>();
//                String key = tds.get(0).text();
//                String value = tds.get(1).text();
//                if (key.length() > 0 && value.length() > 0) {
//                    subLanguage.add(json(key, value));
//                    languageArray.add(subLanguage);
//                }
//            }
//        }
//        String partJson = json(languageArray, categoryName);
//        partJsons.add(partJson);
//    }
//
//    private void tableDetect() {
//        for (Entry<TableTitleFor51, Element> entry : tablesWithLabel.entrySet()) {
//            TableTitleFor51 key = entry.getKey();
//            Element table = entry.getValue();
//            switch (key) {
//                case BasicInformation:
//                    basicInformationDetect(table);
//                    break;
//                case LastestWork:
//                    lastestWorkDetect(table);
//                    break;
//                case LastestEducation:
//                    lastestEducation(table);
//                    break;
//                case CurrentSalary:
//                    currentSalaryDetect(table);
//                    break;
//                case SelfEvaluation:
//                    selfEvaluationDetect(table);
//                    break;
//                case DesiredPosition:
//                    desiredPositionDetect(table);
//                    break;
//                case WorkExpriences:
//                    workExpriencesDetect(table);
//                    break;
//                case Education:
//                    educationDetect(table);
//                    break;
//                case TrainExpriences:
//                    trainExpriencesDetect(table);
//                    break;
//                case ProjectExpriences:
//                    projectExpriencesDetect(table);
//                    break;
//                case Language:
//                    languageDetect(table);
//                    break;
//                case Scores:
//                    scoresDetect(table);
//                    break;
//                case Papers:
//                    papersDetect(table);
//                    break;
//                case SocietyExpriences:
//                    break;
//                case ITSkills:
//                    iTSkillsDetect(table);
//                    break;
//                case Others:
//                    othersDetect(table);
//            }
//        }
//
//    }
//
//    public List<Element> preprocessing() {
//        Elements root = doc.select("#divResume");
//        for (Element tr : root.select("tr")) {//去掉无用tr
//            if (tr.text().isEmpty()) {
//                tr.remove();
//            }
//        }
//        for (Element table : root.select("table")) {
//            if (table.select("table").size() == 1) {
//                if (table.text().trim().length() > 0) {
//                    tables.add(table);
//                }
//            }
//        }
//        detectIdOrNameFromCV(root);
//        return tables;
//    }
//
//    /**
//     * 有头的探测出名字，无头的探测出id
//     */
//    private void detectIdOrNameFromCV(Elements root) {
//        Element select1 = root.select("table").first();
//        if (select1 == null) {
//            throw new SomethingUnknownException();
//        }
//        Elements select2 = select1.select("table[width=97%]");
//        String idValue = "";
//        String nameValue = "";
//        for (Element table : select2) {
//            if (idValue.length() != 0 || nameValue.length() != 0) {
//                break;
//            }
//            Element tr = table.select("table>tbody>tr").first();
//            if (tr == null) {
//                continue;
//            }
//            Elements spans = tr.select("span");
//            for (Element span : spans) {
//                Elements bs = span.select("b");
//                if (bs.size() != 1) {
//                    continue;
//                }
//                String text = bs.text();
//                if (text.trim().length() == 0 || text.trim().contains("标签")) {
//                    continue;
//                }
//                if (text.contains(":")) {
//                    String[] ids = text.split(":");
//                    if (ids.length == 2) {
//                        idValue = ids[1];
//                        partJsons.add(json("id", idValue));
//                    }
//                } else {
//                    nameValue = text;
//                    partJsons.add(json("name", nameValue));
//                }
//            }
//        }
//    }
//
//    @Override
//    public String outputJson() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("{");
//        int index = 0;
//        for (String part : partJsons) {
//            sb.append(part);
//            index++;
//            if (index != partJsons.size()) {
//                sb.append(",");
//            }
//        }
//        sb.append("}");
//        String json = sb.toString();
//        if (json.length() < 20) {
//            throw new SomethingUnknownException();
//        }
//        return sb.toString();
//    }
//
//    private String json(String name, String value) {
//        if (value.contains("\"")) {
//            value.replaceAll("\"", "\\\"");
//        }
//        return "\"" + name + "\":\"" + value + "\"";
//    }
//
//    private String json(List<List<String>> array, String name) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("\"" + name + "\":");
//        sb.append("[");
//        int index = 0;
//        for (List<String> jsonArray : array) {
//            sb.append(json(jsonArray));
//            index++;
//            if (index != array.size()) {
//                sb.append(",");
//            }
//        }
//        sb.append("]");
//        return sb.toString();
//    }
//
//    private String json(List<String> jsonArray) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("{");
//        int index = 0;
//        if (jsonArray != null && jsonArray.size() > 0) {
//            for (String tmp : jsonArray) {
//                sb.append(tmp);
//                index++;
//                if (index != jsonArray.size()) {
//                    sb.append(",");
//                }
//            }
//        }
//        sb.append("}");
//        return sb.toString();
//    }
//
//    private String json(String name, List<String> jsonArray) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("\"" + name + "\":");
//        sb.append(json(jsonArray));
//        return sb.toString();
//    }
//}
