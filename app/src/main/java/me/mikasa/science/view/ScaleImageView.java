package me.mikasa.science.view;

import android.widget.ImageView;
import android.content.Context;
import android.util.AttributeSet;
/**
 * Created by mikasacos on 2018/9/13.
 */

public class ScaleImageView extends ImageView {
    private float originalWidth;
    private float originalHeight;

    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, int width, int height) {
        super(context);
        originalHeight = height;
        originalWidth = width;
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOriginalSize(float originalWidth, float originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originalWidth > 0 && originalHeight > 0) {
            float ratio = originalWidth / originalHeight;
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            if (width > 0) {
                height = (int) (width / ratio);
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
