package com.mcanererdem.instaparseapp;

import android.app.Application;

import com.parse.Parse;

public class ServerOption extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("gsktZO1aipqF7Q1ODXZdb4v8ahG8z64xVLwX66IF")
                .clientKey("45jif6sECKS6IrSNDb5NwHzhJVfsyW1NJJWJAFfs")
                .server("https://parseapi.back4app.com/")
                .build());
    }
}
