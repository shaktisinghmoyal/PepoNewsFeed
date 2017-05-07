package com.pepo.news.data.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;


public class BaseMethodsForApiRestCalls {
    private final String Tag = "BaseMethodsForApiRestCalls";

    @Inject
    Context context;

    boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        return isConnected;
    }


}
