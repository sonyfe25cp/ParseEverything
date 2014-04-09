package com.parseeverything.result;

import com.parseeverything.utils.NewsProvider;


/**
 * 
 * @author ChenJie
 * @date 7 Mar, 2014
 */
public class NewsModel extends HtmlModel {

    private int id;

    private String publishDate;

    private String title;//标题

    private String content;//解析后的正文

    private String contentHtml;//带格式的正文

    private String url;

    private String html;//html原文
    
    private NewsProvider provider;

	public NewsModel(String url, NewsProvider provider) {
		super();
		this.url = url;
		this.provider = provider;
	}

	public NewsModel() {
		super();
	}
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("publishDate:");
        sb.append(publishDate);
        sb.append("\n");
        sb.append("title:");
        sb.append(title);
        sb.append("\n");
//        sb.append("content:");
//        sb.append(content);
        sb.append("\n");
//        sb.append("contentHtml:");
//        sb.append(contentHtml);
        sb.append("\n");
        sb.append("url:");
        sb.append(url);
        sb.append("\n");
        sb.append(provider.toString());
        sb.append("\n");
        //        sb.append("html:");
        //        sb.append(html);
        //        sb.append("\n");
        return sb.toString();

    }


    public String getPublishDate() {
		return publishDate;
	}


	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}


	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

	public NewsProvider getProvider() {
		return provider;
	}

	public void setProvider(NewsProvider provider) {
		this.provider = provider;
	}

}
