package me.mikasa.science.model;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import me.mikasa.science.bean.TianWen;
import me.mikasa.science.contract.GetTianWenContract;
import me.mikasa.science.utils.JsoupUtil;
import me.mikasa.science.utils.OkHttpResultCallback;
import me.mikasa.science.utils.OkHttpUtil;
import okhttp3.Call;

import static me.mikasa.science.constants.Constant.TianWenHead;
import static me.mikasa.science.constants.Constant.TianWenTail;
/**
 * Created by mikasacos on 2018/9/12.
 */

public class GetTianWenModelImpl implements GetTianWenContract.Model {
    private GetTianWenContract.Presenter mPresenter;
    public GetTianWenModelImpl(GetTianWenContract.Presenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public void getTianWen(final int page) {
        OkHttpUtil.getInstance().getAsync(TianWenHead + String.valueOf(page) + TianWenTail,
                new OkHttpResultCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.i("model","net error");
                    }

                    @Override
                    public void onResponse(byte[] bytes) {
                        try {
                            String s=new String(bytes,"gbk");
                            Log.i("op",s);
                            List<TianWen>list= JsoupUtil.getInstance().getTianWen(s);
                            if (list!=null){
                                if (page==1){
                                    Collections.shuffle(list);
                                }
                                mPresenter.getTianWenSuccess(list);
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
