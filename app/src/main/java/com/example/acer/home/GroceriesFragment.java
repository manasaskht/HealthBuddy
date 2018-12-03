package com.example.acer.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class GroceriesFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    public static GroceriesFragment newInstance() {
        GroceriesFragment fragment = new GroceriesFragment();
        return fragment;
    }

    RecyclerView groceryRecyclerView;
    //ArrayList <GroceryCard> groceryList;
    GroceryCardAdapter groceryCardAdapter;
    List<GroceryModel> listAddedGrocery;
    View groceryScreenView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View GroceriesView =  inflater.inflate(R.layout.fragment_groceries,container,false);
        groceryScreenView = GroceriesView;
        groceryRecyclerView = (RecyclerView) GroceriesView.findViewById(R.id.recyclerVwGroceries);
        FloatingActionButton btnFloatAdd = GroceriesView.findViewById(R.id.btnFloatAdd);
        groceryRecyclerView.setPaddingRelative(2, 2, 2, 2);
        //Initializing the DB object
        DBRepository groceryRepository =new DBRepository(getContext());
        listAddedGrocery = groceryRepository.ViewGroceries();
        constructRecyclerView();
        //Delete functionality
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT, (RecyclerItemTouchHelper.RecyclerItemTouchHelperListener) this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(groceryRecyclerView);

        btnFloatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://stackoverflow.com/questions/50090135/pass-data-fragment-to-fragment-in-the-same-activity
                Intent addGroceryAct = new Intent(getContext(), AddGroceryActivity.class);
                getContext().startActivity(addGroceryAct);
            }
        });
        return GroceriesView;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        DBRepository groceryRepository =new DBRepository(getContext());
        listAddedGrocery = groceryRepository.ViewGroceries();
        constructRecyclerView();

    }
    // Method to recycler view layout for Grocery
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
                try {
                    // Sending the data to the Edit Grocery detail screen
                    Intent editGrocery = new Intent(getContext(), EditGroceryActivity.class);
                    editGrocery.putExtra("GroceryName", listAddedGrocery.get(position).groceryName);
                    editGrocery.putExtra("expiryDate", listAddedGrocery.get(position).expiryDate);
                    editGrocery.putExtra("quantity", Integer.toString(listAddedGrocery.get(position).quantity));
                    editGrocery.putExtra("ID", Integer.toString(listAddedGrocery.get(position).baseID));
                    editGrocery.putExtra("unit",listAddedGrocery.get(position).unit);
                    getContext().startActivity(editGrocery);
                }
                catch (Exception ex)
                {

                }
            }
        });
    }
    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * Reference URL: https://www.androidhive.info/2017/09/android-recyclerview-swipe-delete-undo-using-itemtouchhelper/
     */
    @Override
    public void onSwiped (RecyclerView.ViewHolder viewHolder , int direction, int position)
    {
        if(viewHolder instanceof GroceryCardAdapter.GroceryViewHolder)
        {
            String name = listAddedGrocery.get(viewHolder.getAdapterPosition()).getGroceryName();
            int RowID = listAddedGrocery.get(viewHolder.getAdapterPosition()).getBaseID();
            // backup of removed item for undo purpose
            final GroceryModel deletedItem = listAddedGrocery.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            groceryCardAdapter.removeItem(viewHolder.getAdapterPosition());
            DBRepository dbRepository = new DBRepository(getContext());
            dbRepository.DeleteGrocery(RowID);
            Snackbar snackbar = Snackbar.make(groceryScreenView,name + " removed from list",Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
