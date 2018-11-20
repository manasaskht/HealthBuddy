package com.example.acer.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * This class contains the code to fill the recipe card template with the data
 * @author  Abinaya Raja
 * @version 1.0
 * @since   20 November, 2018
 */
public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.MyViewHolder> {

    Context mContext;
    List<RecipeCard> recipeList;

    public RecipeCardAdapter(Context mContext, List<RecipeCard> albumList) {
        this.mContext = mContext;
        this.recipeList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card_template, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {
        RecipeCard recipe = recipeList.get(position);
        myViewHolder.txtRecipeName.setText(recipe.recipeName);
        myViewHolder.recipeThumbnail.setImageResource(recipe.recipeThumbnail);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRecipeName;
        public ImageView recipeThumbnail;

        public MyViewHolder(View view) {
            super(view);
            txtRecipeName = view.findViewById(R.id.txtRecipeName);
            recipeThumbnail = view.findViewById(R.id.imgRecipe);

        }
    }
}