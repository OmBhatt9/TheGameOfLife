
package com.example.thegameoflife;
import android.content.Context;
import android.content.IntentSender;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Class that uses canvas to calculate and display grid size and show movement of cells by
 * game of Life logic.
 */

public class PixelGridView extends SurfaceView implements Runnable {

    private int colWidth;
    private int rowH;
    private boolean onGoing = false;
    private int columns;
    private int rows;
    private Logic logic;
    private Rect rect = new Rect();
    private Thread thread;
    private Paint cellColor = new Paint();

    public PixelGridView(Context context) {
        super(context);
        initGrid();
    }

    public PixelGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGrid();
    }

    /**
     * specifies code to run on the thread and inserts a wait time between each transition as th game plays.
     */
    @Override
    public void run() {
        while (onGoing) {
            if (!getHolder().getSurface().isValid()) {
                continue;
            }
            try {
                Thread.sleep(95);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Canvas canvas = getHolder().lockCanvas();
            logic.newGen();
            drawCells(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Initialises a new thread as soon as transitions happen.
     */

    public void start() {
        onGoing = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * calculates rows, columns and their data to draw cells.
     */

    private void initGrid() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        columns = point.x / 50;
        rows = point.y / 50;
        colWidth = point.x / columns;
        rowH = point.y / rows;
        logic = new Logic(columns, rows);
    }

    /**
     * draws transitioning cells on screen by drawing white and black rectangles with cell state information
     * from CellManager class.
     * @param canvas used to draw cells.
     */

    private void drawCells(Canvas canvas) {

        int i = 0;
        while (i < columns) {
            int j = 0;
            while (j < rows) {
                CellManager changeCell = logic.getCell(i, j);
                int left = (changeCell.getXcoord() * colWidth) - 1;
                int top = (changeCell.getYcoord() * rowH) - 1;
                int right = (changeCell.getXcoord() * colWidth + colWidth) - 1;
                int bottom = (changeCell.getYcoord() * rowH + rowH) - 1;
                rect.set(left, top, right, bottom);
                if (changeCell.getLiving()) {
                    cellColor.setColor(Color.WHITE);
                } else {
                    cellColor.setColor(Color.BLACK);
                }
                canvas.drawRect(rect, cellColor);
                j++;
            }
            i++;
        }
    }

    /**
     * To implement user interactivity, function reverses the cell states when user touches
     * the cell at that position, which changes the transition of the cell body
     * @param event the position where user wants to change a cell state.
     * @return the action that cell has been changed.
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int a = (int) (event.getX() / colWidth);
            int b = (int) (event.getY() / rowH);
            CellManager cell = logic.getCell(a, b);
            cell.reverse();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}