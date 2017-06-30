package com.github.wbinarytree.marblediagramview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yaoda on 30/06/17.
 */

class BallView extends View {

    private float distance = 0;
    private Paint paint;
    private float dX;
    private float dY;

    public BallView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setY(getY() + distance - getHeight() / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
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
