package me.mikasa.science.utils;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.mikasa.science.bean.BaiKeItem;
import me.mikasa.science.bean.Book;
import me.mikasa.science.bean.GalleryItem;
import me.mikasa.science.bean.GuoKe;
import me.mikasa.science.bean.SongShu;
import me.mikasa.science.bean.TianWen;

/**
 * Created by mikasacos on 2018/9/8.
 */

public class JsoupUtil {
    private static JsoupUtil sJsoupUtil;
    public static JsoupUtil getInstance(){
        if (sJsoupUtil ==null){
            synchronized (JsoupUtil.class){
                if (sJsoupUtil ==null){
                    sJsoupUtil =new JsoupUtil();
                }
            }
        }
        return sJsoupUtil;
    }
    private static String TianZhiWenHead="http://www.astron.ac.cn/";

    public List<SongShu>getSongShu(String s){
        List<SongShu>beanList=new ArrayList<>();
        Document doc=Jsoup.parse(s);
        Element e=doc.select("div.brright").first();
        Elements es=e.children();
        for (int i=1;i<es.size()-1;i++){
            Element ee=es.get(i);
            SongShu bean=new SongShu();
            bean.setLink(ee.child(1).child(0).child(0).attr("href"));
            bean.setImgUrl(ee.child(0).child(0).child(0).attr("src"));
            bean.setTitle(ee.child(1).child(0).child(0).text());
            bean.setAuthor(ee.child(1).child(1).child(1).text());
            bean.setCategory(ee.child(1).child(1).child(0).text());
            bean.setDesc(ee.child(1).child(2).child(1).text());//.text()
            bean.setComment(ee.child(1).child(3).text());
            beanList.add(bean);
        }
        return beanList;
    }
    public List<GuoKe>getGuoKe(String s){
        List<GuoKe>beanList=new ArrayList<>();
        Document doc=Jsoup.parse(s);
        Element e=doc.getElementById("waterfall");
        Elements es=e.select("div.article");
        Log.i("oputil",String.valueOf(es.size()));
        for (Element ee:es){
            GuoKe bean=new GuoKe();
            if (!TextUtils.isEmpty(ee.child(0).attr("href"))){
                //存在label
                bean.setLabel(ee.child(0).text());
                bean.setTitle(ee.child(1).text());
                bean.setAuthor(ee.child(2).child(0).text());
                bean.setComment(ee.child(2).child(2).text());
                bean.setImgUrl(ee.child(3).child(0).attr("src"));
                bean.setDesc(ee.child(4).text());
                bean.setLink(ee.child(1).child(0).attr("href"));
            }else {
                bean.setTitle(ee.child(0).text());
                bean.setAuthor(ee.child(1).child(0).text());
                bean.setComment(ee.child(1).child(2).text());
                bean.setImgUrl(ee.child(2).child(0).attr("src"));
                bean.setDesc(ee.child(3).text());
                bean.setLink(ee.child(0).child(0).attr("href"));
            }
            beanList.add(bean);
        }
        return beanList;
    }
    public List<TianWen>getDogStar(String s){
        List<TianWen>beanList=new ArrayList<>();
        Document doc=Jsoup.parse(s);
        Element e=doc.select("div.right-block").first();
        Elements es=e.select("div.panel-middle").select("div.panel-shadow");
        for (Element ee:es){
            TianWen bean=new TianWen();
            Element ee1=ee.child(1);//article-box
            Element ee2=ee1.child(2);//p
            bean.setDes(ee2.text());
            bean.setTitle(ee1.child(0).text());
            //Element ee3=ee1.getElementsByAttribute("href").first();
            //String str=getDogStarPic("http://www.dogstar.net"+ee1.child(0).child(0).child(0).attr("href"));
            //bean.setPicUrl(str);
            bean.setPicUrl(ee.child(0).child(1).child(0).attr("src"));
            beanList.add(bean);
        }
        return beanList;
    }
    public List<TianWen>getNasaChina(String s){
        List<TianWen>beanList=new ArrayList<>();
        Document doc=Jsoup.parse(s);
        Element e=doc.select("div.content-area").first().child(0);
        Elements es=e.select("div.post-entry");
        for (Element ee:es){
            TianWen bean=new TianWen();
            bean.setPicUrl(ee.child(0).child(0).child(1).attr("src"));
            Log.i("op",ee.child(0).child(0).child(1).attr("src"));
            bean.setTitle(ee.child(1).child(0).text());
            bean.setDes(ee.child(3).text());
            beanList.add(bean);
        }
        return beanList;
    }
    public List<TianWen>getTianWen(String s){
        List<TianWen>beanList=new ArrayList<>();
        Document doc=Jsoup.parse(s);
        Element e=doc.select("ul.newslist_pic").first();
        Log.i("op",String.valueOf(e.children()));
        for (Element ee:e.children()){//ee-->li
            TianWen bean=new TianWen();
            bean.setTitle(ee.child(0).attr("title"));
            bean.setPicUrl(ee.child(0).child(0).attr("src"));
            bean.setDes(ee.child(1).child(1).text());
            String date=ee.child(1).child(0).child(0).text();
            bean.setDate(date.substring(1,date.length()-1));//??
            final String link=TianZhiWenHead+ee.child(0).attr("href");
            Log.i("opjsoup",link);
            bean.setLink(link);
            beanList.add(bean);
        }
        return beanList;
    }
    public List<GalleryItem>getTianWenStar(String s){
        List<GalleryItem>beanList=new ArrayList<>();
        Document doc=Jsoup.parse(s);
        Element e=doc.select("div.m_c").first().child(0);
        for (Element ee:e.children()){
            GalleryItem bean=new GalleryItem();
            bean.setPicUrl(ee.child(0).child(0).attr("src"));
            bean.setTitle(ee.child(1).child(0).attr("title"));
            bean.setLink(TianZhiWenHead+ee.child(0).attr("href"));
            beanList.add(bean);
        }
        return beanList;
    }
    public List<Book>getDangDangBook(String s,int multi){
        if (multi>3&&multi<1){
            return null;
        }
        List<Book>beanList=new ArrayList<>();
        Document doc=Jsoup.parse(s);
        Element e=doc.getElementById("search_nature_rg");
        Elements es=e.child(0).children();
        for (int i=20*(multi-1);i<20*multi;i++){
            Book bean=new Book();
            Element ee=es.get(i);
            bean.setTitle(ee.child(0).attr("title"));
            bean.setLink(ee.child(0).attr("href"));
            bean.setDesc(ee.child(2).text());
            if (ee.child(0).child(0).attr("data-original").length()==0){
                bean.setImgUrl(ee.child(0).child(0).attr("src"));
            }else {
                bean.setImgUrl(ee.child(0).child(0).attr("data-original"));
            }
            bean.setPrice(ee.child(3).child(0).text());
            bean.setAuthor(ee.child(7).text());
            beanList.add(bean);
        }
        return beanList;
    }
}
