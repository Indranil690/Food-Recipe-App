package com.example.foodrecipeapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    private ListView shoppingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingListView = findViewById(R.id.shoppingListView);

        // Get ingredients passed from intent
        ArrayList<String> ingredients = getIntent().getStringArrayListExtra("ingredients");

        // Set up ListView with ingredients
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredients);
        shoppingListView.setAdapter(adapter);
    }
}
