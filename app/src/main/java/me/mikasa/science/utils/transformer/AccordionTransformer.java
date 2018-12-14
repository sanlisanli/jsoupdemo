package me.mikasa.science.utils.transformer;

import android.view.View;

import me.mikasa.science.base.ABaseTransformer;

/**
 * Created by mikasacos on 2018/9/13.
 */

public class AccordionTransformer extends ABaseTransformer {
    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setScaleX(position < 0 ? 1f + position : 1f - position);
    }
}
