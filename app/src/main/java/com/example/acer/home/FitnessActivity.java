package com.example.acer.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class FitnessActivity extends AppCompatActivity {

    private TextView tvSteps, tvKilometers;
    private PieChart chart;
    private EditText etCalories;
    private Button btUpdateCalories;
    private float burntCalories;
    private float remainingCalories;
    private float totalCalories;
    private float steps;
    private float kilometers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_activity);

        tvSteps             = findViewById(R.id.tvSteps);
        tvKilometers        = findViewById(R.id.tvKilometers);
        etCalories          = findViewById(R.id.etCalories);
        btUpdateCalories    = findViewById(R.id.btUpdateCalories);
        chart               = findViewById(R.id.chart);

        chart.invalidate();
        setCalorieButtonOnClickListener();
    }

    protected  void setCalorieButtonOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (etCalories.getText()+"" == "") {
                    return;
                } else {
                    float newCalories = Float.parseFloat(etCalories.getText() + "");
                    totalCalories = newCalories;
                    updateChart();
                }
            }

        };
        findViewById(R.id.btUpdateCalories).setOnClickListener(listener);
    }

    private float stepsToKm() {
        return steps * 0.000762f;
    }

    private float calcCaloriesRemaining(){
        return totalCalories - burntCalories;
    }

    private float stepsToCalories(){
        return steps * 0.04f;
    }

    private void updateChart() {
        steps = MainActivity.stepCount;
        kilometers = stepsToKm();
        List<PieEntry> entries = new ArrayList<>();
        //These would be replaced with database calls
        entries.add(new PieEntry(stepsToCalories(), "Calories Burned"));
        entries.add(new PieEntry(totalCalories - stepsToCalories(),
                "Calories Remaining"));
        tvSteps.setText("Steps:\n"+(int) steps);
        tvKilometers.setText("Distance:\n"+kilometers+"km");
        PieDataSet set = new PieDataSet(entries,"");
        set.setColors(new int[] {Color.rgb(192,0,0),
                Color.rgb(51,153,255)});
        PieData data = new PieData(set);
        chart.setContentDescription("");
        chart.setEntryLabelTextSize(15);
        chart.setData(data);
        chart.setNoDataText("Error: No Data to Display!");
        chart.setCenterText("Calorie Consumption Goal:\n"+totalCalories);
        chart.animateXY(3000,3000, Easing.EaseInQuart);
    }

}
