package com.github.wbinarytree.marblediagramview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yaoda on 30/06/17.
 */

class MarbleView extends View {

    private float distance = 0;
    private Paint paint;
    private TextPaint textPaint;
    private float dX;
    private float dY;
    private String name = "";
    private float width;
    private float height;

    public MarbleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(48);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        setY(getY() + distance - w / 2);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2, height / 2, getWidth() / 2, paint);
        float xPos = width / 2;
        float yPos = height / 2 - (textPaint.descent() + textPaint.ascent() / 2);
        canvas.drawText(name, xPos, yPos, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = getX() - event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                //y(event.getRawY() + dY)
                float x = event.getRawX() + dX;
                if (x < getWidth() / 2) x = getWidth() / 2;
                View parent = (View) getParent();
                int width = parent.getWidth();
                if (x > width - getWidth() / 2 * 3) x = width - getWidth() / 2 * 3;
                animate().x(x).setDuration(0).start();
                //setX(event.getRawX() + dX);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void invalidate() {
        //setX(getX() + distance);

        super.invalidate();
    }
}
