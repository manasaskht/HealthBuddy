package com.example.acer.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.home.Model.GroceryModel;

import java.util.List;

/**
 * This class contains the code to fill the grocery card template with the data
 * @author  Sarmad Noor
 * @version 1.0
 * @since   20 November, 2018
 */

public class GroceryCardAdapter extends RecyclerView.Adapter <GroceryCardAdapter.GroceryViewHolder> {

    Context mContext;
    private List <GroceryModel> groceryList;
    private onCardItemClickListener mItemListener;
 public GroceryCardAdapter (Context mContext, List<GroceryModel> groceryList)
 {
     this.groceryList = groceryList;
     this.mContext = mContext;
 }
    public interface onCardItemClickListener
    {
        void onCardItemCLicked (int position);
        void onEditItemClick (int position);
    }
    public void setOnGroceryItemClickListener(onCardItemClickListener listener)
    {
        mItemListener = listener;
    }
 @Override
 public GroceryViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
 {
     View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.groceries_card_template,parent, false);
     return new GroceryViewHolder(viewItem,mItemListener);
 }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder groceryViewHolder, int position) {
        GroceryModel groceryCard = groceryList.get(position);
        groceryViewHolder.vExpriryDate.setText(groceryCard.expiryDate);
        groceryViewHolder.vQuantity.setText(String.valueOf(groceryCard.quantity));
        groceryViewHolder.vGroceryName.setText(groceryCard.groceryName);
    }


    @Override
    public int getItemCount()
 {
     return groceryList.size();
 }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        protected TextView vGroceryName;
        protected TextView vQuantity;
        protected TextView vExpriryDate;
        protected ImageView vEditButton;

        public GroceryViewHolder(View groceryView, final onCardItemClickListener listener) {
            super(groceryView);
            vGroceryName = groceryView.findViewById(R.id.txtGroceryName);
            vQuantity = groceryView.findViewById(R.id.txtQuantity);
            vExpriryDate = groceryView.findViewById(R.id.txtExpiryDate);
            vEditButton  = groceryView.findViewById(R.id.imgEdit);
            groceryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                    {
                        int position =  getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onCardItemCLicked(position);
                        }
                    }
                }
            });
            vEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                    {
                        int position =  getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onEditItemClick(position);
                        }
                    }
                }
            });

        }

    }

}
