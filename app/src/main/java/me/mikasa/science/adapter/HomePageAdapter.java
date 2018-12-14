package me.mikasa.science.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by mikasacos on 2018/9/12.
 */

public class HomePageAdapter extends FragmentPagerAdapter {
    private List<Fragment>mList;
    private List<String>mTitle;
    public HomePageAdapter(FragmentManager fm){
        super(fm);
    }
    public HomePageAdapter(FragmentManager fm,List<Fragment>list,List<String>titles){
        super(fm);
        mList=list;
        mTitle=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }
    @Override
    public int getCount() {
        return mTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
