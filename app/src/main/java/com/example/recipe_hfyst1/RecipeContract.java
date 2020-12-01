package com.example.recipe_hfyst1;

import android.net.Uri;

public final class RecipeContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recipeDatabase.db";
    public static final String AUTHORITY = "com.example.recipedatabase.provider.MyContentProvider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri RECIPE_URI = Uri.parse("content://"+AUTHORITY+"/recipe/");
    public static final Uri INGREDIENT_URI = Uri.parse("content://"+AUTHORITY+"/ingredient/");
    public static final Uri RECIPE_INGREDIENTS_URI = Uri.parse("content://"+AUTHORITY+"/recipeingredient/");

    //field names
    public static final String RECIPE_ID = "_id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INSTRUCTIONS = "instructions";
    public static final String RECIPE_RATING = "rating";

    public static final String INGREDIENT_ID = "_id";
    public static final String INGREDIENT_NAME = "ingredientname";

    public static final String RECIPE_INGREDIENTS_RECIPE_ID = "recipe_id";
    public static final String RECIPE_INGREDIENTS_INGREDIENT_ID = "ingredient_id";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/recipes.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/recipes.data.text";

    //create tables
    public static final String CREATE_RECIPES_TABLE = "CREATE TABLE recipes (" +
            "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR(128) NOT NULL," +
            "instructions VARCHAR(128) NOT NULL," +
            "rating INTEGER);";

    public static final String CREATE_INGREDIENTS_TABLE = "CREATE TABLE ingredients (" +
            "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "ingredientname VARCHAR(128) NOT NULL);";

    public static final String CREATE_RECIPE_INGREDIENT_TABLE = "CREATE TABLE recipe_ingredients (" +
            "recipe_id INT NOT NULL," +
            "ingredient_id INT NOT NULL," +
            "CONSTRAINT fk1 FOREIGN KEY (recipe_id) REFERENCES recipes (_id)," +
            "CONSTRAINT fk2 FOREIGN KEY (ingredient_id) REFERENCES ingredients (_id)," +
            "CONSTRAINT _id PRIMARY KEY (recipe_id, ingredient_id) );";
}
