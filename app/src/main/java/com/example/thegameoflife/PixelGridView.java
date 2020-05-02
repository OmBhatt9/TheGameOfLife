package com.example.thegameoflife;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

/**
 * TODO: document your custom view class.
 */
public class PixelGridView extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    int paintColor = ContextCompat.getColor(getContext(), R.color.white);
    private Paint paint = new Paint();

    private boolean[][] cellTouched;

    public PixelGridView(Context context) {
        this(context, null);
    }

    public PixelGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=getContext().obtainStyledAttributes(
                attrs,
                R.styleable.PixelGridView);
        //Don't forget this
        a.recycle();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(paintColor);
        paint.setStrokeWidth(2.5f);
    }

    public void setColumns(int numColumns) {
        this.numColumns = numColumns;
        calcHeightWidth();
    }

    public int getColumns() {
        return numColumns;
    }

    public void setRows(int numRows) {
        this.numRows = numRows;
        calcHeightWidth();
    }

    public int getRows() {
        return numRows;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calcHeightWidth();
    }

    private void calcHeightWidth() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth() / getColumns();
        cellHeight = cellWidth;
        //cellHeight = getHeight() / getNumRows();

        cellTouched = new boolean[numColumns][numRows];

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (cellTouched[i][j]) {
                    canvas.drawRect(i * cellWidth, j * cellHeight,
                            (i + 1) * cellWidth, (j + 1) * cellHeight,
                            paint);
                }
            }
        }

        for (int i = 1; i < numColumns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, paint);
        }

        for (int i = 1; i < numRows; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(ev.getX() / cellWidth);
            int row = (int)(ev.getY() / cellHeight);

            cellTouched[column][row] = !cellTouched[column][row];
            invalidate();
        }

        return true;
    }
}
