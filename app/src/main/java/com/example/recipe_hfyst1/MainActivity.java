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
    private DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHandler = new DatabaseHandler(this,null,null,1);

        lv = findViewById(R.id.recipeListView);
        setRecipeListView("Title");
    }

    private void setRecipeListView(String sortOrder){
        final List<Recipe> recipeList = databaseHandler.getAllRecipe(sortOrder);
        if(recipeList.size()<=0){
            Toast.makeText(this,"No recipes found.",Toast.LENGTH_SHORT).show();
            return;
        }

        // parse into string format for display
        List<String> displayList = new ArrayList<>();
        for(Recipe r: recipeList){
            displayList.add(r.getName() + ",  "+ r.getRating()+"-star");
        }
        lv.setAdapter(new ArrayAdapter<>(this, R.layout.recipe_list_view, displayList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View view, int position, long id) {

                Recipe selectedRecipe = recipeList.get(position);

                Intent intent = new Intent(getApplicationContext(), ViewRateActivity.class);
                intent.putExtra("id", selectedRecipe.get_id());
                startActivity(intent);
            }
        });

    }

    public void onClickAdd(View v){
        Intent intent = new Intent(this, InsertActivity.class);
        startActivity(intent);
    }

    public void onClickFilterTitle(View v){
        setRecipeListView("Title");
    }

    public void onClickFilterRating(View v){
        setRecipeListView("Rating");
    }

    public void onClickIngredients(View v){
        Intent intent = new Intent(this,IngredientActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        setRecipeListView("Title");
        super.onResume();
    }
}