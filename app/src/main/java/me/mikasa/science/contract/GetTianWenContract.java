package me.mikasa.science.contract;

import java.util.List;

import me.mikasa.science.bean.TianWen;

/**
 * Created by mikasacos on 2018/9/8.
 */

public interface GetTianWenContract {
    interface View{
        void getTianWenSuccess(List<TianWen> list);
        void getError(String msg);
    }
    interface Model{
        void getTianWen(int page);//没有getError,getSuccess
    }
    interface Presenter{
        void getTianWen(int page);
        void getTianWenSuccess(List<TianWen>list);
        void getError(String msg);
    }
}
