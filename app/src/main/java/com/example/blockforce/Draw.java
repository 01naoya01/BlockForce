package com.example.blockforce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Draw extends View {
    Tet game = new Tet();
    public Draw(Context context) {
        super(context);
    }

    public void setField(Tet game) {
        this.game = game;
        invalidate();
    }

    public Draw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Draw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p;
        canvas.drawColor(Color.BLACK);
        int initx = 220 ,inity = 250 ,ratio=50;
        for(int i = 3; i < game.getField().length; i++){
            for(int j = 0; j < game.getField()[i].length; j++){
                p=Pcolor(game.getField()[i][j],40);
                canvas.drawPoint(ratio * j + initx,ratio * (i-3) + inity ,p);
            }
        }
        initx = 950;
        ratio=25;
        for(int k=0; k < game.getNextForm().length; k++){
            for(int i = 0; i < game.getNextForm()[k].length; i++){
                for(int j = 0; j < game.getNextForm()[k][i].length; j++){
                    p=Pcolor(game.getNextForm()[k][i][j],18);
                    if(k==3)k=4;
                    canvas.drawPoint(ratio * j + initx,ratio * (i-3) + inity+k*120 ,p);
                    if(k==4)k=3;
                }
            }
        }

    }

    public Paint Pcolor(int n, int size){
        Paint p = new Paint();
        p.setStrokeWidth(size);
        switch (n){
            case 0:
                p.setColor(Color.BLACK);
                break;
            case 1:
                p.setColor(Color.RED);
                break;
            case 2:
                p.setColor(Color.BLUE);
                break;
            case 3:
                p.setColor(Color.YELLOW);
                break;
            case 4:
                p.setColor(Color.GREEN);
                break;
            case 5:
                p.setColor(Color.CYAN);
                break;
            case 6:
                p.setColor(Color.MAGENTA);
                break;
            case 7:
                p.setARGB(255,255,165,0);

                break;
            case 8:
                p.setARGB(255,75,0,130);
                break;
            case -1:
                p.setColor(Color.DKGRAY);
                break;
            case 99:
                p.setColor(Color.WHITE);
                break;
        }
        return p;
    }
}
