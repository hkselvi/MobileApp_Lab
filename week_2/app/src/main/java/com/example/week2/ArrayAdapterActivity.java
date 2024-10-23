package com.example.week2;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ArrayAdapterActivity extends ListActivity {
    static final String[] ANIMALS =
            new String[]{"Dog", "Cat", "Butterfly", "Bear"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adapter classes in a bridge between view and the data
        setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_array_adapter,ANIMALS));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        //then click a row msg
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}