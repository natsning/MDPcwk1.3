package com.example.recipe_hfyst1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    private DatabaseHandler databaseHandler;
    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        databaseHandler = new DatabaseHandler(getContext(),null,null,1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // setting tables according to uri
        if (RecipeContract.RECIPE_URI.equals(uri)) {
            queryBuilder.setTables(RecipeContract.RECIPE_TABLE);
        } else if (RecipeContract.INGREDIENT_URI.equals(uri)) {
            queryBuilder.setTables(RecipeContract.INGREDIENT_TABLE);
        } else if (RecipeContract.RECIPE_INGREDIENTS_URI.equals(uri)) {
            queryBuilder.setTables(RecipeContract.RECIPE_INGREDIENT_TABLE);
        } else {
            throw new IllegalArgumentException("Unknown URI");
        }

        // uri matching
        int uriType = RecipeContract.uriMatcher.match(uri);

        database = databaseHandler.getReadableDatabase();

        // check for uri type
        switch(uriType){
            case RecipeContract.UM_RECIPE_ID:
                queryBuilder.appendWhere(RecipeContract.RECIPE_ID+ '=' + uri.getLastPathSegment());
                break;
            case RecipeContract.UM_INGREDIENT_ID:
                queryBuilder.appendWhere(RecipeContract.INGREDIENT_ID+ '=' + uri.getLastPathSegment());
                break;
            case RecipeContract.UM_RECIPE_TABLE:
            case RecipeContract.UM_INGREDIENT_TABLE:
            case RecipeContract.UM_RECIPE_INGREDIENT_TABLE:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        // execute sqlite command and return result
        Cursor cursor = queryBuilder.query(databaseHandler.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = RecipeContract.uriMatcher.match(uri);
        database = databaseHandler.getWritableDatabase();
        long id = 0;
        switch(uriType)
        {
            case RecipeContract.UM_RECIPE_TABLE:
                id = database.insert(RecipeContract.RECIPE_TABLE, null, values);
                break;
            case RecipeContract.UM_INGREDIENT_TABLE:
                id = database.insert(RecipeContract.INGREDIENT_TABLE,null,values);
                break;
            case RecipeContract.UM_RECIPE_INGREDIENT_TABLE:
                database.insert(RecipeContract.RECIPE_INGREDIENT_TABLE,null,values);
                id = -2;
            case RecipeContract.UM_RECIPE_ID:
            case RecipeContract.UM_INGREDIENT_ID:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(RecipeContract.RECIPE_TABLE + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = RecipeContract.uriMatcher.match(uri);
        database = databaseHandler.getWritableDatabase();

        int rowsUpdated = 0;
        switch (uriType) {
            case RecipeContract.UM_RECIPE_TABLE:
                rowsUpdated = database.update(RecipeContract.RECIPE_TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,
                null);
        return rowsUpdated;
    }
}
