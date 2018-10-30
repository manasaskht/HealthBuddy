package com.example.acer.home;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginFragment {

    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static FragmentManager fragmentManager;

    SharedPreferences sp;
    SharedPreferences loginpreference;
}
