package com.example.blockforce;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Play extends AppCompatActivity {
    Timer timer = new Timer();
    Tet game = new Tet();
    int speed=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        game.init();
        FieldDraw(game);
        final Handler handler = new Handler();
        timer.schedule( new TimerTask(){
            @Override
            public void run() {
                // handlerを通じてUI Threadへ処理をpost
                handler.post(new Runnable() {
                    public void run() {
                        //一定時間毎の処理をここに記述
                        if(!game.update()){
                            //ゲームオーバー時の処理
                            GameOver();
                        }
                        speed=200;
                        FieldDraw(game);
                    }
                });
            }
        }, 0, speed);
    }

    public void GameOver(){
        Toast.makeText(Play.this,"Game Over !!",Toast.LENGTH_LONG).show();
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

    public void Under_onClick(View view) {
        if(game.under()){
            GameOver();
        }
        FieldDraw(game);
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
                    //Log.d("slide","Left");
                    cx=event.getX();
                }else if((cx-event.getX())<-sensitivity){
                    //右にスライドした場合の処理
                    game.move(TRUE);
                    //Log.d("slide","Right");
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

        Button a = findViewById(R.id.button3);
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
