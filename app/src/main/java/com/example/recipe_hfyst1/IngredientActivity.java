package com.example.recipe_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        setIngredientListView();
    }

    private void setIngredientListView(){
        DatabaseHandler databaseHandler = new DatabaseHandler(this,null,null,1);
        ListView lv = findViewById(R.id.ingredientListView);

        final List<Ingredient> ingList = databaseHandler.getAllIngredient();
        if(ingList.size()<=0){
            Toast.makeText(this,"No ingredients found.",Toast.LENGTH_SHORT).show();
            return;
        }
        // parse into string format for display
        List<String> displayList = new ArrayList<>();
        for(Ingredient ing: ingList){
            displayList.add(ing.getName());
        }
        lv.setAdapter(new ArrayAdapter<>(this, R.layout.ingredient_list_view, displayList));
    }

    public void onClickBackIng(View v){
        finish();
    }
}