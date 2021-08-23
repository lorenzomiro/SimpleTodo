package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //keys

    public static final String KEY_ITEM_TEXT = "item_text";

    public static final String KEY_ITEM_POSITION = "item_position";

    public static final int EDIT_TEXT_CODE = 20;

    //create a list of items

    List<String> items;

    //create elements from the design

    Button btnAdd;

    EditText etItem;

    RecyclerView rvItems;

    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add buttons (buttons, recycler view, etc.)

        btnAdd = findViewById(R.id.btnAdd);

        etItem = findViewById(R.id.etItem);

        rvItems = findViewById(R.id.rvItems);

        //load in items

        loadItems();

        ItemsAdapter.onLongClickListener onLongClickListener = new ItemsAdapter.onLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {

                //test for long click

                Log.d("MainActivity","Long click at position " + position);

                //delete item from model

                items.remove(position);

                //notify adapter of removed position

                itemsAdapter.notifyItemRemoved(position);

                Toast.makeText(getApplicationContext(), "Item was removed.", Toast.LENGTH_SHORT).show();

                saveItems();

            }

        };

        ItemsAdapter.onClickListener onClickListener = new ItemsAdapter.onClickListener() {
            @Override
            public void onItemClicked(int position) {
                //open up edit activity

                Log.d("MainActivity","Single click at position " + position);

                //create new activity

                Intent i = new Intent(MainActivity.this, EditActivity.class); //goes from main page to edit activity page

                //pass data that'll be edited

                i.putExtra(KEY_ITEM_TEXT, items.get(position));

                i.putExtra(KEY_ITEM_POSITION, position);

                //display activity

                startActivityForResult(i, EDIT_TEXT_CODE);

            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);

        rvItems.setAdapter(itemsAdapter);

        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String todoItem = etItem.getText().toString();

                etItem.getText().toString();

                //add items to model

                items.add(todoItem);

                //notify adapter that item has been inserted

                itemsAdapter.notifyItemChanged(items.size() - 1);

                //clear text

                etItem.setText("");

                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();

                saveItems();

            }
        });

    }

    //handle activity's result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {

            //get updated text value

            String itemText = data.getStringExtra(KEY_ITEM_TEXT);

            //find original position of edited item from position key

            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            //update model at right position with new item text

            items.set(position, itemText);

            //notify the adapter

            itemsAdapter.notifyItemChanged(position);

            //finalize/persist changes

            saveItems();

            Toast.makeText(getApplicationContext(), "Nice! Item updated successfully!", Toast.LENGTH_SHORT).show();

        } else {

            Log.w("MainActivity", "Unknown call to onActivityResult");

        }

    }

    private File getDataFile() {

        return new File(getFilesDir(), "data.txt");

    }

    //function that loads items by reading data file line-by-line

    private void loadItems() {

        try {

        items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset())); //need help

        } catch (IOException e) {

            Log.e("MainActivity", "Error reading items", e);

            items = new ArrayList<>();

        }

    }


    //function that saves items by writing them into data file

    private void saveItems(){

        try {

            FileUtils.writeLines(getDataFile(), items);

        } catch (IOException e) {

            Log.e("MainActivity", "Error writing items", e);

        }



    }

}