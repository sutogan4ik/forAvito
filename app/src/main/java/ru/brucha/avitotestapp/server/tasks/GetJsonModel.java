package ru.brucha.avitotestapp.server.tasks;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import ru.brucha.avitotestapp.server.interfaces.GetJsonModelListener;

/**
 * Created by Prog on 07.07.2015.
 */
public class GetJsonModel extends GetTask {
    private GetJsonModelListener listener;
    public GetJsonModel(String stringUrl, GetJsonModelListener listener) {
        super(stringUrl);
        this.listener = listener;
    }

    @Override
    protected void loadComplete(String result) {
        ParameterizedType type = (ParameterizedType) listener.getClass().getGenericInterfaces()[0];
        Type generic = type.getActualTypeArguments()[0];
        Gson gson = new Gson();
        Object obj = gson.fromJson(result, generic);
        listener.loadComplete(obj, stringUrl);
    }

    @Override
    protected void loadError(Exception error) {
        listener.loadError(error);
    }
}
