package me.mikasa.science.presenter;

import android.content.Context;

import java.util.List;

import me.mikasa.science.bean.SongShu;
import me.mikasa.science.contract.GetSongShuContract;
import me.mikasa.science.model.GetSongShuModelImpl;

/**
 * Created by mikasacos on 2018/9/27.
 */

public class GetSongShuPresenterImpl implements GetSongShuContract.Presenter {
    private GetSongShuContract.View mView;
    private GetSongShuContract.Model mModel;
    public GetSongShuPresenterImpl(GetSongShuContract.View view){
        this.mView=view;
        mModel=new GetSongShuModelImpl(this);
    }

    @Override
    public void getSongShu(int page) {
        mModel.getSongShu(page);
    }

    @Override
    public void getSongshuSuccess(List<SongShu> list) {
        mView.getSongShuSuccess(list);
    }

    @Override
    public void getError(String msg) {
        mView.getError(msg);
    }
}
