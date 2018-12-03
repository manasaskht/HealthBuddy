package com.example.acer.home;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
/**
 * This class contains the code for swipe functionality
 * @author  Sarmad Noor
 * @version 1.0
 * @since   30 November, 2018
 * Reference URL: https://www.androidhive.info/2017/09/android-recyclerview-swipe-delete-undo-using-itemtouchhelper/
 */
public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    public interface RecyclerItemTouchHelperListener
    {
        void onSwiped (RecyclerView.ViewHolder viewHolder,int direction,int position);
    }
    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs,int swipeDirs, RecyclerItemTouchHelperListener listener)
    {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
    }
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState)
    {
        if(viewHolder != null)
        {
            final View viewFront = ((GroceryCardAdapter.GroceryViewHolder) viewHolder).viewFront;
            getDefaultUIUtil().onSelected(viewFront);
        }
    }
    @Override
    public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,float dX,float dY,
                                int actionState, boolean isCurrentlyActive)
    {
        final View viewFront = ((GroceryCardAdapter.GroceryViewHolder) viewHolder).viewFront;
        getDefaultUIUtil().onDrawOver(canvas, recyclerView,viewFront,dX,dY,actionState,isCurrentlyActive);
    }
    @Override
    public void clearView(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder)
    {
        final View viewFront = ((GroceryCardAdapter.GroceryViewHolder) viewHolder).viewFront;
        getDefaultUIUtil().clearView(viewFront);
    }
    @Override
    public void onChildDraw (Canvas canvas,RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder, float dX, float dY,
                             int actionState, boolean isCurrentlyActive)
    {
        final View viewFront = ((GroceryCardAdapter.GroceryViewHolder) viewHolder).viewFront;
        getDefaultUIUtil().onDraw(canvas,recyclerView,viewFront,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public int convertToAbsoluteDirection (int flags, int layoutDirection)
    {
        return super.convertToAbsoluteDirection(flags,layoutDirection);
    }





}
