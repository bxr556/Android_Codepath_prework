package com.codepath.todoapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.todoapp.data.ToDoItemContract;

public class EditItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_TO_DO_ITEM_LOADER=0;

    private Uri mCurrentItemUri;
    EditText etTitle;
    DatePicker dpDueDate;
    EditText etNote;
    Spinner sPriority;
    Spinner sStatus;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                onSave();
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    //private int position;
    //private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mCurrentItemUri = getIntent().getData();


        //position = getIntent().getIntExtra("position",0);
        //content = getIntent().getStringExtra("content");
        etTitle = (EditText)findViewById(R.id.et_title);
        dpDueDate = (DatePicker)findViewById(R.id.datePicker) ;
        etNote = (EditText)findViewById(R.id.et_notes);
        sPriority = ( Spinner)findViewById(R.id.spinner_priority);
        sStatus = ( Spinner)findViewById(R.id.spinner_status) ;

        //etNote  = (EditText)findViewById(R.id.et)

        getLoaderManager().initLoader(EXISTING_TO_DO_ITEM_LOADER,null,this);
        //etTitle.setText(content);
        //etTitle.setSelection(content.length());

    }

    private void onSave(){
        EditText etItemText = (EditText)findViewById(R.id.et_title);
        String newContent = etItemText.getText().toString();
        if (newContent.trim().length()==0){
            Toast.makeText(this,"Please provide some content",Toast.LENGTH_SHORT).show();

            return;
        }

        StringBuilder sb_due_date = new StringBuilder();
        sb_due_date.append(String.valueOf(dpDueDate.getYear()));
        sb_due_date.append("-");
        sb_due_date.append(String.valueOf(dpDueDate.getMonth()));
        sb_due_date.append("-");
        sb_due_date.append(String.valueOf(dpDueDate.getDayOfMonth()));
        String due_date = sb_due_date.toString();

        String note = etNote.getText().toString();
        String priority = sPriority.getSelectedItem().toString();
        String status = sStatus.getSelectedItem().toString();


        ContentValues values = new ContentValues();
        values.put(ToDoItemContract.toDoItemEntry.COLUMN_TITLE,newContent);
        values.put(ToDoItemContract.toDoItemEntry.COLUMN_DUE_DATE,due_date);
        values.put(ToDoItemContract.toDoItemEntry.COLUMN_NOTES,note);
        values.put(ToDoItemContract.toDoItemEntry.COLUMN_PRIORITY,priority);
        values.put(ToDoItemContract.toDoItemEntry.COLUMN_STATUS,status);

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
                ToDoItemContract.toDoItemEntry.COLUMN_TITLE,
                ToDoItemContract.toDoItemEntry.COLUMN_DUE_DATE,
                ToDoItemContract.toDoItemEntry.COLUMN_NOTES,
                ToDoItemContract.toDoItemEntry.COLUMN_PRIORITY,
                ToDoItemContract.toDoItemEntry.COLUMN_STATUS
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
            etTitle.setText(title);
            etTitle.setSelection(title.length());


            int dueDateColumnIndex = data.getColumnIndex(ToDoItemContract.toDoItemEntry.COLUMN_DUE_DATE);
            String dueDate = data.getString(dueDateColumnIndex);
            if (dueDate != null && dueDate.contains("-")) {
                String[] dueDateSplited = dueDate.split("-");
                dpDueDate.updateDate(Integer.parseInt(dueDateSplited[0]), Integer.parseInt(dueDateSplited[1]), Integer.parseInt(dueDateSplited[2]));
            }else{

            }

            int noteColumnIndex = data.getColumnIndex(ToDoItemContract.toDoItemEntry.COLUMN_NOTES);
            String note = data.getString(noteColumnIndex);
            etNote.setText(note);


            int priorityColumnIndex = data.getColumnIndex(ToDoItemContract.toDoItemEntry.COLUMN_PRIORITY);
            String priority = data.getString(priorityColumnIndex);

            String[] priority_array = getResources().getStringArray(R.array.priority_arrays);
            int index = -1;
            for ( int i =0; i<priority_array.length;i++){
                if (priority_array[i].equals(priority)){
                    index =i;
                    break;
                }
            }
            sPriority.setSelection(index);


            int statusColumnIndex = data.getColumnIndex(ToDoItemContract.toDoItemEntry.COLUMN_STATUS);
            String status = data.getString(statusColumnIndex);
            String[] status_array = getResources().getStringArray(R.array.status_arrays);
            index = -1;
            for ( int i =0; i<status_array.length;i++){
                if (status_array[i].equals(status)){
                    index =i;
                    break;
                }
            }
            sStatus.setSelection(index);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        etTitle.setText("");
    }
}
