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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class is the main class for the recipe view
 * @author  Abinaya Raja
 * @version 1.0
 * @since   20 November, 2018
 */
public class RecipesFragment extends Fragment {

    private RecipeCardAdapter adapter;
    private RecyclerView recipeRecyclerView;
    private ArrayList<RecipeCard> recipeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Reference URL : https://stackoverflow.com/questions/30093111/findviewbyid-not-working-in-fragment
        View recipeView = inflater.inflate(R.layout.fragment_recipes, container,false);
        recipeRecyclerView = recipeView.findViewById(R.id.recyclerVwRecipes);

        recipeList = GetRecipes();
        adapter = new RecipeCardAdapter(this.getContext(), recipeList);

        return recipeView;
    }

    private ArrayList<RecipeCard> GetRecipes() {
        String url = getResources().getString(R.string.azureApiUrl) + "Recipe";
        final ArrayList<RecipeCard> recipes = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int responseSize = response.length();
                        for (int i = 0; i < responseSize; i++) {
                            try {
                                JSONObject recipe = response.getJSONObject(i);
                                RecipeCard recipeCard = new RecipeCard(recipe.getInt("RecipeID"),recipe.getString("RecipeName"), recipe.getString("RecipeImageURL"));
                                recipes.add(recipeCard);
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), R.string.errorRetrievingRecipes, Toast.LENGTH_SHORT);
                                e.printStackTrace();
                            }
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                            recipeRecyclerView.setLayoutManager(layoutManager);
                            recipeRecyclerView.setAdapter(adapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        SingletonRequestQueue.getInstance(getContext()).addToRequestQueue(request);
        return recipes;
    }
}
