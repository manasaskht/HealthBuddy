package com.example.acer.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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
        groceryRecyclerView.setPaddingRelative(2, 2, 2, 2);
        //Initializing the DB object
        DBRepository groceryRepository =new DBRepository(getContext());
        listAddedGrocery = groceryRepository.ViewGroceries();
        constructRecyclerView();
        //Delete functionality
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT, (RecyclerItemTouchHelper.RecyclerItemTouchHelperListener) this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(groceryRecyclerView);
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
