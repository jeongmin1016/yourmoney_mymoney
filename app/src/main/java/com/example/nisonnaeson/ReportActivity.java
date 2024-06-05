package com.example.nisonnaeson;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private HorizontalBarChart barChart;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Parent LinearLayout
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        parentLayout.setBackgroundColor(Color.parseColor("#F0F8FF")); // light cyan background
        parentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        // Toolbar
        LinearLayout toolbarLayout = new LinearLayout(this);
        toolbarLayout.setOrientation(LinearLayout.HORIZONTAL);
        toolbarLayout.setBackgroundColor(Color.parseColor("#6db33f"));
        toolbarLayout.setPadding(20, 20, 20, 20);
        toolbarLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ImageView backButton = new ImageView(this);
        backButton.setImageResource(R.drawable.baseline_arrow_back_ios_24); // replace with your back icon
        backButton.setColorFilter(Color.WHITE); // Set icon color to white
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes the current activity and returns to the previous one
            }
        });
        toolbarLayout.addView(backButton);
        TextView titleView = new TextView(this);
        titleView.setText("             독일 여행");
        titleView.setTextSize(32);
        titleView.setTextColor(Color.WHITE);
        titleView.setPadding(20, 0, 0, 0);
        titleView.setTypeface(ResourcesCompat.getFont(this, R.font.nanumpenscript_regular)); // Set custom font for title
        toolbarLayout.addView(titleView);

        parentLayout.addView(toolbarLayout);

        // Bar chart for user balances
        barChart = new HorizontalBarChart(this);
        LinearLayout.LayoutParams barChartParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, convertToPx(200));
        barChartParams.setMargins(0, 0, 0, 20); // Adjusted top margin
        barChart.setLayoutParams(barChartParams);
        parentLayout.addView(barChart);

        // Pie chart
        pieChart = new PieChart(this);
        LinearLayout.LayoutParams pieChartParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, convertToPx(300)); // Set your desired width and height
        pieChartParams.setMargins(0, 20, 0, 20);
        pieChart.setLayoutParams(pieChartParams);
        parentLayout.addView(pieChart);

        // Save button
        Button saveButton = new Button(this);
        saveButton.setText("나의 가계부에 담기");
        saveButton.setBackgroundColor(Color.parseColor("#D9EAD3")); // light green background
        saveButton.setTextColor(Color.BLACK);
        saveButton.setGravity(Gravity.CENTER); // Center text
        saveButton.setPadding(20, 10, 20, 10); // Padding inside button
        saveButton.setTextSize(16); // Text size

        // Set button border
        saveButton.setBackground(getResources().getDrawable(R.drawable.button_border));

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.gravity = Gravity.CENTER_HORIZONTAL; // Center horizontally
        buttonParams.setMargins(90, 120, 90, 20); // Adjusted margin
        saveButton.setLayoutParams(buttonParams);
        parentLayout.addView(saveButton);

        setContentView(parentLayout);

        // Initialize charts with dummy data
        updateBarChart();
        updatePieChart();
    }

    private void updateBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 5900));
        entries.add(new BarEntry(1, -8500));
        entries.add(new BarEntry(2, 1800));

        BarDataSet dataSet = new BarDataSet(entries, "User Balances");
        dataSet.setColors(new int[]{Color.parseColor("#bcdaa8"),Color.parseColor("#eebebe"), Color.parseColor("#bcdaa8")});
        dataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(dataSet);

        barChart.setData(barData);
        barChart.invalidate(); // refresh

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        XAxis xAxis = barChart.getXAxis();

        leftAxis.setDrawLabels(false); // Remove left Y-axis labels
        rightAxis.setDrawLabels(false); // Remove right Y-axis labels
        xAxis.setDrawLabels(false); // Remove X-axis labels

        leftAxis.setDrawGridLines(false); // Remove grid lines
        rightAxis.setDrawGridLines(false); // Remove grid lines
        xAxis.setDrawGridLines(false); // Remove grid lines

        leftAxis.setDrawAxisLine(false); // Remove top border
        rightAxis.setDrawAxisLine(false); // Remove bottom border

        barChart.getDescription().setEnabled(false); // Remove description label
        barChart.getLegend().setEnabled(false); // Remove legend

        barChart.setDrawBorders(false); // 경계선 제거
        barChart.setDrawGridBackground(false); // 그리드 배경 제거

        // Custom renderer to draw values inside bars and names next to bars
        barChart.setRenderer(new CustomBarChartRenderer(barChart, barChart.getAnimator(), barChart.getViewPortHandler(), this));
    }

    private void updatePieChart() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40, "식비"));
        entries.add(new PieEntry(30, "교통"));
        entries.add(new PieEntry(20, "숙박"));
        entries.add(new PieEntry(10, "기타"));

        PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
        dataSet.setColors(new int[]{
                Color.parseColor("#e6c0ff"),
                Color.parseColor("#b8ffcc"),
                Color.parseColor("#ffdca7"),
                Color.parseColor("#ff8e8f")
        }); // Example colors
        dataSet.setDrawValues(false); // 숫자 숨기기
        dataSet.setValueTextColor(Color.parseColor("#262625")); // 텍스트 색상 검은색으로 설정

        dataSet.setSliceSpace(1f); // 조각 간의 간격 설정
        dataSet.setValueLineColor(Color.parseColor("#262625")); // 경계선 색상 검은색으로 설정

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.setDrawHoleEnabled(false); // Remove the hole in the center
        pieChart.getDescription().setEnabled(false); // Remove description label
        pieChart.getLegend().setEnabled(false); // Remove legend
        pieChart.invalidate(); // refresh
    }


    private int convertToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}

