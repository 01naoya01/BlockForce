package com.example.blockforce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("aasd","aasdwasdwa");
    }


    public void Start_onClick(View view) {
        Intent i = new Intent(this,com.example.blockforce.Play.class);
        startActivity(i);
        overridePendingTransition(0, 0);
    }
}