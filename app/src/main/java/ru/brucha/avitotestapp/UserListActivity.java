package ru.brucha.avitotestapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ru.brucha.avitotestapp.adapters.DividerItemDecorator;
import ru.brucha.avitotestapp.adapters.UserLIstAdapter;
import ru.brucha.avitotestapp.models.User;
import ru.brucha.avitotestapp.server.Loader;
import ru.brucha.avitotestapp.server.interfaces.GetJsonModelListener;

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
        Loader.getInstance().getJson("https://api.github.com/users", new GetJsonModelListener<List<User>>() {
            @Override
            public void loadComplete(List<User> result, String url) {
                adapter.addAll(result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void loadError(Exception error) {
                error.printStackTrace();
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
