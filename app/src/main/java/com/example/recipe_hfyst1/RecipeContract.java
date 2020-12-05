package com.example.recipe_hfyst1;

import android.content.UriMatcher;
import android.net.Uri;

public final class RecipeContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recipeDatabase.db";
    public static final String AUTHORITY = "com.example.recipe_hfyst1.MyContentProvider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);


    //field names
    public static final String RECIPE_TABLE = "recipe";
    public static final String RECIPE_ID = "_id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INSTRUCTIONS = "instructions";
    public static final String RECIPE_RATING = "rating";
    public static final Uri RECIPE_URI = Uri.parse("content://"+AUTHORITY+"/"+RECIPE_TABLE+"/");

    public static final String INGREDIENT_TABLE = "ingredient";
    public static final String INGREDIENT_ID = "_id";
    public static final String INGREDIENT_NAME = "ingredientname";
    public static final Uri INGREDIENT_URI = Uri.parse("content://"+AUTHORITY+"/"+INGREDIENT_TABLE+"/");

    public static final String RECIPE_INGREDIENT_TABLE = "recipe_ingredient";
    public static final String RECIPE_INGREDIENTS_RECIPE_ID = "recipe_id";
    public static final String RECIPE_INGREDIENTS_INGREDIENT_ID = "ingredient_id";
    public static final Uri RECIPE_INGREDIENTS_URI = Uri.parse("content://"+AUTHORITY+"/"+RECIPE_INGREDIENT_TABLE+"/");

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/recipes.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/recipes.data.text";

    //create tables
    public static final String CREATE_RECIPE_TABLE = "CREATE TABLE "+
            RECIPE_TABLE + "("+
            RECIPE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            RECIPE_NAME + " VARCHAR(128) NOT NULL," +
            RECIPE_INSTRUCTIONS + " VARCHAR(128) NOT NULL," +
            RECIPE_RATING + " INTEGER"+
            ")";

    public static final String CREATE_INGREDIENT_TABLE = "CREATE TABLE "+
            INGREDIENT_TABLE + " (" +
            INGREDIENT_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            INGREDIENT_NAME + " VARCHAR(128) NOT NULL"+
            ")";

    public static final String CREATE_RECIPE_INGREDIENT_TABLE = "CREATE TABLE "+
            RECIPE_INGREDIENT_TABLE + " (" +
                RECIPE_INGREDIENTS_RECIPE_ID + " INT NOT NULL," +
                RECIPE_INGREDIENTS_INGREDIENT_ID + " INT NOT NULL," +
                "CONSTRAINT fk1 FOREIGN KEY ("+ RECIPE_INGREDIENTS_RECIPE_ID + ") REFERENCES " +
                    RECIPE_TABLE + "("+ RECIPE_ID + ")," +
                "CONSTRAINT fk2 FOREIGN KEY ("+RECIPE_INGREDIENTS_INGREDIENT_ID +") REFERENCES "+
                    INGREDIENT_TABLE + "("+INGREDIENT_ID+")," +
                "CONSTRAINT _id PRIMARY KEY ("+ RECIPE_INGREDIENTS_RECIPE_ID + "," +
                    RECIPE_INGREDIENTS_INGREDIENT_ID + ")"+
            " )";


    public static final String JOINT_TABLE =
                    RECIPE_TABLE + " R " +
                    " LEFT OUTER JOIN " +
                        RECIPE_INGREDIENT_TABLE + " RI " +
                            "ON( "+
                            "R."+ RECIPE_ID + " = "+ "RI."+ RECIPE_INGREDIENTS_RECIPE_ID +
                            ")"+
                    " LEFT OUTER JOIN " +
                        INGREDIENT_TABLE + " I " +
                            "ON( " +
                            "RI." + RECIPE_INGREDIENTS_INGREDIENT_ID + "=" + "I." + INGREDIENT_ID +
                            ")";


    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final int UM_RECIPE_TABLE = 1;
    public static final int UM_RECIPE_ID = 2;
    public static final int UM_INGREDIENT_TABLE = 11;
    public static final int UM_INGREDIENT_ID = 12;
    public static final int UM_RECIPE_INGREDIENT_TABLE = 21;
    public static final int UM_RECIPE_INGREDIENT_ID = 22;
    static {
        uriMatcher.addURI(AUTHORITY, RECIPE_TABLE, UM_RECIPE_TABLE);
        uriMatcher.addURI(AUTHORITY, RECIPE_TABLE + "/#", UM_RECIPE_ID);
        uriMatcher.addURI(AUTHORITY, INGREDIENT_TABLE, UM_INGREDIENT_TABLE);
        uriMatcher.addURI(AUTHORITY, INGREDIENT_TABLE + "/#", UM_INGREDIENT_ID);
        uriMatcher.addURI(AUTHORITY, RECIPE_INGREDIENT_TABLE, UM_RECIPE_INGREDIENT_TABLE);
        uriMatcher.addURI(AUTHORITY, RECIPE_INGREDIENT_TABLE + "/#", UM_RECIPE_INGREDIENT_ID);
    }

}
