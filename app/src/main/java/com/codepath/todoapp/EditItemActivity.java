package com.codepath.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    private int position;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        position = getIntent().getIntExtra("position",0);
        content = getIntent().getStringExtra("content");
        EditText etItemText = (EditText)findViewById(R.id.etEditItemText);
        etItemText.setText(content);
        etItemText.setSelection(content.length());

    }

    public void onSubmit(View v){
        EditText etItemText = (EditText)findViewById(R.id.etEditItemText);
        String newContent = etItemText.getText().toString();
        if (newContent.trim().length()==0){
            Toast.makeText(this,"Please provide some content",Toast.LENGTH_SHORT).show();

            return;
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra("content",newContent);
        returnIntent.putExtra("position",position);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
