package io.github.ccmagic.datarain.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.ccmagic.datarain.DataRainA;
import io.github.ccmagic.datarain.R;

public class DataRainAActivity extends AppCompatActivity {
    private DataRainA mDataRainA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_one);

        mDataRainA = findViewById(R.id.data_rain_a);
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mDataRainA.startRain();
    }
}
