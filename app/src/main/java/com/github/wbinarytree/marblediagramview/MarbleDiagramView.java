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
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Style.FILL_AND_STROKE;

/**
 * Created by yaoda on 30/06/17.
 */

public class MarbleDiagramView extends FrameLayout {
    private static final int MARGIN = 10;
    private static final float ARROWSIZE = 25f;
    private static final float LINE_SIZE = 5f;
    private final Context context;
    private boolean isBaseDrew;
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
    //private List<List<Marble>> observables;
    private List<List<MarbleView>> observables;
    private float distance;
    private boolean observableLoaded;
    private MarbleDiagram diagram;

    public MarbleDiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MarbleDiagramView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        //setWillNotDraw(false);
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
        isBaseDrew = false;
        observableLoaded = false;
        initTest();
        //loadObservable();

    }

    private void initTest() {
        number = 3;
        operatorName = "Merge";
        observables = new ArrayList<>();
        List<MarbleView> o1 = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        drawBase(canvas);
        //if (distance != 0 && !observableLoaded) {
        //    loadObservable();
        //}
        if (distance != 0 && !observableLoaded) {
            load(diagram);
        }
        super.dispatchDraw(canvas);
    }

    public void loadDiagram(MarbleDiagram diagram) {
        this.diagram = diagram;
    }

    private void load(MarbleDiagram diagram) {
        this.removeAllViews();
        this.operatorName = diagram.getOperatorName();
        this.setNumber(diagram.getSource().size());
        distance = height / (number + 2) / 2;
        invalidate();
        LayoutParams params = new LayoutParams(80, 80);
        float posY = distance;
        float posX;
        float resY = distance * (2 * number + 3);
        float resX = 40;
        for (List<Marble> marbleList : diagram.getSource()) {
            posX = 40;
            for (Marble marble : marbleList) {
                MarbleView marbleView = new MarbleView(context, marble);
                marbleView.setLayoutParams(params);
                marbleView.setDistanceX(posX);
                marbleView.setName(marble.getValue());
                posX += 40 * 3;
                marbleView.setDistanceY(posY);
                this.addView(marbleView);
                MarbleView marbleViewRes = new MarbleView(context, marble);
                marbleViewRes.setLayoutParams(params);
                marbleViewRes.setDistanceX(resX);
                marbleViewRes.setName(marble.getValue());
                resX += 40 * 3;
                marbleViewRes.setDistanceY(resY);
                marbleViewRes.setClickable(false);
                this.addView(marbleViewRes);
                marbleView.linkedWith(marbleViewRes);
            }
            posY += 2 * distance;
        }
        observableLoaded = true;
    }

    public void loadObservable() {
        MarbleView marbleView = new MarbleView(context);
        FrameLayout.LayoutParams params = new LayoutParams(80, 80);
        marbleView.setLayoutParams(params);
        marbleView.setDistanceY(distance);
        marbleView.setDistanceX(40);
        marbleView.setName("A");
        this.addView(marbleView);
        MarbleView marbleView2 = new MarbleView(context);
        marbleView2.setLayoutParams(params);
        marbleView2.setDistanceY(distance * ((number + 1) * 2 + 1));
        marbleView2.setName("A");
        this.addView(marbleView2);
        marbleView.linkedWith(marbleView2);
        observableLoaded = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        distance = height / (number + 2) / 2;
        invalidate();
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
        path.reset();
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
