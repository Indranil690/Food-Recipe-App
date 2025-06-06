package com.example.foodrecipeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<FoodItem> foodList;
    private Context context;
    private SharedPreferences sharedPreferences;

    public FoodAdapter(Context context, List<FoodItem> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.sharedPreferences = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem food = foodList.get(position);
        holder.foodName.setText(food.getName());
        holder.foodImage.setImageResource(food.getImageResId());

        // Load favorite status
        boolean isFavorite = isFavorite(food.getName());
        updateFavoriteIcon(holder.favoriteIcon, isFavorite);

        // Click to open FoodDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("foodName", food.getName());
            intent.putExtra("ingredients", food.getIngredients());
            intent.putExtra("recipe", food.getRecipe());
            intent.putExtra("imageResId", food.getImageResId());
            context.startActivity(intent);
        });

        // Click to toggle favorite
        holder.favoriteIcon.setOnClickListener(v -> {
            boolean newFavoriteStatus = !isFavorite(food.getName());
            setFavorite(food.getName(), newFavoriteStatus);
            updateFavoriteIcon(holder.favoriteIcon, newFavoriteStatus);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        ImageView foodImage, favoriteIcon;

        ViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            foodImage = itemView.findViewById(R.id.foodImage);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }
    }

    // Check if the food is in favorites
    private boolean isFavorite(String foodName) {
        Set<String> favorites = sharedPreferences.getStringSet("favorite_foods", new HashSet<>());
        return favorites.contains(foodName);
    }

    // Add/remove food from favorites
    private void setFavorite(String foodName, boolean isFavorite) {
        Set<String> favorites = sharedPreferences.getStringSet("favorite_foods", new HashSet<>());
        Set<String> updatedFavorites = new HashSet<>(favorites);

        if (isFavorite) {
            updatedFavorites.add(foodName);
        } else {
            updatedFavorites.remove(foodName);
        }

        sharedPreferences.edit().putStringSet("favorite_foods", updatedFavorites).apply();
    }

    // Update favorite icon appearance
    private void updateFavoriteIcon(ImageView favoriteIcon, boolean isFavorite) {
        if (isFavorite) {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_filled); // Filled heart ❤️
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_border); // Empty heart 🤍
        }
    }
}
