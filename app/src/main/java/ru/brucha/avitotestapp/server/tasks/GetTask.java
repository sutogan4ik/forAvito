package ru.brucha.avitotestapp.server.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Prog on 07.07.2015.
 */
public abstract class GetTask implements Runnable {
    protected String stringUrl;

    public GetTask(String stringUrl) {
        this.stringUrl = stringUrl;
    }

    @Override
    public void run() {
        StringBuilder result = new StringBuilder("");
        try{
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
            loadError(e);
        }
        loadComplete(result.toString());
    }

    protected abstract void loadComplete(String result);
    protected abstract void loadError(Exception error);
}
