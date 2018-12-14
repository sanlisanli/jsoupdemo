package me.mikasa.science.presenter;

import java.util.List;

import me.mikasa.science.bean.TianWen;
import me.mikasa.science.contract.GetTianWenContract;
import me.mikasa.science.model.GetTianWenModelImpl;

/**
 * Created by mikasacos on 2018/9/12.
 */

public class GetTianWenPresenterImpl implements GetTianWenContract.Presenter {
    private GetTianWenContract.View mView;
    private GetTianWenContract.Model mModel;
    public GetTianWenPresenterImpl(GetTianWenContract.View view){
        this.mView=view;
        mModel=new GetTianWenModelImpl(this);//要的是实例化的model
    }

    @Override
    public void getTianWen(int page) {
        mModel.getTianWen(page);
    }

    @Override
    public void getTianWenSuccess(List<TianWen> list) {
        mView.getTianWenSuccess(list);
    }

    @Override
    public void getError(String msg) {
        mView.getError(msg);
    }
}
