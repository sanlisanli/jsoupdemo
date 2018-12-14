package me.mikasa.science.view.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.mikasa.science.R;
import me.mikasa.science.utils.UIUtils;

/**
 * Created by mikasacos on 2018/9/23.
 */

public class BottomBarItem extends LinearLayout {
    private Context mContext;
    private int mIconNormalResourceId;//icon
    private int mIconSelectedResourceId;
    private String mText;
    private int mTextSize=12;
    private int mTextColorNormal=0xFF999999;//title
    private int mTextColorSelected = 0xFF46C01B;
    private int mWhiteColor = 0xFFFFFFFF;  //白色
    private int mMarginTop = 0;//文字和图标的距离,默认0dp
    private boolean mOpenTouchBg=false;
    private Drawable mTouchDrawable;//触摸时的背景
    private int mIconWidth;//图标的宽度
    private int mIconHeight;//图标的高度
    private int mItemPadding;//BottomBarItem的padding

    private ImageView mImageView;
    private TextView mTextView;

    public BottomBarItem(Context context) {
        this(context, null);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public BottomBarItem(Context context,@Nullable AttributeSet attrs,int defStyleAttr){
        super(context, attrs, defStyleAttr);
        mContext=context;
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.BottomBarItem);
        initAttrs(ta);
        ta.recycle();//??
        checkValues();//检查值是否合法
        init();
    }

    /**
     * 初始化属性
     * @param ta
     */
    private void initAttrs(TypedArray ta){
        mIconNormalResourceId=ta.getResourceId(R.styleable.BottomBarItem_iconNormal,-1);
        mIconSelectedResourceId=ta.getResourceId(R.styleable.BottomBarItem_iconSelected,-1);
        mText=ta.getString(R.styleable.BottomBarItem_itemText);
        mTextSize=ta.getDimensionPixelSize(R.styleable.BottomBarItem_itemTextSize, UIUtils.sp2px(mContext,mTextSize));
        mTextColorNormal = ta.getColor(R.styleable.BottomBarItem_textColorNormal, mTextColorNormal);
        mTextColorSelected = ta.getColor(R.styleable.BottomBarItem_textColorSelected, mTextColorSelected);

        mMarginTop = ta.getDimensionPixelSize(R.styleable.BottomBarItem_itemMarginTop, UIUtils.dip2Px(mContext, mMarginTop));

        mOpenTouchBg = ta.getBoolean(R.styleable.BottomBarItem_openTouchBg, mOpenTouchBg);
        mTouchDrawable = ta.getDrawable(R.styleable.BottomBarItem_touchDrawable);
        mIconWidth = ta.getDimensionPixelSize(R.styleable.BottomBarItem_iconWidth, 0);
        mIconHeight = ta.getDimensionPixelSize(R.styleable.BottomBarItem_iconHeight, 0);
        mItemPadding = ta.getDimensionPixelSize(R.styleable.BottomBarItem_itemPadding, 0);
    }

    /**
     * 检查传入的值是否完善
     */
    private void checkValues(){
        if (mIconNormalResourceId == -1) {
            throw new IllegalStateException("您还没有设置默认状态下的图标，请指定iconNormal的图标");
        }

        if (mIconSelectedResourceId == -1) {
            throw new IllegalStateException("您还没有设置选中状态下的图标，请指定iconSelected的图标");
        }

        if (mOpenTouchBg && mTouchDrawable == null) {
            //如果有开启触摸背景效果但是没有传对应的drawable
            throw new IllegalStateException("开启了触摸效果，但是没有指定touchDrawable");
        }
    }
    private void init(){
        setOrientation(VERTICAL);//linearLayout
        setGravity(Gravity.CENTER);
        View view=initView();
        mImageView.setImageResource(mIconNormalResourceId);
        if (mIconWidth!=0&&mIconHeight!=0){
            LayoutParams lp=(LayoutParams)mImageView.getLayoutParams();
            lp.width=mIconWidth;
            lp.height=mIconHeight;
            mImageView.setLayoutParams(lp);
        }
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTextSize);
        mTextView.setTextColor(mTextColorNormal);
        mTextView.setText(mText);
        LayoutParams textLp=(LayoutParams)mTextView.getLayoutParams();
        textLp.topMargin=mMarginTop;
        mTextView.setLayoutParams(textLp);
        if (mOpenTouchBg){
            //如果有开启触摸背景
            setBackground(mTouchDrawable);//linearLayout--->setBackground
        }
        addView(view);//linearLayout-->addView()
    }
    @NonNull
    private View initView(){
        View view=View.inflate(mContext,R.layout.item_bottom_bar,null);
        if (mItemPadding!=0){
            //如果有设置item的padding
            view.setPadding(mItemPadding, mItemPadding, mItemPadding, mItemPadding);
        }
        mImageView=(ImageView)view.findViewById(R.id.iv_icon);
        mTextView=(TextView)view.findViewById(R.id.tv_text);
        return view;
    }
    public ImageView getImageView(){
        return mImageView;
    }
    public TextView getTextView(){
        return mTextView;
    }
    public void setIconNormalResourceId(int id){
        this.mIconNormalResourceId=id;
    }
    public void setIconSelectedResourceId(int id){
        this.mIconSelectedResourceId=id;
    }
    public void setStatus(boolean isSelected){
        Drawable drawable=ContextCompat.getDrawable(mContext,isSelected?mIconSelectedResourceId:mIconNormalResourceId);
        mImageView.setImageDrawable(drawable);
        //mImageView.setImageDrawable(getResources().getDrawable(isSelected?mIconSelectedResourceId:mIconNormalResourceId));
        mTextView.setTextColor(isSelected?mTextColorSelected:mTextColorNormal);
    }
}
