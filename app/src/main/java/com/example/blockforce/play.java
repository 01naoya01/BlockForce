package com.example.blockforce;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Play extends AppCompatActivity {
    private Timer timer = new Timer();
    private Tet game = new Tet();
    private int period=1000;
    final Handler handler = new Handler();
    boolean sw = TRUE;
    @Override
    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        game.init();
        FieldDraw(game);
        TextView v = findViewById(R.id.Level);
        v.setText(String.format("Level\n%d", game.getLevel()));
        v = findViewById(R.id.Lines);
        v.setText(String.format("Lines\n%d", game.getDelete_count()));
        repeat(handler);
    }

    public void repeat(final Handler handler){
        final TextView special = findViewById(R.id.SpecialFormView);
        timer.schedule( new TimerTask(){
            @Override
            public void run() {
                // handlerを通じてUI Threadへ処理をpost
                handler.post(new Runnable() {
                    @SuppressLint("DefaultLocale")
                    public void run() {
                        if(sw){
                            //一定時間毎の処理をここに記述
                            if(!game.update()){
                                //ゲームオーバー時の処理
                                GameOver();
                            }
                            special.setText(String.format("After\n%dturns", game.getCount()));
                            FieldDraw(game);
                        }
                        sw=TRUE;
                    }
                });
            }
        }, 0, period);
    }

    @SuppressLint("DefaultLocale")
    public void LevelCheck(){
        TextView v = findViewById(R.id.Level);
        v.setText(String.format("Level\n%d", game.getLevel()));
        v = findViewById(R.id.Lines);
        v.setText(String.format("Lines\n%d", game.getDelete_count()));

        period=1000-100*game.getLevel();
        timer.cancel();
        timer=new Timer();
        sw=FALSE;
        repeat(handler);
    }

    public void GameOver(){
        Toast.makeText(Play.this,"Game Over !!  Lines "+game.getDelete_count(),Toast.LENGTH_LONG).show();
        timer.cancel();
        finish();
        overridePendingTransition(0, 0);
    }

    public void Rotate_Left_onClick(View view) {
        game.rotation(false);
        FieldDraw(game);
    }

    public void Rotate_Right_onClick(View view) {
        game.rotation(true);
        FieldDraw(game);
    }

    @SuppressLint("DefaultLocale")
    public void Under_onClick(View view) {
        TextView special = findViewById(R.id.SpecialFormView);
        if(game.getCount()==4){
            special.setText("after\n20turns");
        }else{
            special.setText(String.format("After\n%dturns", game.getCount() - 1));
        }
        if(game.under()){
            GameOver();
        }
        FieldDraw(game);
        LevelCheck();
    }

    double cx=0,cy=0;
    int sensitivity=50;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cx=event.getX();
                cy=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if((cx-event.getX())>sensitivity){
                    //左にスライドした場合の処理
                    game.move(FALSE);
                    cx=event.getX();
                }else if((cx-event.getX())<-sensitivity){
                    //右にスライドした場合の処理
                    game.move(TRUE);
                    cx=event.getX();
                }
                if((cy-event.getY())<-sensitivity){
                    //下にスライドした場合の処理
                    game.update();
                    cy=event.getY();
                }
                FieldDraw(game);
                break;
            case MotionEvent.ACTION_UP:
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        timer.cancel();
        finish();
        overridePendingTransition(0, 0);
        return super.onKeyDown(keyCode, event);
    }

    public void FieldDraw (Tet game){
        Draw v = findViewById(R.id.view);
        v.setField(game);
    }
}
