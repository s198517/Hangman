package com.example.simenarvnes.s198517;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.simenarvnes.hangman.R;

/**
 * Created by simenarvnes on 13/09/15.
 */
public class Hangman extends View {
    public final Paint paint = new Paint();
    public static int AVAILABLE_ATTEMPTS = 6;
    public int round = 0;

    public Hangman(Context context) {
        super(context);

    }

    public int getRound(){ return round; }
    public void setRound() {
        round++;
    }

    public void resetRound(){ round = 0; }

    public void onDraw(Canvas canvas){
        float scale = 1.0F;
        int width = (int)(canvas.getWidth() * scale);
        int height = (int)(canvas.getHeight() * scale);

        // define drawing colors
        paint.setColor(getResources().getColor(R.color.background));
        // set stroke width
        paint.setStrokeWidth(2);
        // set style
        paint.setStyle(Paint.Style.STROKE);
        // set text size
        paint.setTextSize(20);

        switch(round) {
            case 0:
                // draw platform
                paint.setStrokeWidth(10);
                canvas.drawLine(50, 300, 200, 300, paint);
                canvas.drawLine(50, 300, 50, 350, paint);
                canvas.drawLine(200, 300, 200, 350, paint);
                break;

            case 1:
                // draw platform
                paint.setStrokeWidth(10);
                canvas.drawLine(50, 300, 200, 300, paint);
                canvas.drawLine(50, 300, 50, 350, paint);
                canvas.drawLine(200, 300, 200, 350, paint);
                // draw pole
                paint.setStrokeWidth(5);
                canvas.drawLine(125, 300, 125, 50, paint);
                canvas.drawLine(125,50, 250, 50, paint);
                break;

            case 2:
                // draw platform
                paint.setStrokeWidth(10);
                canvas.drawLine(50, 300, 200, 300, paint);
                canvas.drawLine(50, 300, 50, 350, paint);
                canvas.drawLine(200, 300, 200, 350, paint);
                // draw pole
                paint.setStrokeWidth(5);
                canvas.drawLine(125, 300, 125, 50, paint);
                canvas.drawLine(125,50, 250, 50, paint);
                paint.setStrokeWidth(2);
                // draw rope
                canvas.drawLine(250, 50, 250, 100, paint);
                break;

            case 3:
                // draw platform
                paint.setStrokeWidth(10);
                canvas.drawLine(50, 300, 200, 300, paint);
                canvas.drawLine(50, 300, 50, 350, paint);
                canvas.drawLine(200, 300, 200, 350, paint);
                // draw pole
                paint.setStrokeWidth(5);
                canvas.drawLine(125, 300, 125, 50, paint);
                canvas.drawLine(125,50, 250, 50, paint);
                paint.setStrokeWidth(2);
                // draw rope
                canvas.drawLine(250, 50, 250, 100, paint);
                // draw head
                canvas.drawCircle(250, 125, 25, paint);
                break;

            case 4:
                // draw platform
                paint.setStrokeWidth(10);
                canvas.drawLine(50, 300, 200, 300, paint);
                canvas.drawLine(50, 300, 50, 350, paint);
                canvas.drawLine(200, 300, 200, 350, paint);
                // draw pole
                paint.setStrokeWidth(5);
                canvas.drawLine(125, 300, 125, 50, paint);
                canvas.drawLine(125,50, 250, 50, paint);
                paint.setStrokeWidth(2);
                // draw rope
                canvas.drawLine(250, 50, 250, 100, paint);
                // draw head
                canvas.drawCircle(250, 125, 25, paint);
                // draw body
                canvas.drawLine(250, 150, 250, 200, paint);
                break;

            case 5:
                // draw platform
                paint.setStrokeWidth(10);
                canvas.drawLine(50, 300, 200, 300, paint);
                canvas.drawLine(50, 300, 50, 350, paint);
                canvas.drawLine(200, 300, 200, 350, paint);
                // draw pole
                paint.setStrokeWidth(5);
                canvas.drawLine(125, 300, 125, 50, paint);
                canvas.drawLine(125,50, 250, 50, paint);
                paint.setStrokeWidth(2);
                // draw rope
                canvas.drawLine(250, 50, 250, 100, paint);
                // draw head
                canvas.drawCircle(250, 125, 25, paint);
                // draw body
                canvas.drawLine(250, 150, 250, 200, paint);
                // draw arms
                canvas.drawLine(250, 160, 225, 175, paint);
                canvas.drawLine(250, 160, 275, 175, paint);
                break;

            case 6:
                // draw platform
                paint.setStrokeWidth(10);
                canvas.drawLine(50, 300, 200, 300, paint);
                canvas.drawLine(50, 300, 50, 350, paint);
                canvas.drawLine(200, 300, 200, 350, paint);
                // draw pole
                paint.setStrokeWidth(5);
                canvas.drawLine(125, 300, 125, 50, paint);
                canvas.drawLine(125,50, 250, 50, paint);
                paint.setStrokeWidth(2);
                // draw rope
                canvas.drawLine(250, 50, 250, 100, paint);
                // draw head
                canvas.drawCircle(250, 125, 25, paint);
                // draw body
                canvas.drawLine(250, 150, 250, 200, paint);
                // draw arms
                canvas.drawLine(250, 160, 225, 175, paint);
                canvas.drawLine(250, 160, 275, 175, paint);
                // draw legs
                canvas.drawLine(250, 200, 225, 225, paint);
                canvas.drawLine(250, 200, 275, 225, paint);
                canvas.drawText("You\n be\n dead", 325, 150, paint);
                break;


        }
        super.onDraw(canvas);
    }

    // redraw image
    public void reDraw(){
        invalidate();
    }

}

