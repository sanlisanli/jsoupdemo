package me.mikasa.science.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.mikasa.science.R;
import me.mikasa.science.activity.WebViewActivity;
import me.mikasa.science.adapter.BookAdapter;
import me.mikasa.science.bean.Book;
import me.mikasa.science.contract.GetBookContract;
import me.mikasa.science.listener.OnRecyclerViewItemClickListener;
import me.mikasa.science.presenter.GetBookPresneterImpl;

/**
 * Created by mikasacos on 2018/9/21.
 */

public class DiscoveryFragment extends Fragment implements GetBookContract.View,
        OnRecyclerViewItemClickListener{
    @BindView(R.id.xrv_dis)
    XRecyclerView recyclerView;
    @BindView(R.id.root_dis)
    LinearLayout root_dis;
    Unbinder mUnbinder;
    private Context mContext;
    private BookAdapter adapter;
    private static int sPage=1;
    private List<Book>mBookList;
    private GetBookPresneterImpl mPresneter;
    public static DiscoveryFragment newInstance(){
        return new DiscoveryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_discovery,container,false);
        mUnbinder= ButterKnife.bind(this,rootView);
        initData();
        initView();
        setListener();
        return rootView;
    }
    private void initData(){
    }
    private void initView(){
        mContext=this.getContext();
        mPresneter=new GetBookPresneterImpl(this);
        adapter=new BookAdapter(mContext);
        recyclerView.setLimitNumberToCallLoadMore(2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }
    private void setListener(){
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresneter.getBook(1);
            }

            @Override
            public void onLoadMore() {
                ++sPage;
                if (sPage==6){
                    if (recyclerView!=null){
                        recyclerView.setNoMore(true);
                        recyclerView.loadMoreComplete();
                    }
                    return;
                }
                mPresneter.getBook(sPage);
                sPage++;
            }
        });
        adapter.setOnRecyclerViewItemClickListener(this);//在adapter里设置监听事件
        recyclerView.refresh();//开始请求网络数据
    }

    @Override
    public void onItemClick(int pos) {
        String link=mBookList.get(pos-1).getLink();//xrv有header
        Log.i("op",link);
        Intent intent=new Intent(mContext, WebViewActivity.class);
        intent.putExtra("url",link);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(int pos) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void getBookSuccess(List<Book> list) {
        if (sPage==1){
            if (recyclerView!=null){
                recyclerView.refreshComplete();
            }
            mBookList=new ArrayList<>();
            mBookList=list;
            adapter.updateData(mBookList);
        }else {
            if (recyclerView!=null){
                recyclerView.loadMoreComplete();
            }
            mBookList.addAll(list);
            adapter.appendData(list);
        }
    }

    @Override
    public void getError(String msg) {
    }
}
