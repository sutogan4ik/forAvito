package ru.brucha.avitotestapp.server;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ru.brucha.avitotestapp.Utils;
import ru.brucha.avitotestapp.server.interfaces.GetBitmapListener;
import ru.brucha.avitotestapp.server.interfaces.GetJsonListener;
import ru.brucha.avitotestapp.server.interfaces.GetJsonModelListener;
import ru.brucha.avitotestapp.server.tasks.GetJsonModel;
import ru.brucha.avitotestapp.server.tasks.GetJsonTask;
import ru.brucha.avitotestapp.server.tasks.LoadAvatar;

/**
 * Created by Prog on 07.07.2015.
 */
public class Loader {
    private static Loader instance;

    public static Loader getInstance(){
        if(instance == null){
            instance = new Loader();
        }
        return instance;
    }

    private ExecutorService executorService;
    private LruCache<String, Bitmap> memoryCache;
    private Handler handler = new Handler();
    private Loader(){
        executorService = Executors.newCachedThreadPool();
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
            }
        };
    }

    public  void getJson(String url, GetJsonListener listener){
        executorService.submit(new GetJsonTask(url, listener));
    }

    public <T> void getJson(String url, GetJsonModelListener<T> listener){
        executorService.submit(new GetJsonModel(url, listener));
    }

    public Future loadImage(String url, final ImageView imageView){
        Future future;
        imageView.setImageBitmap(null);
        if(getBitmapFromMemCache(getKey(url, imageView)) == null) {
            future = executorService.submit(new LoadAvatar(url, new GetBitmapListener() {
                @Override
                public void loadComplete(Bitmap result, String url) {
                    displayImage(result, imageView);
                    Bitmap optimal = getOptimalBitmap(result, imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
                    addBitmapToMemoryCache(getKey(url, imageView), optimal);
                }

                @Override
                public void loadError(Exception error) {

                }
            }));
            return future;
        }else{
            displayImage(getBitmapFromMemCache(getKey(url, imageView)), imageView);
        }
        return null;
    }

    private void displayImage(final Bitmap bitmap, final ImageView imageView){
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    private String getKey(String url, ImageView imageView){
        return url + ":width:" + imageView.getMeasuredWidth() + ":height:" + imageView.getMeasuredHeight();
    }

    private Bitmap getOptimalBitmap(Bitmap original, int w, int h){
        return Utils.decodeSampledBitmapFromResource(original, w, h);
    }
}
