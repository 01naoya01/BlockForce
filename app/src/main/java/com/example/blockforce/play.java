package com.example.blockforce;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        game.init();
        FieldDraw(game);
        TextView v = findViewById(R.id.Level);
        v.setText("Level\n"+game.getLevel());
        v = findViewById(R.id.Lines);
        v.setText("Lines\n"+game.getDelete_count());
        repeat(handler);
    }

    public void repeat(final Handler handler){
        final TextView special = findViewById(R.id.SpecialFormView);
        timer.schedule( new TimerTask(){
            @Override
            public void run() {
                // handlerを通じてUI Threadへ処理をpost
                handler.post(new Runnable() {
                    public void run() {
                        if(sw){
                            //一定時間毎の処理をここに記述
                            if(!game.update()){
                                //ゲームオーバー時の処理
                                GameOver();
                            }
                            special.setText("After\n"+game.getCount()+"turns");
                            FieldDraw(game);
                        }
                        sw=TRUE;
                    }
                });
            }
        }, 0, period);
    }

    public void LevelCheck(){
        TextView v = findViewById(R.id.Level);
        v.setText("Level\n"+game.getLevel());
        v = findViewById(R.id.Lines);
        v.setText("Lines\n"+game.getDelete_count());

        period=1000-100*game.getLevel();
        timer.cancel();
        timer=new Timer();
        sw=FALSE;
        repeat(handler);
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
        TextView special = findViewById(R.id.SpecialFormView);
        if(game.getCount()==4){
            special.setText("After\n"+20+"turns");
        }else{
            special.setText("After\n"+(game.getCount()-1)+"turns");
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
