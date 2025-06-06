package com.example.foodrecipeapp;

public class FoodItem {
    private String name;
    private int imageResId;
    private String ingredients;
    private String recipe;
    private String category;
    private boolean isFavorite;

    public FoodItem(String name, int imageResId, String ingredients, String recipe, String category) {
        this.name = name;
        this.imageResId = imageResId;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.category = category;
        this.isFavorite = false;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public String getIngredients() { return ingredients; }
    public String getRecipe() { return recipe; }
    public String getCategory() { return category; }
    public boolean isFavorite() { return isFavorite; }

    public void setFavorite(boolean favorite) { this.isFavorite = favorite; }
}
