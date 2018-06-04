package io.github.ccmagic.datarain.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.ccmagic.datarain.DataRainB;
import io.github.ccmagic.datarain.R;

public class DataRainBActivity extends AppCompatActivity {
    DataRainB dataRainB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_two);


        dataRainB = findViewById(R.id.data_rain_b);
//        dataRainViewB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataRainViewB.startRain();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataRainB.startRain();
    }
}
