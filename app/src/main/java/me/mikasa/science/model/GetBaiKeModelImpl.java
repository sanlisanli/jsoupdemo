package me.mikasa.science.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.mikasa.science.bean.BaiKeItem;
import me.mikasa.science.contract.GetBaiKe;

import static me.mikasa.science.constants.Constant.BaiKeItemTitles;
/**
 * Created by mikasacos on 2018/9/24.
 */

public class GetBaiKeModelImpl implements GetBaiKe.Model {
    private GetBaiKe.Presenter mPresenter;
    public GetBaiKeModelImpl(GetBaiKe.Presenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public void getBaiKe(Context context) {
        List<BaiKeItem>beanList=new ArrayList<>(BaiKeItemTitles.length);
        String[] titles=BaiKeItemTitles;
        List<Bitmap>imgs=getBaiKeBitmapFromAssets(context);
        if (titles.length==imgs.size()){
            for (int i=0;i<BaiKeItemTitles.length;i++){
                BaiKeItem bean=new BaiKeItem();
                bean.setImg(imgs.get(i));
                bean.setTitle(titles[i]);
                beanList.add(bean);
            }
        }else {
            Log.i("op","title与img数目不相等");
        }
        Collections.shuffle(beanList);
        mPresenter.getBaiKeSuccess(beanList);
    }

    private List<Bitmap> getBaiKeBitmapFromAssets(Context context){
        AssetManager am=context.getAssets();
        List<String>bitmapPaths=new ArrayList<>();
        try {
            String[] paths=am.list("");
            for (String path:paths){
                if (path.endsWith(".jpg")){
                    bitmapPaths.add(path);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        List<Bitmap>bitmaps=new ArrayList<>(bitmapPaths.size());
        //
        try {
            for (String path:bitmapPaths){
                InputStream is=am.open(path);
                Bitmap bitmap= BitmapFactory.decodeStream(is);
                bitmaps.add(bitmap);
                is.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return bitmaps;
    }
}
