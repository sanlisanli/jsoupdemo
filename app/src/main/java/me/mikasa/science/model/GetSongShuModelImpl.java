package me.mikasa.science.model;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import me.mikasa.science.bean.SongShu;
import me.mikasa.science.contract.GetSongShuContract;
import me.mikasa.science.utils.JsoupUtil;
import me.mikasa.science.utils.OkHttpResultCallback;
import me.mikasa.science.utils.OkHttpUtil;
import okhttp3.Call;

import static me.mikasa.science.constants.Constant.SongShuHui_Head;
import static me.mikasa.science.constants.Constant.SongShuHui_Tail;

/**
 * Created by mikasacos on 2018/9/27.
 */

public class GetSongShuModelImpl implements GetSongShuContract.Model {
    private GetSongShuContract.Presenter mPresenter;
    public GetSongShuModelImpl(GetSongShuContract.Presenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public void getSongShu(final int page) {
        OkHttpUtil.getInstance().getAsync(SongShuHui_Head+String.valueOf(page)+SongShuHui_Tail,
                new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
            }

            @Override
            public void onResponse(byte[] bytes) {
                try {
                    String s=new String(bytes,"utf-8");
                    List<SongShu> list= JsoupUtil.getInstance().getSongShu(s);
                    if (list!=null){
                        if (page==1){
                            Collections.shuffle(list);
                        }
                        mPresenter.getSongshuSuccess(list);
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
