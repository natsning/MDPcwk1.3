package com.example.recipe_hfyst1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private ContentResolver cr;
    private final String TAG = "DatabaseHandler";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, RecipeContract.DATABASE_NAME, factory, RecipeContract.DATABASE_VERSION);
        cr = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RecipeContract.CREATE_RECIPE_TABLE);
        db.execSQL(RecipeContract.CREATE_INGREDIENT_TABLE);
        db.execSQL(RecipeContract.CREATE_RECIPE_INGREDIENT_TABLE);
        Log.d(TAG,"Tables created");
    }

    public List<Recipe> getAllRecipe(String sortOrder){
        List<Recipe> recipeList = new ArrayList<>();
        Cursor cursor;

        if(sortOrder.equals("Title") ){
            cursor = cr.query(RecipeContract.RECIPE_URI, null, null, null,
                    RecipeContract.RECIPE_NAME+" ASC");
            Log.d(TAG,"SELECT * Query executed");

        }else{
            cursor = cr.query(RecipeContract.RECIPE_URI, null, null, null,
                    RecipeContract.RECIPE_RATING+" DESC");
        }

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    Recipe r = new Recipe(cursor.getInt(cursor.getColumnIndex(RecipeContract.RECIPE_ID)),
                                    cursor.getString(cursor.getColumnIndex(RecipeContract.RECIPE_NAME)),
                                    cursor.getString(cursor.getColumnIndex(RecipeContract.RECIPE_INSTRUCTIONS)),
                                    cursor.getInt(cursor.getColumnIndex(RecipeContract.RECIPE_RATING))
                                );
                    recipeList.add(r);
                }while(cursor.moveToNext());
            }
        }

        return recipeList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insertRecipe(Recipe recipe){
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RECIPE_NAME, recipe.getName());
        values.put(RecipeContract.RECIPE_INSTRUCTIONS, recipe.getInstructions());
        Uri uriContainingID = cr.insert(RecipeContract.RECIPE_URI, values);

        if(uriContainingID!=null){
            return idExtractor(uriContainingID);
        }
        return -1;
    }

    public int checkIngredientExist(String ingredient_name){
        Cursor c = cr.query(RecipeContract.INGREDIENT_URI,null,
                RecipeContract.INGREDIENT_NAME+"= '"+ingredient_name+"'",
                null,null);
        if(c!=null && c.moveToFirst()){
            return c.getInt(c.getColumnIndex(RecipeContract.INGREDIENT_ID));
        }else{
            return -1;
        }
    }

    public int insertIngredient(String ingredient){
        ContentValues values = new ContentValues();
        values.put(RecipeContract.INGREDIENT_NAME, ingredient);
        Uri uriContainingID = cr.insert(RecipeContract.INGREDIENT_URI,values);

        if(uriContainingID!=null){
            return idExtractor(uriContainingID);
        }
        return -1;
    }

    public void insertRecipeIngredient(int r_id, int ing_id){
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RECIPE_INGREDIENTS_RECIPE_ID,r_id);
        values.put(RecipeContract.RECIPE_INGREDIENTS_INGREDIENT_ID,ing_id);
        cr.insert(RecipeContract.RECIPE_INGREDIENTS_URI,values);
    }

    private int idExtractor(Uri uri){
        int index = uri.toString().lastIndexOf("/");
        return Integer.parseInt(uri.toString().substring(index+1));
    }
}
