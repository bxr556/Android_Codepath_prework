package com.codepath.todoapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.todoapp.data.ToDoItemContract;

public class EditItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_TO_DO_ITEM_LOADER=0;

    private Uri mCurrentItemUri;
    EditText etItemText;

    //private int position;
    //private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mCurrentItemUri = getIntent().getData();


        //position = getIntent().getIntExtra("position",0);
        //content = getIntent().getStringExtra("content");
        etItemText = (EditText)findViewById(R.id.etEditItemText);

        getLoaderManager().initLoader(EXISTING_TO_DO_ITEM_LOADER,null,this);
        //etItemText.setText(content);
        //etItemText.setSelection(content.length());

    }

    public void onSubmit(View v){
        EditText etItemText = (EditText)findViewById(R.id.etEditItemText);
        String newContent = etItemText.getText().toString();
        if (newContent.trim().length()==0){
            Toast.makeText(this,"Please provide some content",Toast.LENGTH_SHORT).show();

            return;
        }

        ContentValues values = new ContentValues();
        values.put(ToDoItemContract.toDoItemEntry.COLUMN_TITLE,newContent);
        int rowsAffected = getContentResolver().update(mCurrentItemUri,values, null,null);


        //Intent returnIntent = new Intent();
        //returnIntent.putExtra("content",newContent);
        //returnIntent.putExtra("position",position);
        //setResult(RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ToDoItemContract.toDoItemEntry._ID,
                ToDoItemContract.toDoItemEntry.COLUMN_TITLE
        };

        return new CursorLoader(this,
                mCurrentItemUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount()<1){
            return;
        }

        //This should be the only row in the cursor
        if (data.moveToFirst()){
            int titleColumnIndex = data.getColumnIndex(ToDoItemContract.toDoItemEntry.COLUMN_TITLE);
            String title = data.getString(titleColumnIndex);
            etItemText.setText(title);
            etItemText.setSelection(title.length());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        etItemText.setText("");
    }
}
