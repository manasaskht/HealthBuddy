package com.example.acer.home;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class FitnessFragment extends Fragment {

    private TextView tvSteps, tvKilometers;
    private PieChart chart;
    private EditText etCalories;
    private Button btUpdateCalories;
    private float totalCalories;
    private float steps;
    private float kilometers;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fitness, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        tvSteps = view.findViewById(R.id.tvSteps);
        tvKilometers = view.findViewById(R.id.tvKilometers);
        etCalories = view.findViewById(R.id.etCalories);
        chart = view.findViewById(R.id.chart);
        btUpdateCalories = view.findViewById(R.id.btUpdateCalories);
        totalCalories = sharedPref.getFloat(getString(R.string.savedCalories),300);
        btUpdateCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCalories.getText() + "" == "") {
                    return;
                } else {
                    totalCalories = Float.parseFloat(etCalories.getText() + "");
                    editor.putFloat(getString(R.string.savedCalories) , totalCalories);
                    editor.commit();
                    updateChart();
                }
            }
        });
        updateChart();
        chart.invalidate();
    }

    public void onStart() {
        super.onStart();
        updateChart();
        chart.invalidate();
    }

    public void onResume() {
        super.onResume();
        updateChart();
        chart.invalidate();
    }

    public void onPause() {
        super.onPause();
        updateChart();
        chart.invalidate();
    }

    private float stepsToKm() {
        return steps * 0.000762f;
    }

    private float stepsToCalories() {
        return steps * 0.04f;
    }

    private void updateChart() {
        steps = sharedPref.getInt(getString(R.string.savedSteps), 0);
        kilometers = stepsToKm();
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(stepsToCalories(), "Cals Burned"));
        entries.add(new PieEntry(totalCalories - stepsToCalories(),
                "Cals Remaining"));
        tvSteps.setText("Steps:\n" + (int) steps);
        DecimalFormat df = new DecimalFormat("#.##");
        tvKilometers.setText("Distance:\n" + df.format(kilometers) + "km");
        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(new int[]{Color.rgb(51, 153, 255),
                Color.rgb(192, 0, 0)});
        set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(set);
        data.setValueTextSize(20);
        data.setValueTextColor(Color.rgb(0,0,0));
        chart.getDescription().setEnabled(false);
        chart.setEntryLabelTextSize(16);
        chart.setData(data);
        chart.setEntryLabelColor(Color.rgb(0,0,0));
        chart.setNoDataText("Error: No Data to Display!");
        chart.setCenterText("Calorie Consumption Goal:\n" + totalCalories);
        chart.animateXY(3000, 3000, Easing.EaseInQuart);
    }
}