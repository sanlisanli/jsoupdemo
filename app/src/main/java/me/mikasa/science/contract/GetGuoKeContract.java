package me.mikasa.science.contract;

import android.content.Context;

import java.util.List;

import me.mikasa.science.bean.GuoKe;

/**
 * Created by mikasacos on 2018/9/27.
 */

public interface GetGuoKeContract {
    interface View{
        void getGuokeSuccess(List<GuoKe> list);
        void getError(String msg);
    }
    interface Model{
        void getGuoke(Context context);//没有getError,getSuccess
    }
    interface Presenter{
        void getGuoKe(Context context);
        void getGuoKeSuccess(List<GuoKe>list);
        void getError(String msg);
    }
}
