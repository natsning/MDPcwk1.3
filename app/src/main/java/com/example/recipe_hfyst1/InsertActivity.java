package com.example.recipe_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private final String TAG = "Insert Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        db = new DatabaseHandler(this,null,null,1);
    }

    public void onClickConfirm(View v){
        Log.d(TAG,"on click confirm");
        EditText recipe_title = findViewById(R.id.field_title);
        EditText recipe_ins = findViewById(R.id.field_instructions);
        EditText recipe_ing = findViewById(R.id.field_ingredients);

        // title and ingredients cannot be empty
        if(recipe_title.getText().toString().isEmpty()||recipe_ing.getText().toString().isEmpty()){
            Toast.makeText(this,R.string.toast_empty_error,Toast.LENGTH_SHORT).show();
            return;
        }

        Recipe r = new Recipe(recipe_title.getText().toString(),recipe_ins.getText().toString());
        db.insertRecipe(r);

        finish();


    }
}