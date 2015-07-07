package ru.brucha.avitotestapp.server.interfaces;

import android.graphics.Bitmap;

/**
 * Created by Prog on 07.07.2015.
 */
public interface GetBitmapListener {
    void loadComplete(Bitmap result, String url);
    void loadError(Exception error);
}
