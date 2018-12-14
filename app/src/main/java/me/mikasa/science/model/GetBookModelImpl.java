package me.mikasa.science.model;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import me.mikasa.science.bean.Book;
import me.mikasa.science.contract.GetBookContract;
import me.mikasa.science.utils.JsoupUtil;
import me.mikasa.science.utils.OkHttpResultCallback;
import me.mikasa.science.utils.OkHttpUtil;
import okhttp3.Call;

import static me.mikasa.science.constants.Constant.DangDangBook;
/**
 * Created by mikasacos on 2018/9/21.
 */

public class GetBookModelImpl implements GetBookContract.Model {
    private GetBookContract.Presenter mPresenter;
    public GetBookModelImpl(GetBookContract.Presenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public void getBook(final int page) {
        OkHttpUtil.getInstance().getAsync(DangDangBook, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.i("op","dd net error");
            }

            @Override
            public void onResponse(byte[] bytes) {
                try {
                    String s=new String(bytes,"GB2312");
                    List<Book>list= JsoupUtil.getInstance().getDangDangBook(s,page);
                    if (list!=null){
                        if (page==1){
                            Collections.shuffle(list);
                        }
                        mPresenter.getBookSuccess(list);
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
