package com.example.recipe_hfyst1;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private ContentResolver cr;

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, RecipeContract.DATABASE_NAME, factory, RecipeContract.DATABASE_VERSION);
        cr = context.getContentResolver();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RecipeContract.CREATE_RECIPES_TABLE);
        db.execSQL(RecipeContract.CREATE_INGREDIENTS_TABLE);
        db.execSQL(RecipeContract.CREATE_RECIPE_INGREDIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
