# jsoupdemo
   
  
<a href="https://github.com/sanlisanlisanli/jsoupdemo/tree/master/apk">apk下载</a>    
  
要知道每一个资源丰富的app背后都有一个庞大的数据库作为内容支撑的，那是不是意味着没有强大团队的的我们就与开发资源精彩丰富的app无缘了呢   
  
并不是，别忘了我们还有取之不尽用之不绝的资源宝库--互联网  
  
我们可以使用爬虫为我们的app提供内容支撑  
  
提到爬虫，就不得不提专为java打造的爬虫利器--jsoup了  
  
看完utils包中的jsoupUtil，包你学会最基本爬虫技巧，不会你打我  
  
一、构造Javabean    
  
可以说每一个recyclerview list 的child 都是一个Javabean类型的数据封装集  
  
我们先建立一个叫做TianWen(天文)的javabean  
  
public TianWen{  //既然是爬HTMl网页，那么我们爬到的数据一般都是String型   
private String title;    
private String imgUrl;   
private String content;    
private String link;   
//public setter getter方法，一定要是public的    
  
二、分析网页  
  
以 http://www.astron.ac.cn/list-2-1.htm 为例，光标移动到第一篇文章的标题  “宇宙团状物质以三分之一光速的速度被吸入黑洞中”  处，   
  
<img src="https://github.com/sanlisanlisanli/jsoupdemo/blob/master/screenshots/QQ%E6%B5%8F%E8%A7%88%E5%99%A8%E6%88%AA%E5%9B%BE20181001024600.png"/>  
  
右键鼠标，点击检查  
  
然后就可以看到整个document tree 了  
  
<img src="https://github.com/sanlisanlisanli/jsoupdemo/blob/master/screenshots/QQ%E6%B5%8F%E8%A7%88%E5%99%A8%E6%88%AA%E5%9B%BE20181001022941.png"/>     
  
这个网页的结构可是说是非常简单明了，document tree 的结构一目了然    
       
我们要爬的就是所有 li 标签的标题text，图片src，内容p和跳转链接href  
  
三、依据document tree的结构一一爬取我们想要的文本text，图片链接src和跳转链接href（title,img,content,link）  
  
<img src="https://github.com/sanlisanlisanli/jsoupdemo/blob/master/screenshots/3.png"/>       
    
private static String TianZhiWenHead="http://www.astron.ac.cn/";   
public List<TianWen>getTianWen(String s){//s为网页url  
        List<TianWen>beanList=new ArrayList<>();  
        Document doc=Jsoup.parse(s);  
        Element e=doc.select("ul.newslist_pic").first();  
        for (Element ee:e.children())    
      
e.children()就是 ul 标签下所有的 li 标签，注意用.children的时候，  
要注意辨别是不是所有的child都是你想要爬的内容，容易空指针   
    
    {   
            TianWen bean=new TianWen();  
            bean.setTitle(ee.child(0).attr("title"));//setTitle  
            bean.setImgUrl(ee.child(0).child(0).attr("src"));//setImgUrl  
            bean.setContent(ee.child(1).child(1).text());//setContent   
            final String link=TianZhiWenHead+ee.child(0).attr("href");    
            bean.setLink(link);//setLink    
            beanList.add(bean);   
        }   
        return beanList;//这个beanList就是recyclerview需要的dataList   
    }   
     
       
四、绑定数据和recyclerview   
  
有了dataList,再需要一个适配器adapter,就可以把dataList和recyclerview绑定到一起   
  
关于recyclerview的baseAdapter，github上有很多，这里推荐两个  
  
<a href="https://github.com/CymChad/BaseRecyclerViewAdapterHelper" target="_blank">BaseRecyclerViewAdapterHelper</a>  
<a href="https://github.com/hongyangAndroid/baseAdapter" target="_blank">baseAdapter</a>  
   
五、本项目截图  
  
<img src="https://github.com/sanlisanlisanli/jsoupdemo/blob/master/screenshots/Screenshot_20181001-035002.jpg" width="260" height="480"/>   

<img src="https://github.com/sanlisanlisanli/jsoupdemo/blob/master/screenshots/Screenshot_20181001-035009.jpg" width="260" height="480"/>     
  
   
<img src="https://github.com/sanlisanlisanli/jsoupdemo/blob/master/screenshots/Screenshot_20181001-035023.jpg" width="260" height="480"/>     
    
       
<img src="https://github.com/sanlisanlisanli/jsoupdemo/blob/master/screenshots/Screenshot_20181001-035035.jpg" width="260" height="480"/>  
  
<img src="https://github.com/sanlisanlisanli/jsoupdemo/blob/master/screenshots/Screenshot_20181001-035042.jpg" width="260" height="480"/>
  
     
六、拥抱开源  
  
glide   
jsoup   
okhttp   
xrecyclerview   
ByeBurger   
circleimageview   
  
.
