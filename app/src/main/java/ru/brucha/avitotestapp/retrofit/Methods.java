package ru.brucha.avitotestapp.retrofit;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import ru.brucha.avitotestapp.models.User;

/**
 * Created by Prog on 06.07.2015.
 */
public interface Methods {
    @GET("/users")
    void getUsers(Callback<List<User>> userList);
}
