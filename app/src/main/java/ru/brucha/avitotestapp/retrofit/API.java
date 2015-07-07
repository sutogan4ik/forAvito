package ru.brucha.avitotestapp.retrofit;

import retrofit.RestAdapter;

/**
 * Created by Prog on 06.07.2015.
 */
public class API {
    private static API instance;

    public static synchronized API getInstance(){
        if(instance == null){
            instance = new API();
        }
        return instance;
    }

    private API(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();
        methods = restAdapter.create(Methods.class);
    }

    public static Methods getMethods(){
        return getInstance().methods;
    }

    private Methods methods;
}
