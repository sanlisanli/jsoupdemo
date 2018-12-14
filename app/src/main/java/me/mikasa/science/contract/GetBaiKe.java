package me.mikasa.science.contract;

import android.content.Context;

import java.util.List;

import me.mikasa.science.bean.BaiKeItem;

/**
 * Created by mikasacos on 2018/9/24.
 */

public interface GetBaiKe {
    interface View{
        void getBaiKeSuccess(List<BaiKeItem> list);
        void getError(String msg);
    }
    interface Model{
        void getBaiKe(Context context);//没有getError,getSuccess
    }
    interface Presenter{
        void getBaike(Context context);
        void getBaiKeSuccess(List<BaiKeItem>list);
        void getError(String msg);
    }
}
