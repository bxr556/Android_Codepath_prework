package com.codepath.todoapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;


import android.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.todoapp.data.ToDoItemContract;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int EDIT_ITEM_REQUEST_CODE = 1;

    private static final int TO_DO_ITEM_LOADER =0;

    //ArrayList<String> todoItems;
    //ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;


    ToDoItemCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        populateArrayItems();
        mCursorAdapter = new ToDoItemCursorAdapter(this,null);
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(mCursorAdapter);

        etEditText = (EditText)findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                //todoItems.remove(position);
                Uri currentUri = ContentUris.withAppendedId(ToDoItemContract.toDoItemEntry.CONTENT_URI,id);
                getContentResolver().delete(currentUri,null,null);
                mCursorAdapter.notifyDataSetChanged();
                //writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent editItemIntent = new Intent(MainActivity.this,EditItemActivity.class);

                Uri currentItemUri = ContentUris.withAppendedId(ToDoItemContract.toDoItemEntry.CONTENT_URI,id);
                editItemIntent.setData(currentItemUri);
//                editItemIntent.putExtra("position",position);
//                editItemIntent.putExtra("content",todoItems.get(position));
                //startActivityForResult(editItemIntent,EDIT_ITEM_REQUEST_CODE );
                startActivity(editItemIntent);
            }
        });

        getLoaderManager().initLoader(TO_DO_ITEM_LOADER,null,this);
    }

//    public void populateArrayItems(){
//        //readItems();
//        //aToDoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems );
//
//
//    }

//    private void readItems(){
//        File filesDir = getFilesDir();
//        File file = new File(filesDir,"todo.txt");
//        try{
//            todoItems = new ArrayList<String>(FileUtils.readLines(file));
//        }catch (FileNotFoundException e){
//            todoItems = new ArrayList<String>();
//        }catch(IOException e){
//
//        }
//    }

//    private void writeItems(){
//        File filesDir = getFilesDir();
//        File file = new File(filesDir,"todo.txt");
//        try{
//            FileUtils.writeLines(file,todoItems);
//        }catch (IOException e){
//
//        }
//    }

    public void onAddItem(View view){
        //aToDoAdapter.add(etEditText.getText().toString());
        ContentValues values = new ContentValues();
        values.put(ToDoItemContract.toDoItemEntry.COLUMN_TITLE,etEditText.getText().toString());
        etEditText.setText("");
        Uri newUri = getContentResolver().insert(ToDoItemContract.toDoItemEntry.CONTENT_URI,values);
        //writeItems();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE){
//            int position = data.getExtras().getInt("position",0);
//            String content = data.getExtras().getString("content");
//            //todoItems.set(position,content);
//            mCursorAdapter.notifyDataSetChanged();
//            //writeItems();
//        }
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ToDoItemContract.toDoItemEntry._ID,
                ToDoItemContract.toDoItemEntry.COLUMN_TITLE
        };

        return new CursorLoader(this,
                ToDoItemContract.toDoItemEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
