package ru.brucha.avitotestapp.server.tasks;

import android.util.Log;
import android.widget.ImageView;

import java.util.concurrent.Future;

import ru.brucha.avitotestapp.server.Loader;

/**
 * Created by Prog on 07.07.2015.
 */
public class CancelableTask {
    private Future future;
    private ImageView imageView;

    public CancelableTask(ImageView imageView) {
        this.imageView = imageView;
    }

    public void execute(String url){
        if(future != null && !future.isDone()){
            future.cancel(true);
        }
        future = Loader.getInstance().loadImage(url, imageView);
    }
}
