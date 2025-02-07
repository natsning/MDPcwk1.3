package com.example.recipe_hfyst1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewRateActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private RatingBar ratingBar;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        db = new DatabaseHandler(this,null,null,1);
        ratingBar = findViewById(R.id.ratingBar);

        // extract the recipe ID
        setAllText(getIntent().getIntExtra("id",-1));

    }

    /**
     * Queries database to get all the relevant details for a recipe using the recipe ID
     * using {@link DatabaseHandler#getRecipe} and {@link #setIngredientListView}
     * @param recipeID recipe ID
     */
    private void setAllText(int recipeID){
        recipe = db.getRecipe(recipeID);
        if(recipe!=null){
            TextView recipeName = findViewById(R.id.headerViewRate);
            TextView instructions = findViewById(R.id.textInstructions);

            recipeName.setText(recipe.getName());
            instructions.setText(recipe.getInstructions());
            ratingBar.setRating(recipe.getRating());

            setIngredientListView(recipeID);
        }
    }

    /**
     * Uses recipeID to get the recipe's ingredients with {@link DatabaseHandler#getRecipeIngredient}.
     * Extract the names of the returned Ingredients then populate it into a listView
     * See also {@link Ingredient}
     * @param recipeID recipe ID
     */
    private void setIngredientListView(int recipeID){
        ListView lv = findViewById(R.id.viewRateIngredientsList);
        List<Ingredient> ingList = db.getRecipeIngredient(recipeID);
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

    /**
     * Handles onClick back button to return to previous activity
     * @param v back button
     */
    public void onClickBackVR(View v){
        finish( );
    }

    /**
     * Handles onClick confirm button to confirm the rating value given.
     * The rating will then be updated to the recipe.
     * See also {@link Recipe}, {@link DatabaseHandler#updateRating}
     * @param v confirm button
     */
    public void onClickConfirmVR(View v){
        recipe.setRating((int)ratingBar.getRating());
        int rowsUpdated = db.updateRating(recipe);
        if(rowsUpdated>0){
            Toast.makeText(this,"Rating updated",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Rating not updated",Toast.LENGTH_SHORT).show();
        }
    }

}