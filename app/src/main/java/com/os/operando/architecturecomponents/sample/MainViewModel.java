package com.os.operando.architecturecomponents.sample;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private String message;

    public MainViewModel(String message) {
        this.message = message;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared");
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private String message;

        public Factory(String message) {
            this.message = message;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new MainViewModel(message);
        }
    }
}
