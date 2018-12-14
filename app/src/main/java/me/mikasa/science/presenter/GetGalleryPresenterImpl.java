package me.mikasa.science.presenter;

import java.util.List;

import me.mikasa.science.bean.GalleryItem;
import me.mikasa.science.contract.GetGalleryContract;
import me.mikasa.science.model.GetGalleryModeiImpl;

/**
 * Created by mikasacos on 2018/9/13.
 */

public class GetGalleryPresenterImpl implements GetGalleryContract.Presenter {
    private GetGalleryContract.View mView;
    private GetGalleryContract.Model mModel;
    public GetGalleryPresenterImpl(GetGalleryContract.View view){
        this.mView=view;
        mModel=new GetGalleryModeiImpl(this);
    }

    @Override
    public void getGallery(int page) {
        mModel.getGallery(page);
    }

    @Override
    public void getGallerySuccess(List<GalleryItem> list) {
        mView.getGallerySuccess(list);
    }

    @Override
    public void getError(String msg) {
        mView.getError(msg);
    }
}
