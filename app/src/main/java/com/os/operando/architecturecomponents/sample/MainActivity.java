package com.os.operando.architecturecomponents.sample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLifecycle().addObserver(new SampleObserver());

        // startActivity(Main2Activity.createIntent(this));

        final SampleLiveData sampleLiveData = new SampleLiveData();
        sampleLiveData.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        sampleLiveData.getUpperCaseMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleLiveData.changeData();
            }
        });

        //   MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        MainViewModel mainViewModel = ViewModelProviders.of(this, new MainViewModel.Factory("test")).get(MainViewModel.class);

        Log.d(TAG, mainViewModel.toString());

    }
}