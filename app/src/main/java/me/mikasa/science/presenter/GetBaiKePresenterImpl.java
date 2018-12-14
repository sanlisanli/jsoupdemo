package me.mikasa.science.presenter;

import android.content.Context;

import java.util.List;

import me.mikasa.science.bean.BaiKeItem;
import me.mikasa.science.contract.GetBaiKe;
import me.mikasa.science.model.GetBaiKeModelImpl;

/**
 * Created by mikasacos on 2018/9/24.
 */

public class GetBaiKePresenterImpl implements GetBaiKe.Presenter {
    private GetBaiKe.View mView;
    private GetBaiKe.Model mModel;
    public GetBaiKePresenterImpl(GetBaiKe.View view){
        this.mView=view;
        this.mModel=new GetBaiKeModelImpl(this);
    }

    @Override
    public void getBaike(Context context) {
        mModel.getBaiKe(context);
    }

    @Override
    public void getBaiKeSuccess(List<BaiKeItem> list) {
        mView.getBaiKeSuccess(list);
    }

    @Override
    public void getError(String msg) {
        mView.getError(msg);
    }
}
