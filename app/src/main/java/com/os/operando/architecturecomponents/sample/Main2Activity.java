package com.os.operando.architecturecomponents.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, Main2Activity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getLifecycle().addObserver(new SampleObserver());
    }
}
