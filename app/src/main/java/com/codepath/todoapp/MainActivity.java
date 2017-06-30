package com.codepath.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_ITEM_REQUEST_CODE = 1;

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        populateArrayItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);

        etEditText = (EditText)findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent editItemIntent = new Intent(MainActivity.this,EditItemActivity.class);
                editItemIntent.putExtra("position",position);
                editItemIntent.putExtra("content",todoItems.get(position));
                startActivityForResult(editItemIntent,EDIT_ITEM_REQUEST_CODE );
            }
        });
    }

    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems );

    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }catch (FileNotFoundException e){
            todoItems = new ArrayList<String>();
        }catch(IOException e){

        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(file,todoItems);
        }catch (IOException e){

        }
    }

    public void onAddItem(View view){
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE){
            int position = data.getExtras().getInt("position",0);
            String content = data.getExtras().getString("content");
            todoItems.set(position,content);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
