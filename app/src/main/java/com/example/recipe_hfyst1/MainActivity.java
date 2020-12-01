package com.example.recipe_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickAdd(View v){
        Intent intent = new Intent(this, InsertActivity.class);
        startActivity(intent);
    }

    public void onClickFilterTitle(View v){

    }

    public void onClickFilterRating(View v){

    }

    public void onClickIngredients(View v){

    }
}