package com.os.operando.architecturecomponents.sample;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

public class SampleLiveData {

    private final MutableLiveData<String> message = new MutableLiveData<>();

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<String> getUpperCaseMessage() {
        return Transformations.map(message, new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input.toUpperCase();
            }
        });
    }

    public void changeData() {
        message.setValue("change data");
    }
}