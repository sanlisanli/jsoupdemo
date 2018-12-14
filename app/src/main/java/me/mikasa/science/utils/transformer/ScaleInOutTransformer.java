package me.mikasa.science.utils.transformer;

import android.view.View;

import me.mikasa.science.base.ABaseTransformer;

/**
 * Created by mikasacos on 2018/9/13.
 */

public class ScaleInOutTransformer extends ABaseTransformer {
    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setPivotY(view.getHeight() / 2f);
        float scale = position < 0 ? 1f + position : 1f - position;
        view.setScaleX(scale);
        view.setScaleY(scale);
    }
}
