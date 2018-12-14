package me.mikasa.science.presenter;

import android.content.Context;

import java.util.List;

import me.mikasa.science.bean.GuoKe;
import me.mikasa.science.contract.GetGuoKeContract;
import me.mikasa.science.model.GetGuoKeModelImpl;

/**
 * Created by mikasacos on 2018/9/27.
 */

public class GetGuoKePresenterImpl implements GetGuoKeContract.Presenter {
    private GetGuoKeContract.View mView;
    private GetGuoKeContract.Model mModel;
    public GetGuoKePresenterImpl(GetGuoKeContract.View view){
        this.mView=view;
        mModel=new GetGuoKeModelImpl(this);
    }

    @Override
    public void getGuoKe(Context context) {
        mModel.getGuoke(context);
    }

    @Override
    public void getGuoKeSuccess(List<GuoKe> list) {
        mView.getGuokeSuccess(list);
    }

    @Override
    public void getError(String msg) {
        mView.getError(msg);
    }
}
