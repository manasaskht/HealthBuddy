package com.example.acer.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents the recipe card template
 * @author  Abinaya Raja
 * @version 1.0
 * @since   30 November, 2018
 */

public class RecipeDetailActivity extends AppCompatActivity {

    TextView txtRecipeName;
    ImageView imgRecipeImageURL;
    TextView txtIngredients;
    TextView txtPreparationTime;
    TextView txtPreparation;
    TextView txtSourceLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //https://stackoverflow.com/questions/46954409/how-to-send-data-from-fragment-to-another-activity
        int recipeID = getIntent().getIntExtra("recipeID", 0);
        //txtRecipeName = findViewById(R.id.recipeName);
        imgRecipeImageURL = findViewById(R.id.recipeImage);
        txtIngredients = findViewById(R.id.ingredients);
        txtPreparationTime = findViewById(R.id.preparationTime);
        txtPreparation = findViewById(R.id.preparation);
        txtSourceLink = findViewById(R.id.sourceLink);
        GetRecipeDetail(recipeID);
    }

    private RecipeDetail GetRecipeDetail(int recipeID) {
        String url = getResources().getString(R.string.azureApiUrl) + "Recipe?recipeID=" + recipeID;
        final RecipeDetail recipeDetail = new RecipeDetail();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    recipeDetail.RecipeName = response.getString("RecipeName");
                    recipeDetail.Ingredients = response.getString("Ingredients");
                    recipeDetail.PreparationTime = response.getString("PreparationTime");
                    recipeDetail.PreparationMethod = response.getString("PreparationMethod");
                    recipeDetail.RecipeImageURL = response.getString("RecipeImageURL");
                    recipeDetail.SourceLink = response.getString("SourceLink");
                    BindResponseToDetailView(recipeDetail);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), R.string.errorRetrievingRecipes, Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);
        return recipeDetail;
    }

    private void BindResponseToDetailView(RecipeDetail recipeDetail) {

        //txtRecipeName.setText(recipeDetail.RecipeName);
        Picasso.with(getApplicationContext()).load(recipeDetail.RecipeImageURL).fit().into(imgRecipeImageURL);
        txtIngredients.setText(recipeDetail.Ingredients);
        txtPreparationTime.setText(recipeDetail.PreparationTime);
        txtPreparation.setText(recipeDetail.PreparationMethod);
        txtSourceLink.setText(recipeDetail.SourceLink);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        return true;
    }

}
