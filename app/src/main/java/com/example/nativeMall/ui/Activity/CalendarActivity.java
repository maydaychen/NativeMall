package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

import com.example.nativeMall.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarActivity extends AppCompatActivity {

    @BindView(R.id.cv_date_selector)
    CalendarView mCvDateSelector;

    Long data;
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public Date nowdate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
//        data = mCvDateSelector.getDate();

        mCvDateSelector.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = i + "-" + (i1 + 1) + "-" + i2;
            try {
                Date date = format.parse(time);
                long a = date.getTime();
                String s = sdf.format(nowdate);
                Date date1 = sdf.parse(s);
                long b = date1.getTime();
                if (Math.abs(a - b) >= 24 * 60 * 60 * 1000) {
                    Intent intent = new Intent();
                    intent.putExtra("date", i + "-" + (i1 + 1) + "-" + i2);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

    }
}
