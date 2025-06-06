package com.example.foodrecipeapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity {
    private List<FoodItem> favoriteFoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadFavorites();
        recyclerView.setAdapter(new FoodAdapter(this, favoriteFoods));
    }

    private void loadFavorites() {
        SharedPreferences sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE);
        Set<String> favorites = sharedPreferences.getStringSet("favorite_foods", new HashSet<>());

        favoriteFoods.clear(); // Clear list to avoid duplicates

        for (String name : favorites) {
            for (FoodItem food : FoodListActivity.foodList) { // Access food list
                if (food.getName().equals(name)) {
                    favoriteFoods.add(food); // Add actual food item with correct image
                    break;
                }
            }
        }
    }
}
