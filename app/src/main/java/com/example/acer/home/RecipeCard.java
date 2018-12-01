package com.example.acer.home;

/**
 * This class represents the recipe card template
 * @author  Abinaya Raja
 * @version 1.0
 * @since   20 November, 2018
 */
public class RecipeCard {
    int recipeID;
    String recipeName;
    String recipeImageURL;

    RecipeCard(int recipeID, String recipeName, String recipeImageURL){
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeImageURL = recipeImageURL;
    }
}
