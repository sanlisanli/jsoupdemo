package me.mikasa.science.view.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import me.mikasa.science.R;

/**
 * Created by mikasacos on 2018/9/23.
 */

public class BottomBarLayout extends LinearLayout implements ViewPager.OnPageChangeListener{
    private static final String STATE_INSTANCE = "instance_state";
    private static final String STATE_ITEM = "state_item";
    private ViewPager mViewPager;
    private int mChildCount;
    private List<BottomBarItem>mItemViews=new ArrayList<>();
    private int mCurrentItem;
    private boolean mSmoothScroll;
    public BottomBarLayout(Context context) {
        this(context, null);
    }

    public BottomBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public BottomBarLayout(Context context,AttributeSet attrs,int defStyleAttr){
        super(context, attrs,defStyleAttr);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.BottomBarLayout);
        mSmoothScroll=ta.getBoolean(R.styleable.BottomBarLayout_smoothScroll,false);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
    }
    public void setViewPager(ViewPager vp){
        this.mViewPager=vp;
        init();
    }
    private void init(){
        mChildCount=getChildCount();
        if (mViewPager!=null){
            if (mViewPager.getAdapter().getCount()!=mChildCount){
                throw new IllegalArgumentException("LinearLayout的子View数量必须和ViewPager条目数量一致");
            }
        }
        for (int i=0;i<mChildCount;i++){
            if (getChildAt(i)instanceof BottomBarItem){
                BottomBarItem bottomBarItem=(BottomBarItem)getChildAt(i);
                mItemViews.add(bottomBarItem);
                bottomBarItem.setOnClickListener(new MyOnClickListener(i));
            }else {
                throw new IllegalArgumentException("BottomBarLayout的子View必须是BottomBarItem");
            }
        }
        mItemViews.get(mCurrentItem).setStatus(true);

        if (mViewPager!=null){
            mViewPager.addOnPageChangeListener(this);//??
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        resetState();
        mItemViews.get(position).setStatus(true);
        if (mListener!=null){
            mListener.onItemSelected(getBottomItem(position),mCurrentItem,position);
        }
        mCurrentItem=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    public void updateTabState(int position){
        resetState();
        mCurrentItem=position;
        mItemViews.get(mCurrentItem).setStatus(true);
    }
    /**
     * 重置当前按钮的状态
     */
    private void resetState(){
        if (mCurrentItem<mItemViews.size()){
            mItemViews.get(mCurrentItem).setStatus(false);
        }
    }
    public void setCurrentItem(int currentItem){
        if (mViewPager!=null){
            mViewPager.setCurrentItem(currentItem,mSmoothScroll);
        }else {
            updateTabState(currentItem);
        }
    }
    public int getCurrentItem(){
        return mCurrentItem;
    }
    public void setSmoothScrool(boolean ss){
        this.mSmoothScroll=ss;
    }
    public BottomBarItem getBottomItem(int position){
        return mItemViews.get(position);
    }

    private class MyOnClickListener implements OnClickListener{
        private int position;
        public MyOnClickListener(int i){
            this.position=i;
        }

        @Override
        public void onClick(View v) {
            resetState();
            mItemViews.get(position).setStatus(true);
            if (mListener!=null){
                mListener.onItemSelected(getBottomItem(position),mCurrentItem,position);
            }
            mCurrentItem=position;
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable(STATE_INSTANCE,super.onSaveInstanceState());
        bundle.putInt(STATE_ITEM,mCurrentItem);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle=(Bundle)state;
            mCurrentItem=bundle.getInt(STATE_ITEM);
            resetState();
            mItemViews.get(mCurrentItem).setStatus(true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
        }else {
            super.onRestoreInstanceState(state);
        }
    }

    private OnItemSelectedListener mListener;
    public interface OnItemSelectedListener{
        void onItemSelected(BottomBarItem item, int prePos, int curPos);
    }
    public void setOnItemSelectedListener(OnItemSelectedListener listener){
        this.mListener=listener;
    }
}
