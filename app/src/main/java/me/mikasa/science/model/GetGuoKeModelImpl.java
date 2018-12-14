package me.mikasa.science.model;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import me.mikasa.science.bean.GuoKe;
import me.mikasa.science.contract.GetGuoKeContract;
import me.mikasa.science.utils.JsoupUtil;
import me.mikasa.science.utils.OkHttpResultCallback;
import me.mikasa.science.utils.OkHttpUtil;
import okhttp3.Call;

import static me.mikasa.science.constants.Constant.GuoKeTianWen;
/**
 * Created by mikasacos on 2018/9/27.
 */

public class GetGuoKeModelImpl implements GetGuoKeContract.Model {
    private GetGuoKeContract.Presenter mPresenter;
    public GetGuoKeModelImpl(GetGuoKeContract.Presenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public void getGuoke(Context context) {
        OkHttpUtil.getInstance().getAsync(GuoKeTianWen, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                mPresenter.getError("获取网络数据失败");
            }

            @Override
            public void onResponse(byte[] bytes) {
                try {
                    String s=new String(bytes,"utf-8");
                    List<GuoKe> list= JsoupUtil.getInstance().getGuoKe(s);
                    if (list!=null){
                        Collections.shuffle(list);
                        mPresenter.getGuoKeSuccess(list);
                    }else {
                        mPresenter.getError("获取网络数据出错...");
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
