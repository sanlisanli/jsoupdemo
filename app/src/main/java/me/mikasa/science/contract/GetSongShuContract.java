package me.mikasa.science.contract;

import android.content.Context;

import java.util.List;

import me.mikasa.science.bean.SongShu;

/**
 * Created by mikasacos on 2018/9/8.
 */

public interface GetSongShuContract {
    interface View{
        void getSongShuSuccess(List<SongShu> list);
        void getError(String msg);
    }
    interface Model{
        void getSongShu(int page);//没有getError,getSuccess
    }
    interface Presenter{
        void getSongShu(int page);
        void getSongshuSuccess(List<SongShu>list);
        void getError(String msg);
    }
}
