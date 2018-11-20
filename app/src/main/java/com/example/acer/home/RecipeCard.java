package com.example.acer.home;

/**
 * This class represents the recipe card template
 * @author  Abinaya Raja
 * @version 1.0
 * @since   20 November, 2018
 */
public class RecipeCard {
    String recipeName;
    int recipeThumbnail;

    RecipeCard(String recipeName, int recipeThumbnail){
        this.recipeName = recipeName;
        this.recipeThumbnail = recipeThumbnail;
    }
}
