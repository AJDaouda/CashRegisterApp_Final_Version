package com.example.cashregisterapp.Model;

import android.app.Application;

public class MyApp extends Application {
    private StoreManager manager =new StoreManager();

    public StoreManager getManager() {
        return manager;
    }
}
