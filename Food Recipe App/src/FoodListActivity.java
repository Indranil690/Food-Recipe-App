package com.example.foodrecipeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.speech.RecognizerIntent;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.Locale;





public class FoodListActivity extends AppCompatActivity {
    public static List<FoodItem> foodList; // Removed duplicate declaration
    private FoodAdapter foodAdapter;
    private List<FoodItem> filteredList;
    private SearchView searchView;
    private Button btnViewFavorites, btnAll, btnStarter, btnMainCourse, btnDessert;
    private SharedPreferences sharedPreferences;
    private ImageButton btnVoiceSearch;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        btnVoiceSearch = findViewById(R.id.btnVoiceSearch);
        btnViewFavorites = findViewById(R.id.btnViewFavorites);
        btnAll = findViewById(R.id.btnAll);
        btnStarter = findViewById(R.id.btnStarter);
        btnMainCourse = findViewById(R.id.btnMainCourse);
        btnDessert = findViewById(R.id.btnDessert);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE);

        // Initialize food list only once
        if (foodList == null) {
            foodList = new ArrayList<>(Arrays.asList(
                    new FoodItem("Chicken Curry", R.drawable.chicken_curry, "Chicken, Turmeric Powder, Salt, Chicken Masala, Garam Masala, onions, Chili, tomatoes, Coriander leaves", "1. Marinate the chicken. \n2. Heat some oil,put One cup chopped Onion and Two green chili and Saute the onion till they turn golden. \n3. Add One table spoon Ginger Garlic paste and saute it Five minutes.\n4. Add Half cup of tomato,turmeric powder and salt,mix it until the gravy release it's oil.\n5.Add Half KG chicken and cook it for TEN minutes.\n6. Add some hot water and cover the pot for TEN minutes,set the gas in medium flame.\n7. Add Garam masala and Coriander leaves and boil it until it properly cooked.\n8. Enjoy your Desi style chicken curry !", "Main Course"),
                    new FoodItem("Dosa", R.drawable.dosa, "Rice, urad dal, salt, Oil", "1. Soak the rice and dal separately.\n2. Grind dal first into a smooth consistency adding enough water.\n3. Grind rice into a smooth batter by adding just enough water.\n4. Combine the two, add salt, and let it ferment in a large enough bowl lightly covered, for about 6-8 hours in a warm place.\n5.Add enough water to the fermented batter until you have a smooth, pouring consistency.\n6. Heat a tawa and grease with the oil lightly.Pour about Half cup batter to the centre of the tawa,gently spread the batter with a circular motion from the centre towards the sides of the tawa.\n7. Drizzle about 1 tsp of oil around the edges of the dosa and add a few sprinkles on the top as well.\n8. and flip it over. Cook the other side fo. Cook the other side for another 40 seconds or so and remove from pan.\n9. Serve with dosa podi, chutney, sambar, tiffin sambar and Enjoy !","Main Course"),
                    new FoodItem("Strawberry Cake", R.drawable.strawberry_cake, "Flour, sugar, eggs, strawberries, Butter, Baking Powder, Milk, Jell-o", "1. Beat the butter, sugar, and Jell-O together.\n2. Add the eggs.\n3. Add Flour, Baking Powder and mix it.\n4. Make the batter in thick consistency add some milk if needed.\n5. Add the chopped Strawberry.\n6. Bake at 180°C for 30 mins.\n7. Enjoy your Delicious home made Strawberry Cake !","Dessert"),
                    new FoodItem("Salad", R.drawable.salad, "Fresh Greens, tomatoes, cucumber, Onion, Lemon, Salt, Black Paper, Olive Oil", "1. Chop all vegetables.\n2. Add Salt, Black paper, Lemon juice, Little Olive Oil.\n3. Toss with dressing.\n4. Enjoy your Healthy Salad !.","Starter"),
                    new FoodItem("Biryani", R.drawable.biriyani, "Basmati rice, Whole spices , Chicken, Biriyani Masala,Ginger Garlic Paste,Ghee, Salt, yougart, Onion,Milk", "1.Marinate the chicken by mixing yogurt, ginger, garlic, turmeric, red chili powder, garam masala, salt, mint leaves, and lemon juice.\n2.Rinse and drain the rice 2 to 3 times,then soak in water for 20 minutes.\n3.add ghee to a heavy-bottomed pan and add onions, fry the onions until they start to turn golden brown.\n4.In a medium pot add 8 cups of water. Add whole spices and salt to the rice and bring the to a boil on high heat, cook the rice 70%.\n5.To the pan with the remaining ghee, add the marinated chicken. Cook on medium heat for 8 to 10 minutes, turning halfway through.\n6.Then, gently layer the partially cooked rice over the chicken,Top with caramelized onions and saffron-infused milk.\n7.Cover the pot and seal it with aluminum foil, then place some weight on top of the lid. Cook on low heat for 20 minutes.\n8.Finally open the cover and your tasty Biriyani is ready to eat ! .","Main Course"),
                    new FoodItem("Pasta", R.drawable.pasta, "Pasta, Butter , Salt, Spices, Flour, Milk", "1. add water to a pot and Cook the pasta on a medium to high heat.\n2.heat a saucepan, Keep the heat to low and add 2 tablespoons butter.\n3.When the butter melts and starts to bubble, add 1 tablespoon whole wheat flour.\n4.Using a wired whisk, stir the flour quickly as soon as you add it, Keep stirring so that no lumps are formed. 5.Keep heat on low and pour 1 cup cold or chilled milk in a gentle stream with one hand and stir the sauce mixture with the other.\n6. On low heat, simmer the sauce till it thickens. When the sauce has thickened well and coats the back of a spoon, add spices.\n7.Then, add the cooked penne pasta to the prepared white sauce add oregano or other herbs of your choice, mix it very well.\n8. Now Enjoy your pasta ! .","Main Course"),
                    new FoodItem("Sushi", R.drawable.sushi, "Rice, Vinegar, Sugar, Salt, Nori seaweed sheets, Avocado, Cucumber, Pickled ginger, Imitation crabmeat", "1.Bring water to a boil in a medium pot, stir in rice. Reduce heat to medium-low, cover, and simmer until rice is tender and water has been absorbed, 20 to 25 minutes.\n2.Mix rice vinegar, sugar, and salt in a small bowl. Gently stir into cooked rice in the pot and set aside.\n3.Lay nori sheets on a baking sheet,Heat nori in the preheated oven until warm, 1 to 2 minutes.\n4.Center 1 nori sheet on a bamboo sushi mat, spread a thin layer of rice on top, Arrange 1/4 of the crabmeat, avocado, cucumber, and pickled ginger over rice in a line down the center.\n5.Lift one end of the mat and roll it tightly over filling to make a complete roll. Repeat with remaining ingredients.\n6.Use a wet, sharp knife to cut each roll into 4 to 6 slices.\n7. Now Enjoy your tasty Sushi !","Main Course"),
                    new FoodItem("Brownie", R.drawable.brownie, "Sugar, Flour, Butter, Eggs, Cocoa powder, Vanilla, Baking powder, Salt, Walnuts", "1.Mix sugar, flour, melted butter, eggs, cocoa powder, vanilla, baking powder, and salt in a large bowl until combined.\n2.Preheat the oven to 350 degrees F (175 degrees C). Grease a 9x13-inch pan, Spread the batter into the prepared pan.\n3.Decorate with walnut halves.\n4.Bake in the preheated oven until top is dry and edges have started to pull away from the sides of the pan, about 20 to 30 minutes.\n5. Cool before slicing into squares. Enjoy Your Delicious Brownie !","Dessert"),
                    new FoodItem("Ice Cream", R.drawable.icecream, "Hot Milk, Sugar, Cream, Vanilla extract, Chocolate, Chocolate chips", "1.Stir the sugar into the milk until it dissolves completely.\n2.Put in the cream and vanilla extract and melted chocolate.\n3.Place in the refrigerator for 3 to 4 hours or for 30 minutes in the freezer.\n4.Enjoy Your Ice cream !","Dessert"),
                    new FoodItem("Misti Doi", R.drawable.misti_doi, "Milk, Sugar, Hang curd, Pistachios", "1.Pour milk in a kadhai and heat for around 10 minutes, remove it from heat, cover and keep aside.\n2. spread the sugar in a saucepan and heat on low-medium heat. Let it cook undisturbed till the sugar melts and starts to caramelise.\n3.add 1cup of the previously heated milk and cook while continuously stirring, Keep stirring till the entire caramel melts into the milk. 4.Add this mixture to the remaining milk and heat for another 2 mins.\n5.Cool down it little bit and Pour the entire milk mixture onto the hung curd and whisk well till everything is nicely combined.\n6. cover it with a lid or foil, Keep it in a warm place for 4-6 hrs or overnight.\n7.Refrigerate it and enjoy chilled. You can add chopped pistachios or other nuts of your choice as a garnish. ","Dessert"),
                    new FoodItem("Rasmalai", R.drawable.rasmalai, "Rasgulla, Milk, sugar, Almonds, Cardamom powder, Rose water", "1. To make Rasmalai, you will need 12 to 15 Rasgulla. You can either make them at home or you can use store bought ones.\n2.Boil water, then add 10 to 12 almonds. Cover and keep aside for 30 to 40 minutes.After 30 minutes, peel the almonds and slice them thinly. Set aside.\n3.take 1 liter of full fat whole milk in a thick bottomed pan or kadai, Bring the milk to a boil on medium heat.\n4. While the milk is coming to a boil, take 2 tablespoons of warm milk from the pan and add it to a small bowl. Add 8 to 10 saffron strands. Stir and keep aside.\n5.Once the milk has come to a boil, lower the heat and simmer the milk. Collect the floating cream and push it to the sides of the pan using a spoon.Continue to cook this way until the milk is reduced to half.\n6.Once the milk has reduced to half of its original volume, add 4 to 5 tablespoons of sugar, or according to your taste.\n7.Add teaspoon green cardamom powder,  Add most of the sliced almonds (and pistachios if you have used them), reserving a few for garnish.\n8.Take each rasgulla and gently apply pressure with a spatula so the sugar syrup is removed from it.\n9.Now place the squeezed rasgullas in the simmering milk,  Simmer for 2 to 3 minutes on low heat,Turn off the heat and add 1 to 2 teaspoons of either rose water.\n10. then chill thoroughly in the fridge in a covered bowl or container. Enjoy it !","Dessert"),
                    new FoodItem("Chicken Cutlet", R.drawable.chicken_cutlet, "Chicken breasts, Salt, Black Pepper, Garlic powder, Italian breadcrumbs, Parmesan cheese, Eggs, Flour, Vegetable Oil.", "1.Slice chicken breasts lengthwise in half to make them thinner.\n2.Cut into chicken cutlet size pieces. Season the chicken with a little bit of salt, pepper, and garlic powder.\n3.Combine the Italian breadcrumbs and Parmesan cheese in one container. Whisk together eggs in another container. Mix together flour and season with some salt, pepper, and garlic powder in a third container.\n4.Coat chicken in flour mixture, eggs, then breadcrumb mixture.\n5.Heat oil in a pan Once the oil is hot and sizzles when you drop in a piece of flour cook the chicken for a few minutes on each side or until they reach an internal temp of 165 and are golden brown.\n6. Enjoy your tasty cutlets ! ","Starter"),
                    new FoodItem("Paneer Satay", R.drawable.paneer_satay, "Paneer, Chili, Kashmiri red chili, Garlic, Ginger, Onion, Peanut, Jaggery, Coconut Milk, Salt, Oil.", "1.Make a fine paste of ginger, garlic, shallot and chili.\n2.Cut paneer in long rectangular shapes.Mix spice paste with oil, brown sugar and soy sauce and lightly brush over the paneer.\n3.Add peanuts, jaggery, spice paste, soy sauce and some cocounut milk to wet grinder jar. Blend it.\n4.Heat the oil, Add the blended peanut mixture and any more remaining spice paste.\n5.Taste for seasoning and add salt or sugar as needed.\n6.Just before serving, take some of this sauce, and thin out a bit with coconut milk to get the consistency you like.\n7.Heat a grill pan or grill.Gently insert skewers into the paneer and grill on both sides until crispy.\n8.Serve with the satay sauce and salad with a squeeze of lime and Enjoy it !","Starter"),
                    new FoodItem("Chicken Reshmi Kebab", R.drawable.chicken_reshmi_kebab, "Chicken, Ginger Garlic paste, Black paper , Cashew nut, Cream, Garam masala, Kasouri methi, Hang curd, Coriander leaves", "1.Marinate chicken pieces. Add ginger garlic green chili paste, lemon juice, black pepper in chicken. Mix everything and keep for 10 minutes.\n2.Take hung curd in a mixing bowl. Add cashew nut paste, fresh cream, salt,  garam masala, kasoori methi, chopped coriander leaves, Mix everything well to form batter.\n3.Add chicken pieces in batter and mix well. Cover and keep in refrigerator for overnight.\n4.Grill chicken in top rack for 15 minutes at 230 degree celsius. After 15 minutes, take out chicken from oven.\n5.Brush oil on both sides of chicken pieces. Again grill chicken in top rack for 15 minutes at 230 degree celsius. After 15 minutes grilling, Chicken Reshmi Kabab is ready.\n6.Now Enjoy it ! ","Starter")

            ));
        }

        // Initialize filtered list
        filteredList = new ArrayList<>(foodList);

        // Load favorite status
        loadFavorites();

        // Set adapter
        foodAdapter = new FoodAdapter(this, filteredList);
        recyclerView.setAdapter(foodAdapter);

        // Search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        // Voice Search Button Click
        btnVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        // Category button click listeners
        btnAll.setOnClickListener(v -> filterByCategory("All"));
        btnStarter.setOnClickListener(v -> filterByCategory("Starter"));
        btnMainCourse.setOnClickListener(v -> filterByCategory("Main Course"));
        btnDessert.setOnClickListener(v -> filterByCategory("Dessert"));

        // View Favorites button click
        btnViewFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(FoodListActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    // Start voice input
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak food name");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Voice input not supported", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle voice result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenText = result.get(0);
                searchView.setQuery(spokenText, true); // set and submit
            }
        }
    }


    // Method to filter food list
    private void filterList(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(foodList);
        } else {
            for (FoodItem food : foodList) {
                if (food.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(food);
                }
            }
        }
        foodAdapter.notifyDataSetChanged();
    }


    // Filter by category
    private void filterByCategory(String category) {
        filteredList.clear();
        if (category.equals("All")) {
            filteredList.addAll(foodList);
        } else {
            for (FoodItem food : foodList) {
                if (food.getCategory().equals(category)) {
                    filteredList.add(food);
                }
            }
        }
        foodAdapter.notifyDataSetChanged();
    }

    // Load favorite items from SharedPreferences
    private void loadFavorites() {
        Set<String> favorites = sharedPreferences.getStringSet("favorite_foods", new HashSet<>());
        for (FoodItem food : foodList) {
            food.setFavorite(favorites.contains(food.getName()));
        }
    }

    // Method to get FoodItem by name
    public static FoodItem getFoodByName(String name) {
        for (FoodItem food : foodList) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null; // If food is not found
    }
}
