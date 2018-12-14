package me.mikasa.science.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.base.BaseFragment;

/**
 * Created by mikasacos on 2018/9/21.
 */

public class MineFragment extends BaseFragment {

    public static MineFragment newInstance(){
        return new MineFragment();
    }
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setListener() {
    }
}
