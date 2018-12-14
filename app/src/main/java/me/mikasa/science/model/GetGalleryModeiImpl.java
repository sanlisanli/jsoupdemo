package me.mikasa.science.model;

import java.io.UnsupportedEncodingException;
import java.util.List;

import me.mikasa.science.bean.GalleryItem;
import me.mikasa.science.contract.GetGalleryContract;
import me.mikasa.science.utils.JsoupUtil;
import me.mikasa.science.utils.OkHttpResultCallback;
import me.mikasa.science.utils.OkHttpUtil;
import okhttp3.Call;

import static me.mikasa.science.constants.Constant.TianWenStarHead;
import static me.mikasa.science.constants.Constant.TianWenTail;

/**
 * Created by mikasacos on 2018/9/13.
 */

public class GetGalleryModeiImpl implements GetGalleryContract.Model {
    private GetGalleryContract.Presenter mPresenter;
    public GetGalleryModeiImpl(GetGalleryContract.Presenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public void getGallery(int page) {
        OkHttpUtil.getInstance().getAsync(TianWenStarHead + String.valueOf(page) + TianWenTail,
                new OkHttpResultCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(byte[] bytes) {
                        try {
                            String s=new String(bytes,"gbk");
                            List<GalleryItem>list= JsoupUtil.getInstance().getTianWenStar(s);
                            if (list!=null){
                                mPresenter.getGallerySuccess(list);
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
