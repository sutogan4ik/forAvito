package ru.brucha.avitotestapp.server.interfaces;

/**
 * Created by Prog on 07.07.2015.
 */
public interface GetJsonListener {
    void loadComplete(String result, String url);
    void loadError(Exception error);
}
