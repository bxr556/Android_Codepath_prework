package com.codepath.todoapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qunli on 7/8/17.
 */

public class ToDoItemDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ToDoItemDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "todo.db";

    private static final int DATABASE_VERSION = 1;

    public ToDoItemDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TODO_TABLE = "CREATE TABLE "+ ToDoItemContract.toDoItemEntry.TABLE_NAME + "("
                + ToDoItemContract.toDoItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ToDoItemContract.toDoItemEntry.COLUMN_TITLE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NA in this case.

    }
}
