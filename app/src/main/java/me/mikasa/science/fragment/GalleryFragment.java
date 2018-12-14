package me.mikasa.science.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.activity.WebViewActivity;
import me.mikasa.science.adapter.StarGalleryAdapter;
import me.mikasa.science.base.BaseFragment;
import me.mikasa.science.bean.GalleryItem;
import me.mikasa.science.contract.GetGalleryContract;
import me.mikasa.science.listener.OnRecyclerViewItemClickListener;
import me.mikasa.science.presenter.GetGalleryPresenterImpl;

/**
 * Created by mikasacos on 2018/9/13.
 */

public class GalleryFragment extends BaseFragment implements GetGalleryContract.View{
    @BindView(R.id.xrv_gallery)
    XRecyclerView recyclerView;

    private Context mContext;
    private static int sPage;
    private static List<GalleryItem>galleryItemList=new ArrayList<>();
    private StarGalleryAdapter mAdapter;
    private GetGalleryContract.Presenter mPresenter;

    public static GalleryFragment newInstance(){
        return new GalleryFragment();
    }
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    protected void initView() {
        mContext=this.getContext();
        sPage=1;
        mPresenter=new GetGalleryPresenterImpl(this);
        mAdapter=new StarGalleryAdapter(mContext);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.getDefaultFootView().setLoadingHint("努力加载中");
        recyclerView.getDefaultFootView().setNoMoreHint("没有更多了");
        recyclerView.setLimitNumberToCallLoadMore(16);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos) {
              //  Toast.makeText(mContext,"你点击了"+String.valueOf(pos),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url",galleryItemList.get(pos-1).getLink());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(int pos) {
                return false;
            }
        });
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGallery(1);
            }

            @Override
            public void onLoadMore() {
                ++sPage;
                mPresenter.getGallery(sPage);
            }
        });
        //初始化
        recyclerView.refresh();
    }

    @Override
    public void getGallerySuccess(List<GalleryItem> list) {
        galleryItemList.addAll(list);
        if (sPage>20){
            if (recyclerView!=null){
                recyclerView.setNoMore(true);
                recyclerView.loadMoreComplete();
            }
            mAdapter.appendData(list);
        }else if (sPage==1){
            if (recyclerView!=null){
                recyclerView.refreshComplete();
            }
            mAdapter.updateData(list);
        }else {
            if (recyclerView!=null){
                recyclerView.loadMoreComplete();
            }
            mAdapter.appendData(list);
        }
    }

    @Override
    public void getError(String msg) {
        if (sPage==1){
            if (recyclerView!=null){
                recyclerView.refreshComplete();
            }
        }else {
            if (recyclerView!=null){
                recyclerView.loadMoreComplete();
            }
        }
        showToast(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recyclerView!=null){
            recyclerView.destroy();
            recyclerView=null;
        }
    }
}
