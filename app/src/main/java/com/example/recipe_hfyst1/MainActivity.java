package com.example.recipe_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.recipeListView);
        setRecipeListView();
    }

    private void setRecipeListView(){
        Log.d(TAG,"before create database handler");
        DatabaseHandler databaseHandler = new DatabaseHandler(this,null,null,1);
        final List<Recipe> recipeList = databaseHandler.getAllRecipe("Title");

        if(recipeList.size()<=0){
            Toast.makeText(this,"No recipes found.",Toast.LENGTH_SHORT).show();
            return;
        }

        // parse into string format for display
        List<String> displayList = new ArrayList<>();
        for(Recipe r: recipeList){
            displayList.add(r.getName() + ", "+ r.getRating());
        }
        lv.setAdapter(new ArrayAdapter<>(this, R.layout.my_simple_list, displayList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View view, int position, long id) {
//                Recipe selectedFromList = (Recipe) lv.getItemAtPosition(position);
                Recipe selectedRecipe = recipeList.get(position);
                Log.d(TAG, "Selects " + selectedRecipe.get_id());

                Intent det = new Intent(getApplicationContext(), DetailsActivity.class);
                det.putExtra("id", selectedRecipe.get_id());
                startActivity(det);
            }
        });

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