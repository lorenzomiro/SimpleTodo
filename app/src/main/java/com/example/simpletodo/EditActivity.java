package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText etItemEdit;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //declare items for edit text bar + save button

        etItemEdit = findViewById(R.id.etItemEdit);

        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit item");

        etItemEdit.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        //When user finishes editing, they click save button to save changes
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create an intent, which will contain results

                Intent intent = new Intent();

                //pass results (edited data)

                intent.putExtra(MainActivity.KEY_ITEM_TEXT, etItemEdit.getText().toString());

                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                //set intent results

                setResult(RESULT_OK, intent);

                //finish activity, close screen and return to main page

                finish();

            }
        });

    }
}