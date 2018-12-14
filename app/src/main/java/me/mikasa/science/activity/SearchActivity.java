package me.mikasa.science.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.adapter.BaiKeItemAdapter;
import me.mikasa.science.base.BaseToolbarActivity;
import me.mikasa.science.bean.BaiKeItem;
import me.mikasa.science.contract.GetBaiKe;
import me.mikasa.science.listener.OnRecyclerViewItemClickListener;
import me.mikasa.science.presenter.GetBaiKePresenterImpl;

import static me.mikasa.science.constants.Constant.SearchTextHint;

public class SearchActivity extends BaseToolbarActivity implements GetBaiKe.View,
        OnRecyclerViewItemClickListener{
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_search)
    ImageView search;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;

    private String[] mTags;
    private static int tagNum=20;
    private GetBaiKePresenterImpl mPresenter;
    private BaiKeItemAdapter adapter;
    private Context mContext;
    private List<BaiKeItem>mList=new ArrayList<>();
    private String s;
    private TagFlowLayout flowLayout;
    private RecyclerView recyclerView;

    @Override
    protected void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//??
        Intent intent=getIntent();
        s=intent.getStringExtra("hint");
        et_search.setText(s);
        et_search.setSelection(s.length());
    }

    @Override
    protected void initView() {
        mContext=this;
        mTags=getTags();
        TextWatcher watcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(et_search.getText().toString())){
                    iv_delete.setVisibility(View.VISIBLE);
                }else {
                    iv_delete.setVisibility(View.INVISIBLE);
                }
            }
        };
        et_search.addTextChangedListener(watcher);
        flowLayout=(TagFlowLayout)findViewById(R.id.flow_layout);
        flowLayout.setAdapter(new TagAdapter<String>(mTags) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv_tag=(TextView)LayoutInflater.from(mContext).inflate(R.layout.item_tag,flowLayout,false);//mInflater
                tv_tag.setText(s);
                return tv_tag;
            }
        });
        flowLayout=(TagFlowLayout)findViewById(R.id.flow_layout);
        mPresenter=new GetBaiKePresenterImpl(this);
        recyclerView=(RecyclerView)findViewById(R.id.rv_baike);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new BaiKeItemAdapter(mContext);
        recyclerView.setAdapter(adapter);
        mPresenter.getBaike(mContext);
    }
    @Override
    protected void initListener() {
        adapter.setOnRecyclerViewItemClickListener(this);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                et_search.setText(mTags[position]);
                et_search.setSelection(mTags[position].length());
                return true;
            }
        });
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    public void getBaiKeSuccess(List<BaiKeItem> list) {
        mList=list;
        if (adapter!=null){
            adapter.updateData(mList);
        }
    }

    @Override
    public void getError(String msg) {
    }

    @Override
    public void onItemClick(int pos) {
        String s=mList.get(pos).getTitle();
        goToWebViewActivity(s);
    }

    @Override
    public boolean onItemLongClick(int pos) {
        return false;
    }
    private void goToWebViewActivity(String encodeString){
        String s=null;
        try {
            s= URLEncoder.encode(encodeString,"utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String url="https://baike.baidu.com/item/"+s;
        Intent intent=new Intent(this,WebViewActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    private void search(){
        String s=et_search.getText().toString();
        if (s.length()>0){
            String encode=null;
            try {
                encode= URLEncoder.encode(s,"utf-8");
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            String url="https://baike.baidu.com/item/"+encode;
            Intent intent=new Intent(SearchActivity.this,WebViewActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
            finish();
        }
    }
    private String[] getTags(){
        List<String>originalList=new ArrayList<>(SearchTextHint.length);
        Collections.addAll(originalList,SearchTextHint);
        Collections.shuffle(originalList);
        List<String>list=new ArrayList<>(tagNum);
        for (int i=0;i<tagNum;i++){
            list.add(originalList.get(i));
        }
        String[] tags=new String[tagNum];
        return list.toArray(tags);
    }
}
