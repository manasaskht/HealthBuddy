package com.example.acer.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.acer.home.Model.DBImplementer;
import com.example.acer.home.Model.DBRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the main class for the recipe view
 * @author  Abinaya Raja
 * @version 1.0
 * @since   20 November, 2018
 */
public class RecipesFragment extends Fragment {

    public static RecipesFragment newInstance() {
        RecipesFragment fragment = new RecipesFragment();
        return fragment;
    }
    private RecipeCardAdapter adapter;
    private RecyclerView recipeRecyclerView;
    private ArrayList<RecipeCard> recipeList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
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
        DBRepository dbImplementer= new DBRepository(getContext());

        List<String> uniqueGroceryList =dbImplementer.GetUniqueGroceryItem();
        String url = getResources().getString(R.string.azureApiUrl) + "Recipe?GroceryList=" + TextUtils.join(",",uniqueGroceryList);
        final ArrayList<RecipeCard> recipes = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            try{
                boolean matchFound = response.getBoolean("matchFoundWithGroceries");
                final JSONArray suggestedRecipes = response.getJSONArray("suggestedRecipes");
                int responseSize = suggestedRecipes.length();
                for (int i = 0; i < responseSize; i++)
                {
                    JSONObject recipe = suggestedRecipes.getJSONObject(i);
                    RecipeCard recipeCard = new RecipeCard(recipe.getInt("RecipeID"), recipe.getString("RecipeName"), recipe.getString("RecipeImageURL"));
                    recipes.add(recipeCard);
                }
            }
            catch(Exception ex)
            {
                Toast.makeText(getContext(), R.string.errorRetrievingRecipes, Toast.LENGTH_SHORT);
                ex.printStackTrace();
            }
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                recipeRecyclerView.setLayoutManager(layoutManager);
                recipeRecyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        SingletonRequestQueue.getInstance(getContext()).addToRequestQueue(request);
        return recipes;
    }
}
