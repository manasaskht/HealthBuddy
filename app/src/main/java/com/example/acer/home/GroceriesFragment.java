package com.example.acer.home;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.acer.home.Model.DBRepository;
import com.example.acer.home.Model.GroceryModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class contains the code to implement the List view which displays grocery name, quantity, expiry date and intake calories
 * @author  Sarmad Noor,Manasa
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
    private PendingIntent pendingIntent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View GroceriesView =  inflater.inflate(R.layout.fragment_groceries,container,false);
        groceryScreenView = GroceriesView;
        groceryRecyclerView = (RecyclerView) GroceriesView.findViewById(R.id.recyclerVwGroceries);
        FloatingActionButton btnFloatAdd = GroceriesView.findViewById(R.id.btnFloatAdd);
        groceryRecyclerView.setPaddingRelative(2, 2, 2, 2);
        //Notification code starts
        Intent alarmIntent =new Intent(getActivity(),AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(),0,alarmIntent,0);
        String groceryItems= fetchExpiredItem();
        scheduleNotification(getNotification(groceryItems));
        //Notification code ends
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

	//https://gist.github.com/BrandonSmith/6679223
    private void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(getActivity(), AlarmReceiver.class);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 2);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int interval = 1000 * 60*20;
        /* Set the alarm to start at 08:00 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.AM_PM,Calendar.AM);
        
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
        //Working code
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
	
	//https://gist.github.com/BrandonSmith/6679223
    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle("Groceries are getting expired !");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.logo);
        //builder.setLargeIcon(((BitmapDrawable) this.getResources().getDrawable(R.drawable.mybuddylogo)).getBitmap());
        return builder.build();
    }

	//https://gist.github.com/BrandonSmith/6679223
    private String fetchExpiredItem()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-MM-yyyy");
        Date todayDate = Calendar.getInstance().getTime();
        String reportDate = simpleDateFormat.format(todayDate);
        DBRepository dbRepository = new DBRepository(getContext());
        List <String> lstExpiringProducts = dbRepository.GetExpiringGroceries(reportDate);
        String expiredItemsName =  TextUtils.join(",", lstExpiringProducts);
        return expiredItemsName;
    }
}
