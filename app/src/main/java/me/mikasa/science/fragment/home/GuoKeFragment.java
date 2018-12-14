package me.mikasa.science.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.activity.WebViewActivity;
import me.mikasa.science.adapter.GuokeAdapter;
import me.mikasa.science.base.BaseFragment;
import me.mikasa.science.bean.GuoKe;
import me.mikasa.science.contract.GetGuoKeContract;
import me.mikasa.science.listener.OnRecyclerViewItemClickListener;
import me.mikasa.science.presenter.GetGuoKePresenterImpl;

/**
 * Created by mikasacos on 2018/9/27.
 */

public class GuoKeFragment extends BaseFragment implements GetGuoKeContract.View{
    @BindView(R.id.xrv_guoke)
    XRecyclerView recyclerView;

    private GetGuoKePresenterImpl mPresenter;
    private Context mContext;
    private GuokeAdapter mAdapter;
    private List<GuoKe>mGuokeList=new ArrayList<>();
    public static GuoKeFragment newInstance(){
        return new GuoKeFragment();
    }
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_guoke;
    }

    @Override
    protected void initData(Bundle bundle) {
        mContext=this.getContext();
        mPresenter=new GetGuoKePresenterImpl(this);
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter=new GuokeAdapter(mContext);
        recyclerView.setLimitNumberToCallLoadMore(1);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //xrv已有一个header
                Intent intent=new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url",mGuokeList.get(pos-1).getLink());//有header
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
                mPresenter.getGuoKe(mContext);
            }

            @Override
            public void onLoadMore() {
                List<GuoKe> loadMoreList=mGuokeList;
                Collections.shuffle(loadMoreList);
                mAdapter.appendData(loadMoreList);
                //recyclerView.loadMoreComplete();
            }
        });
        recyclerView.refresh();//发送网络数据请求
    }

    @Override
    public void getGuokeSuccess(List<GuoKe> list) {
        recyclerView.refreshComplete();//
        mGuokeList=list;
        mAdapter.updateData(list);
    }

    @Override
    public void getError(String msg) {
    }
}
