package com.codepath.todoapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by qunli on 7/8/17.
 */

public class ToDoItemProvider extends ContentProvider {

    public static final String LOG_TAG = ToDoItemProvider.class.getSimpleName();

    private static final int TODOITEMS = 100;

    private static final int TODOITEM_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ToDoItemContract.CONTENT_AUTHORITY,ToDoItemContract.PATH_TO_DO_ITEMS,TODOITEMS);
        sUriMatcher.addURI(ToDoItemContract.CONTENT_AUTHORITY,ToDoItemContract.PATH_TO_DO_ITEMS+"/#",TODOITEM_ID);
    }

    private ToDoItemDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new ToDoItemDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case TODOITEMS:
                cursor = database.query(ToDoItemContract.toDoItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TODOITEM_ID:
                selection = ToDoItemContract.toDoItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ToDoItemContract.toDoItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case TODOITEMS:
                return ToDoItemContract.toDoItemEntry.CONTENT_LIST_TYPE;
            case TODOITEM_ID:
                return ToDoItemContract.toDoItemEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI "+ uri+ " with match "+ match);
        }
    }

    private Uri insertItems(Uri uri, ContentValues values){
        String title = values.getAsString(ToDoItemContract.toDoItemEntry.COLUMN_TITLE);
        if (title == null){
            throw new IllegalArgumentException("Items requires a title");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(ToDoItemContract.toDoItemEntry.TABLE_NAME,null,values);

        if (id== -1){
            Log.e(LOG_TAG,"Failed to insert row for "+ uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri,id);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case TODOITEMS:
                return insertItems(uri,values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);

        switch (match){
            case TODOITEMS:
                rowsDeleted = database.delete(ToDoItemContract.toDoItemEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case TODOITEM_ID:
                selection = ToDoItemContract.toDoItemEntry._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ToDoItemContract.toDoItemEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for "+ uri);

        }

        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);

        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case TODOITEMS:
                return updateItem(uri, values,selection,selectionArgs);
            case TODOITEM_ID:
                selection = ToDoItemContract.toDoItemEntry._ID+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateItem(uri, values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for "+uri);
        }
    }

    private int updateItem(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        if (values.containsKey(ToDoItemContract.toDoItemEntry.COLUMN_TITLE)){
            String title = values.getAsString(ToDoItemContract.toDoItemEntry.COLUMN_TITLE);
            if (title==null){
                throw new IllegalArgumentException("ToDoItem requires a title");

            }
        }

        if (values.size() ==0){
            return  0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(ToDoItemContract.toDoItemEntry.TABLE_NAME,values,selection,selectionArgs);

        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);

        }
        return rowsUpdated;
    }
}
