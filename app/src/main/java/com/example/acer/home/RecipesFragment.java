package com.example.acer.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * This class is the main class for the recipe view
 * @author  Abinaya Raja
 * @version 1.0
 * @since   20 November, 2018
 */
public class RecipesFragment extends Fragment {

    private RecipeCardAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<RecipeCard> recipeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Reference URL : https://stackoverflow.com/questions/30093111/findviewbyid-not-working-in-fragment
        View recipeView = inflater.inflate(R.layout.fragment_recipes, container,false);
        recyclerView = recipeView.findViewById(R.id.recyclerVwRecipes);


        recipeList = new ArrayList<RecipeCard>();
        recipeList.add(new RecipeCard("Sandwich", R.drawable.sandwich));
        recipeList.add(new RecipeCard("Salad", R.drawable.salad));
        recipeList.add(new RecipeCard("Pizza", R.drawable.pizza));
        adapter = new RecipeCardAdapter(this.getContext(), recipeList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return recipeView;
    }
}
