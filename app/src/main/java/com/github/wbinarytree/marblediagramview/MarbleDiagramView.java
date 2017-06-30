package com.github.wbinarytree.marblediagramview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Style.FILL_AND_STROKE;

/**
 * Created by yaoda on 30/06/17.
 */

public class MarbleDiagramView extends View {
    private static final int MARGIN = 10;
    private static final float ARROWSIZE = 25f;
    private static final float LINE_SIZE = 5f;

    private final Context context;
    private int width;
    private int height;
    private Paint baseLinePaint;
    private Paint arrowPaint;
    private Paint operatorPaint;
    private Paint ballPaint;
    private TextPaint ballTextPaint;
    private TextPaint textPaint;
    private Path path;
    private int number;
    private String operatorName;
    private List<List<BallView>> observables;
    private float distance;

    public MarbleDiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        baseLinePaint = new Paint();
        baseLinePaint.setStyle(Paint.Style.STROKE);
        baseLinePaint.setStrokeWidth(2);
        baseLinePaint.setColor(Color.BLACK);
        arrowPaint = new Paint();
        arrowPaint.setColor(Color.BLACK);
        arrowPaint.setStrokeWidth(2);
        arrowPaint.setStyle(Paint.Style.FILL);
        textPaint = new TextPaint();
        textPaint.setTextSize(60);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD_ITALIC));
        path = new Path();
        operatorPaint = new Paint(ANTI_ALIAS_FLAG);
        operatorPaint.setStyle(Paint.Style.FILL);
        ballPaint = new Paint(ANTI_ALIAS_FLAG);
        ballPaint.setStyle(FILL_AND_STROKE);
        ballPaint.setColor(Color.RED);
        ballPaint.setStrokeWidth(3f);
        ballTextPaint = new TextPaint(ANTI_ALIAS_FLAG);
        ballTextPaint.setTextSize(40);
        ballTextPaint.setColor(Color.BLACK);
        ballTextPaint.setTextAlign(Paint.Align.CENTER);
        ballTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));

        //ballPaint.setStrokeWidth();
        if (getBackground() instanceof ColorDrawable) {
            operatorPaint.setColor(((ColorDrawable) getBackground()).getColor());
        } else {
            operatorPaint.setColor(Color.WHITE);
        }
        operatorPaint.setShadowLayer(5f, 0, 0, Color.BLACK);
        setLayerType(LAYER_TYPE_SOFTWARE, operatorPaint);
        initTest();
    }

    private void initTest() {
        number = 3;
        operatorName = "Merge";
        observables = new ArrayList<>();
        List<BallView> o1 = new ArrayList<>();
        List<BallView> o2 = new ArrayList<>();
        List<BallView> o3 = new ArrayList<>();
        o1.add(new BallView("A"));
        o1.add(new BallView("B"));
        o1.add(new BallView("C"));
        o2.add(new BallView("1"));
        o2.add(new BallView("2"));
        o2.add(new BallView("3"));
        o3.add(new BallView("M"));
        o3.add(new BallView("N"));
        o3.add(new BallView("O"));
        observables.add(o1);
        observables.add(o2);
        observables.add(o3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBase(canvas);
        drawObservables(canvas);
        super.onDraw(canvas);
    }

    private void drawObservables(Canvas canvas) {
        if (observables.size() != number) {
            throw new RuntimeException("don't have enough Observable Set to Draw");
        }
        float y = distance;
        float space = width / 16;
        for (List<BallView> observable : observables) {
            for (int i = 1 ; i <= observable.size() ; i++) {
                float cx = space * (2 * i - 1);
                canvas.drawCircle(cx, y, space / 2, ballPaint);
                float posY = y - (ballTextPaint.descent() + ballTextPaint.ascent()) / 2;
                canvas.drawText(observable.get(i - 1).getValue(), cx, posY, ballTextPaint);
                //TextView text = new TextView(context);
                //this.addView(text);
                //ConstraintSet set = new ConstraintSet();
                //set.clone(this);
                //set.connect(text.getId(),ConstraintSet.TOP,this.getId(),ConstraintSet.TOP, (int) y);
                //set.connect(text.getId(),ConstraintSet.START,this.getId(),ConstraintSet.START, (int) cx);
                //set.applyTo(this);
            }
            y += 2 * distance;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        distance = height / (number + 2) / 2;
    }

    private void drawBase(Canvas canvas) {
        //draw base line

        float length = width - MARGIN;
        path.moveTo(length, 0);
        path.lineTo(length - ARROWSIZE * 2, -ARROWSIZE);
        path.lineTo(length - ARROWSIZE * 2, ARROWSIZE);
        path.offset(0, distance);
        path.close();
        float y;
        for (int i = 0 ; i < number ; i++) {
            y = distance * (i * 2 + 1);
            canvas.drawLine(MARGIN, y, length, y, baseLinePaint);
            canvas.drawPath(path, arrowPaint);
            path.offset(0, distance * 2);
        }
        y = distance * ((number + 1) * 2 + 1);
        path.offset(0, distance * 2);
        canvas.drawLine(MARGIN, y, length, y, baseLinePaint);
        canvas.drawPath(path, arrowPaint);
        y = distance * number * 2;
        canvas.drawRect(MARGIN, y, length, y + 2 * distance, operatorPaint);
        float xPos = (canvas.getWidth() / 2);
        float yPos =
            (distance * (number * 2 + 1) - ((textPaint.descent() + textPaint.ascent()) / 2));
        canvas.drawText(operatorName, xPos, yPos, textPaint);
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
