package me.mikasa.science.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.activity.WebViewActivity;
import me.mikasa.science.adapter.TianWenAdapter;
import me.mikasa.science.base.BaseFragment;
import me.mikasa.science.bean.TianWen;
import me.mikasa.science.contract.GetTianWenContract;
import me.mikasa.science.listener.OnRecyclerViewItemClickListener;
import me.mikasa.science.listener.ScrollToTop;
import me.mikasa.science.presenter.GetTianWenPresenterImpl;

/**
 * Created by mikasacos on 2018/9/12.
 */

public class TianWenFragment extends BaseFragment implements GetTianWenContract.View,ScrollToTop{
    @BindView(R.id.xrv_tianwen)
    XRecyclerView recyclerView;

    private TianWenAdapter mAdapter;
    private static int sPage=1;
    private Context mContext;
    private static List<TianWen>tianWenList=new ArrayList<>();
    private GetTianWenContract.Presenter mPresenter;
    public static TianWenFragment newInstance(){
        return new TianWenFragment();
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_tianwen;
    }

    @Override
    protected void initView() {
        mContext=this.getContext();
        mPresenter=new GetTianWenPresenterImpl(this);
        mAdapter=new TianWenAdapter(mContext);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.getDefaultFootView().setLoadingHint("努力加载中");
        recyclerView.getDefaultFootView().setNoMoreHint("没有更多了");
        recyclerView.setLimitNumberToCallLoadMore(2);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent=new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url",tianWenList.get(pos-1).getLink());//有header
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
                mPresenter.getTianWen(1);
            }

            @Override
            public void onLoadMore() {
                ++sPage;
                mPresenter.getTianWen(sPage);
            }
        });
        //初始化
        recyclerView.refresh();
    }

    @Override
    public void getTianWenSuccess(List<TianWen> list) {
        tianWenList.addAll(list);
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
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recyclerView!=null){
            recyclerView.destroy();
            recyclerView=null;
        }
    }

    @Override
    public void toTop() {
        recyclerView.scrollToPosition(0);
    }
}
