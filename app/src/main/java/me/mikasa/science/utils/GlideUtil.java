package me.mikasa.science.utils;

import android.content.Context;
import android.os.Environment;
import android.support.design.widget.Snackbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import me.mikasa.science.R;
import me.mikasa.science.listener.DownloadListener;

/**
 * Created by mikasacos on 2018/9/30.
 */

public class GlideUtil {
    private Context mContext;
    private DownloadListener mListener;
    public static String FILEPATH;
    public GlideUtil(Context context,DownloadListener listener){
        this.mContext=context;
        this.mListener=listener;
    }
    public void downloadBitmap(String url){
        Glide.with(mContext).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    saveBitmap(String.valueOf(new Date().getTime()),resource);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void saveBitmap(String imgName,byte[] bytes){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            FileOutputStream fos=null;
            try {
                FILEPATH=Environment.getExternalStorageDirectory().getCanonicalPath()+
                        "/Science"+"/downloadPicture";
                File imgDir=new File(FILEPATH);
                if (!imgDir.exists()){
                    imgDir.mkdirs();
                }
                imgName=FILEPATH+"/"+imgName+".jpg";
                fos=new FileOutputStream(imgName);//??
                fos.write(bytes);
                if (mListener!=null){
                    mListener.downloadSuccess();
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if (fos!=null){
                        fos.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
            if (mListener!=null){
                mListener.downloadFailed();
            }
        }
    }
}
