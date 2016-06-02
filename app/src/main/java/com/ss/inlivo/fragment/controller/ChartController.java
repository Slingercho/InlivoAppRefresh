package com.ss.inlivo.fragment.controller;

import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.ss.inlivo.R;
import com.ss.inlivo.activity.MainActivity;
import com.ss.inlivo.model.Vitamins;

import java.util.ArrayList;

/**
 * Created by NAME on 30.5.2016 г..
 */
public class ChartController {

    private static ChartController instance;

    private MainActivity mActivity;
    public static ChartController getInstance(MainActivity activity) {
        if (instance == null) {
            synchronized (ChartController.class) {
                if (instance == null) {
                    instance = new ChartController(activity);
                }
            }
        }
        return instance;
    }

    private ChartController(MainActivity activity){
        this.mActivity = activity;
    }

    public BarData generateChart(Vitamins vitamins){

        //Step 1
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(Float.valueOf(vitamins.getVitaminA()), 0));
        entries.add(new BarEntry(Float.valueOf(vitamins.getVitaminB6()), 1));
        entries.add(new BarEntry(Float.valueOf(vitamins.getVitaminB12()), 2));
        entries.add(new BarEntry(Float.valueOf(vitamins.getVitaminC()), 3));
        entries.add(new BarEntry(Float.valueOf(vitamins.getVitaminD()), 4));
        entries.add(new BarEntry(Float.valueOf(vitamins.getVitaminE()), 5));
        entries.add(new BarEntry(Float.valueOf(vitamins.getVitaminK()), 6));

        //Step 2
        BarDataSet dataSet = new BarDataSet(entries, "vitamins");
        dataSet.setColor(ContextCompat.getColor(mActivity, R.color.chart_color));

        //Step 4
        return new BarData(generateChartLabels(), dataSet);
    }

    public BarData generateChart(){
        //Step 1
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(100f, 0));
        entries.add(new BarEntry(80f, 1));
        entries.add(new BarEntry(26f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(12f, 4));
        entries.add(new BarEntry(9f, 5));
        entries.add(new BarEntry(3f, 6));

        //Step 2
        BarDataSet dataSet = new BarDataSet(entries, "vitamins");
        dataSet.setColor(ContextCompat.getColor(mActivity, R.color.chart_color));

        //Step 4
        return new BarData(generateChartLabels(), dataSet);
    }

    private ArrayList<String> generateChartLabels(){

        ArrayList<String> labels = new ArrayList();
        labels.add("A");
        labels.add("B6");
        labels.add("B12");
        labels.add("C");
        labels.add("D");
        labels.add("E");
        labels.add("K");

        return labels;
    }

}
