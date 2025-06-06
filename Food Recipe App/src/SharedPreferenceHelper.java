package com.example.foodrecipeapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SharedPreferenceHelper {
    private static final String PREF_NAME = "ShoppingPrefs";
    private static final String KEY_SHOPPING_LIST = "shopping_list";

    private SharedPreferences sharedPreferences;

    public SharedPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Save shopping list
    public void saveShoppingList(Set<String> shoppingList) {
        sharedPreferences.edit().putStringSet(KEY_SHOPPING_LIST, shoppingList).apply();
    }

    // Get shopping list
    public Set<String> getShoppingList() {
        return new HashSet<>(sharedPreferences.getStringSet(KEY_SHOPPING_LIST, new HashSet<>()));
    }

    // Add an item
    public void addItem(String item) {
        Set<String> list = getShoppingList();
        list.add(item);
        saveShoppingList(list);
    }

    // Remove an item
    public void removeItem(String item) {
        Set<String> list = getShoppingList();
        list.remove(item);
        saveShoppingList(list);
    }

    // Clear the entire list
    public void clearShoppingList() {
        sharedPreferences.edit().remove(KEY_SHOPPING_LIST).apply();
    }
}
