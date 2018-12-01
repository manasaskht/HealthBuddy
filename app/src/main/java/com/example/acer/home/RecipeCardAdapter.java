package com.example.acer.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        final RecipeCard recipe = recipeList.get(position);
        myViewHolder.txtRecipeName.setText(recipe.recipeName);
        //http://square.github.io/picasso/
        Picasso.with(mContext).load(recipe.recipeImageURL).into(myViewHolder.recipeImage);

        //https://stackoverflow.com/questions/52096024/open-different-activities-by-clicking-on-different-cards-in-cardview-recyclervi
        myViewHolder.recipeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //https://stackoverflow.com/questions/50090135/pass-data-fragment-to-fragment-in-the-same-activity

                Intent recipeDetail = new Intent(mContext, RecipeDetailActivity.class);
                recipeDetail.putExtra("recipeID", recipe.recipeID);
                mContext.startActivity(recipeDetail);

            }
        });    
	}

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRecipeName;
        public ImageView recipeImage;
        public CardView recipeCardView;

        public MyViewHolder(View view) {
            super(view);
            txtRecipeName = view.findViewById(R.id.txtRecipeName);
            recipeImage = view.findViewById(R.id.imgRecipe);
            recipeCardView = view.findViewById(R.id.recipeCardView);
        }
    }
}