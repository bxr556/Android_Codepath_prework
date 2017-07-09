package com.codepath.todoapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by qunli on 7/8/17.
 */

public final class ToDoItemContract {

    //Empty constructor to prevent someone from accidentally instantiating the contract class.
    private ToDoItemContract(){}

    /**
     * A convenience string to use for the content authority
     */
    public static final String CONTENT_AUTHORITY = "com.codepath.todoapp";

    /**
     * base_content_uri
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    /**
     * Possible path for toDoItems.
     */
    public static final String PATH_TO_DO_ITEMS = "toDOItems";

    /**
     * Inner class for toDoItems database table.
     */
    public static final class toDoItemEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_TO_DO_ITEMS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_TO_DO_ITEMS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_TO_DO_ITEMS;

        public static final String TABLE_NAME = "toDoItems";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_TITLE = "title";

    }

}
