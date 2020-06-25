package com.example.blockforce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void Start_onClick(View view) {
        Intent i = new Intent(this,com.example.blockforce.Play.class);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    public void How_onClick(View view){
        DialogFragment dialog = new HowToPlayDialog();
        dialog.show(getSupportFragmentManager(),"dialog_how");
    }
}