package com.example.recipe_hfyst1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
        Log.d(TAG,"oncreate databasehandler");
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

    public void insertRecipe(Recipe recipe){
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RECIPE_NAME, recipe.getName());
        values.put(RecipeContract.RECIPE_INSTRUCTIONS, recipe.getInstructions());
        cr.insert(RecipeContract.RECIPE_URI, values);
    }
}
