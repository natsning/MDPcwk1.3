package com.example.recipe_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InsertActivity extends AppCompatActivity {

    private final String TAG = "Insert Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
    }

    public void onClickConfirm(View v){
        Log.d(TAG,"on click confirm");

    }
}