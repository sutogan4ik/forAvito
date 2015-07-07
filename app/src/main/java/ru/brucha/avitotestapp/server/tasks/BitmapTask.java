package ru.brucha.avitotestapp.server.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Prog on 07.07.2015.
 */
public abstract class BitmapTask implements Runnable {
    protected String fileUrl;

    public BitmapTask(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 8190);

            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte)current);
            }
            byte[] imageData = baf.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            loadComplete(bitmap);
        }catch (Exception ex){
            loadError(ex);
        }
    }

    protected abstract void loadComplete(Bitmap result);
    protected abstract void loadError(Exception error);
}
