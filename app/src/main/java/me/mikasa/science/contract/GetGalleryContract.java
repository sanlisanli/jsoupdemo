package me.mikasa.science.contract;

import java.util.List;

import me.mikasa.science.bean.GalleryItem;

/**
 * Created by mikasacos on 2018/9/13.
 */

public interface GetGalleryContract {
    interface View{
        void getGallerySuccess(List<GalleryItem> list);
        void getError(String msg);
    }
    interface Model{
        void getGallery(int page);//没有getError,getSuccess
    }
    interface Presenter{
        void getGallery(int page);
        void getGallerySuccess(List<GalleryItem>list);
        void getError(String msg);
    }
}
