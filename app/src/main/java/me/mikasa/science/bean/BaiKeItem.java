package me.mikasa.science.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by mikasacos on 2018/9/24.
 */

public class BaiKeItem implements Serializable {
    private Bitmap img;
    private String title;
    private String link;
    public void setImg(Bitmap bitmap){
        this.img=bitmap;
    }
    public Bitmap getImg(){
        return img;
    }
    public void setTitle(String s){
        this.title=s;
    }
    public String getTitle(){
        return title;
    }
    public void setLink(String s){
        this.link=s;
    }
    public String getLink(){
        return link;
    }
}
