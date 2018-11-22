package com.example.acer.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * This class contains the code to implement the List view which displays grocery name, quantity, expiry date and intake calories
 * @author  Sarmad Noor
 * @version 1.0
 * @since   20 November, 2018
 */
public class GroceriesFragment extends Fragment {

    RecyclerView groceryRecyclerView;
    ArrayList <GroceryCard> groceryList;
    GroceryCardAdapter groceryCardAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View GroceriesView =  inflater.inflate(R.layout.fragment_groceries,container,false);
        groceryRecyclerView = (RecyclerView) GroceriesView.findViewById(R.id.recyclerVwGroceries);
        groceryRecyclerView.setPaddingRelative(2, 2, 2, 2);
        groceryList = new ArrayList<GroceryCard>();
        groceryList.add(new GroceryCard("Bread",1,"1 day"));
        groceryList.add(new GroceryCard("Butter",1,"2 days"));

        groceryCardAdapter = new GroceryCardAdapter(this.getContext(),groceryList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        groceryRecyclerView.setLayoutManager(linearLayoutManager);
        groceryRecyclerView.setAdapter(groceryCardAdapter);
        return GroceriesView;
    }
}
