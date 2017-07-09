package com.codepath.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.codepath.todoapp.data.ToDoItemContract;

/**
 * Created by qunli on 7/8/17.
 */

public class ToDoItemCursorAdapter extends CursorAdapter {

    public ToDoItemCursorAdapter(Context context, Cursor c){
        super(context,c,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleTextView = (TextView)view.findViewById(R.id.tv_title);

        int TitleColumnIndex = cursor.getColumnIndex(ToDoItemContract.toDoItemEntry.COLUMN_TITLE);

        String title = cursor.getString(TitleColumnIndex);

        titleTextView.setText(title);

    }
}
