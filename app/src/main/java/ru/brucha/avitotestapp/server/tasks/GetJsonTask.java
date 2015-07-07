package ru.brucha.avitotestapp.server.tasks;

import ru.brucha.avitotestapp.server.interfaces.GetJsonListener;

/**
 * Created by Prog on 07.07.2015.
 */
public class GetJsonTask extends GetTask {
    private GetJsonListener listener;
    public GetJsonTask(String stringUrl, GetJsonListener listener) {
        super(stringUrl);
        this.listener = listener;
    }

    @Override
    protected void loadComplete(String result) {
        listener.loadComplete(result, stringUrl);
    }

    @Override
    protected void loadError(Exception error) {
        listener.loadError(error);
    }
}
