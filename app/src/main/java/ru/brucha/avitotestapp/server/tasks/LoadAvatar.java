package ru.brucha.avitotestapp.server.tasks;

import android.graphics.Bitmap;

import ru.brucha.avitotestapp.server.interfaces.GetBitmapListener;

/**
 * Created by Prog on 07.07.2015.
 */
public class LoadAvatar extends BitmapTask {
    private GetBitmapListener listener;

    public LoadAvatar(String fileUrl, GetBitmapListener listener) {
        super(fileUrl);
        this.listener = listener;
    }

    @Override
    protected void loadComplete(Bitmap result) {
        listener.loadComplete(result, fileUrl);
    }

    @Override
    protected void loadError(Exception error) {
        listener.loadError(error);
    }
}
