package ru.brucha.avitotestapp.server.interfaces;

/**
 * Created by Prog on 07.07.2015.
 */
public interface GetJsonModelListener<T> {
    void loadComplete(T result, String url);
    void loadError(Exception error);
}
