package com.example.acer.home;

import com.android.volley.RequestQueue;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
/**
 * @Author   : Sarmad Noor
 * @BannerNo : B00799557
 * Date      : November 29, 2018
 * Referred : Lab Material
 */
public class SingletonRequestQueue {
    private static SingletonRequestQueue mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private SingletonRequestQueue (Context context)
    {
        mCtx = context.getApplicationContext();
        mRequestQueue= getRequestQueue();
    }
    public static synchronized SingletonRequestQueue getInstance (Context context)
    {
        if (mInstance ==null)
        {
            mInstance = new SingletonRequestQueue(context.getApplicationContext());
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue ()
    {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue (Request <T> request)
    {
        getRequestQueue().add(request);
    }

}
