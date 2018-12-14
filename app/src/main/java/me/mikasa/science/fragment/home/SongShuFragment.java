package me.mikasa.science.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.activity.WebViewActivity;
import me.mikasa.science.adapter.SongShuAdapter;
import me.mikasa.science.base.BaseFragment;
import me.mikasa.science.bean.SongShu;
import me.mikasa.science.contract.GetSongShuContract;
import me.mikasa.science.listener.OnRecyclerViewItemClickListener;
import me.mikasa.science.presenter.GetSongShuPresenterImpl;

/**
 * Created by mikasacos on 2018/9/27.
 */

public class SongShuFragment extends BaseFragment implements GetSongShuContract.View{
    @BindView(R.id.xrv_songshu)
    XRecyclerView recyclerView;

    private Context mContext;
    private GetSongShuPresenterImpl mPresenter;
    private SongShuAdapter mAdapter;
    private List<SongShu>mSongShuList=new ArrayList<>();
    private static int sPage=1;
    public static SongShuFragment newInstance(){
        return new SongShuFragment();
    }
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_songshu;
    }

    @Override
    public void getSongShuSuccess(List<SongShu> list) {
        mSongShuList=list;
        if (sPage>10){
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
    }

    @Override
    protected void initData(Bundle bundle) {
        mContext=this.getContext();
        mPresenter=new GetSongShuPresenterImpl(this);
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter=new SongShuAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        //在adapter里设置点击事件
        mAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.i("opsongshu",mSongShuList.get(pos-1).getLink());//有header
                Intent intent=new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url",mSongShuList.get(pos).getLink());
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
                mPresenter.getSongShu(1);
            }

            @Override
            public void onLoadMore() {
                ++sPage;
                mPresenter.getSongShu(sPage);
            }
        });
        recyclerView.refresh();//发送网络数据请求
    }
}
