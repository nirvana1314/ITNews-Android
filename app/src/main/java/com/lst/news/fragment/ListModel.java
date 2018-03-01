package com.lst.news.fragment;

import java.io.Serializable;

/**
 * Created by lisongtao on 2018/2/26.
 */

public class ListModel implements Serializable {
    private String cid;
    private String title;
    private String thumb;
    private String cate_id;
    private String cate_name;
    private String text;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    @Override
    public String toString() {
        return "ListModel{" +
                "cid='" + cid + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", cate_id='" + cate_id + '\'' +
                ", cate_name='" + cate_name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}