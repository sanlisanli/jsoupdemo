package me.mikasa.science.bean;

import java.io.Serializable;

/**
 * Created by mikasacos on 2018/9/21.
 */

public class Book implements Serializable {
    private String title;
    private String imgUrl;
    private String link;
    private String desc;
    private String price;
    private String author;
    public void setTitle(String s){
        this.title=s;
    }
    public String getTitle(){
        return title;
    }
    public void setImgUrl(String s){
        this.imgUrl=s;
    }
    public String getImgUrl(){
        return imgUrl;
    }
    public void setLink(String s){
        this.link=s;
    }
    public String getLink(){
        return link;
    }
    public void setDesc(String s){
        this.desc=s;
    }
    public String getDesc(){
        return desc;
    }
    public void setPrice(String s){
        this.price=s;
    }
    public String getPrice(){
        return price;
    }
    public void setAuthor(String s){
        this.author=s;
    }
    public String getAuthor(){
        return author;
    }
}
