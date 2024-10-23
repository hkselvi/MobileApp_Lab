package com.example.week2;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class CustomAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final List<Animal> animals = new ArrayList<Animal>();
        animals.add(new Animal("Dog", R.mipmap.dog));
        animals.add(new Animal("Butterfly", R.mipmap.butterfly));
        animals.add(new Animal("Cat", R.mipmap.cat));
        animals.add(new Animal("Bear", R.mipmap.bear));

        setContentView(R.layout.activity_custom_adapter);

        final ListView listView = findViewById(R.id.listView);
        AnimalAdapter animalAdapter = new AnimalAdapter(this, animals);
        listView.setAdapter(animalAdapter);
    }
}