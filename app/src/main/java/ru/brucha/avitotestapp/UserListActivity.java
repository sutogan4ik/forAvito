package ru.brucha.avitotestapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.brucha.avitotestapp.adapters.DividerItemDecorator;
import ru.brucha.avitotestapp.adapters.UserLIstAdapter;
import ru.brucha.avitotestapp.models.User;
import ru.brucha.avitotestapp.retrofit.API;

/**
 * Created by Prog on 06.07.2015.
 */
public class UserListActivity extends Activity {
    private RecyclerView recyclerView;
    private UserLIstAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        adapter = new UserLIstAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecorator(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startInit();
    }

    private void startInit(){
        if(Utils.isOnline(this)){
            loadUsers();
        }else{
            showAlertDialog(R.string.network_warning);
        }
    }

    private void loadUsers(){
        API.getMethods().getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> userList, Response response) {
                if (userList != null && userList.size() != 0) {
                    adapter.addAll(userList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAlertDialog(R.string.load_error);
                    }
                });
            }
        });
    }

    private void showAlertDialog(int messageId){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(messageId)
                .setPositiveButton(R.string.reload, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startInit();
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();
    }


}
