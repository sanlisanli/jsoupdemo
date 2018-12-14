package me.mikasa.science.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.adapter.PagerAdapter;
import me.mikasa.science.base.BaseFragment;
import me.mikasa.science.fragment.home.GuoKeFragment;
import me.mikasa.science.fragment.home.SongShuFragment;
import me.mikasa.science.fragment.home.TianWenFragment;
import me.mikasa.science.listener.ScrollToTop;
import me.mikasa.science.utils.transformer.ScaleInOutTransformer;

/**
 * Created by mikasacos on 2018/9/12.
 */

public class HomeFragment extends BaseFragment implements ScrollToTop{
    @BindView(R.id.home_tab)
    TabLayout tabLayout;
    @BindView(R.id.home_view_pager)
    ViewPager viewPager;

    private List<Fragment>fragmentList;
    private List<String>mTitle;
    private Context mContext;
    private PagerAdapter mAdapter;
    private TianWenFragment tianWenFragment;
    private GuoKeFragment guoKeFragment;
    private SongShuFragment songShuFragment;
    private static int tabPos;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle bundle) {
        tabPos=0;
        fragmentList=new ArrayList<>();
        tianWenFragment=TianWenFragment.newInstance();
        guoKeFragment=GuoKeFragment.newInstance();
        songShuFragment=SongShuFragment.newInstance();
        mTitle=new ArrayList<>();
        mTitle.add("果壳网");
        mTitle.add("天之文");
        mTitle.add("科学松鼠会");
        fragmentList.add(guoKeFragment);
        fragmentList.add(tianWenFragment);
        fragmentList.add(songShuFragment);
    }

    @Override
    protected void initView() {
        mContext=this.getContext();
        mAdapter=new PagerAdapter(getChildFragmentManager(),fragmentList,mTitle);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageTransformer(true,new ScaleInOutTransformer());//??
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        viewPager.setCurrentItem(0);
                        tabPos=0;
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        tabPos=1;
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        tabPos=2;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void toTop() {
        if (tabPos==0){
            tianWenFragment.toTop();
        }
    }
}
