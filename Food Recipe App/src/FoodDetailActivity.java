package com.example.foodrecipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class FoodDetailActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Get references to UI elements
        ImageView foodImageDetail = findViewById(R.id.foodImageDetail);
        TextView foodNameDetail = findViewById(R.id.foodNameDetail);
        TextView foodIngredients = findViewById(R.id.foodIngredients);
        TextView foodRecipe = findViewById(R.id.foodRecipe);
        ImageButton btnSpeak = findViewById(R.id.btnSpeak);
        Button btnAddToShoppingList = findViewById(R.id.btnAddToShoppingList); // NEW

        // Retrieve data from intent
        Intent intent = getIntent();
        String foodName = intent.getStringExtra("foodName");
        String ingredients = intent.getStringExtra("ingredients");
        String recipe = intent.getStringExtra("recipe");
        int imageResId = intent.getIntExtra("imageResId", 0);

        // Set data to views
        foodNameDetail.setText(foodName);
        foodIngredients.setText(ingredients);
        foodRecipe.setText(recipe);
        foodImageDetail.setImageResource(imageResId);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        // Handle speak button click
        btnSpeak.setOnClickListener(v -> {
            String content = "Ingredients: " + foodIngredients.getText().toString() +
                    ". Recipe: " + foodRecipe.getText().toString();
            textToSpeech.speak(content, TextToSpeech.QUEUE_FLUSH, null, null);
        });

        // Handle add to shopping list click
        btnAddToShoppingList.setOnClickListener(v -> {
            String ingredientsText = foodIngredients.getText().toString();
            String[] ingredientArray = ingredientsText.split(",|\\n");

            ArrayList<String> ingredientsList = new ArrayList<>();
            for (String ingredient : ingredientArray) {
                ingredient = ingredient.trim();
                if (!ingredient.isEmpty()) {
                    ingredientsList.add(ingredient);
                }
            }

            Intent shoppingListIntent = new Intent(FoodDetailActivity.this, ShoppingListActivity.class);
            shoppingListIntent.putStringArrayListExtra("ingredients", ingredientsList);
            startActivity(shoppingListIntent);
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
