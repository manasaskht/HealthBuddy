package com.example.acer.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.acer.home.Model.DBRepository;
import com.example.acer.home.Model.GroceryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the code to implement the List view which displays grocery name, quantity, expiry date and intake calories
 * @author  Sarmad Noor
 * @version 1.0
 * @since   20 November, 2018
 */
public class GroceriesFragment extends Fragment {

    RecyclerView groceryRecyclerView;
    //ArrayList <GroceryCard> groceryList;
    GroceryCardAdapter groceryCardAdapter;
    List<GroceryModel> listAddedGrocery;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View GroceriesView =  inflater.inflate(R.layout.fragment_groceries,container,false);
        groceryRecyclerView = (RecyclerView) GroceriesView.findViewById(R.id.recyclerVwGroceries);
        groceryRecyclerView.setPaddingRelative(2, 2, 2, 2);
        //Initializing the DB object
        DBRepository groceryRepository =new DBRepository(getContext());
        listAddedGrocery = groceryRepository.ViewGroceries();
        constructRecyclerView();
        return GroceriesView;
    }
    public void constructRecyclerView ()
    {
        groceryCardAdapter = new GroceryCardAdapter(this.getContext(),listAddedGrocery);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        groceryRecyclerView.setLayoutManager(linearLayoutManager);
        groceryRecyclerView.setAdapter(groceryCardAdapter);
        groceryCardAdapter.setOnGroceryItemClickListener( new GroceryCardAdapter.onCardItemClickListener(){
            @Override
            public void onCardItemCLicked(int position) {
                //groceryList.get(position).changeText1("clicked");
                groceryCardAdapter.notifyItemChanged(position);

            }

            @Override
            public void onEditItemClick(int position) {
                Fragment editGrocery = new EditGrocery();
                Bundle args = new Bundle();
                try {
                    args.putString("GroceryName", listAddedGrocery.get(position).groceryName);
                    args.putString("expiryDate", listAddedGrocery.get(position).expiryDate);
                    args.putString("quantity", Integer.toString(listAddedGrocery.get(position).quantity));
                    args.putString("ID", Integer.toString(listAddedGrocery.get(position).baseID));
                    editGrocery.setArguments(args);
                    //Reference URL: https://stackoverflow.com/questions/14970790/fragment-getarguments-returns-null
                    getFragmentManager().beginTransaction().replace(R.id.frame_container, editGrocery).commit();
                }
                catch (Exception ex)
                {

                }
            }
        });
    }
}
