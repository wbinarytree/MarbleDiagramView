package com.github.wbinarytree.marblediagramview;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 * Created by yaoda on 30/06/17.
 */

public class BallView {
    private String value;
    @ColorInt
    private int color;

    public BallView(String value, @ColorInt int color) {
        this.value = value;
        this.color = color;
    }
    public BallView(String value) {
        this.value = value;
        this.color = Color.RED;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
