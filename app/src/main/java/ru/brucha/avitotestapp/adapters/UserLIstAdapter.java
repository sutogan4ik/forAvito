package ru.brucha.avitotestapp.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.brucha.avitotestapp.R;
import ru.brucha.avitotestapp.models.User;
import ru.brucha.avitotestapp.server.tasks.CancelableTask;

/**
 * Created by Prog on 06.07.2015.
 */
public class UserLIstAdapter extends RecyclerView.Adapter<UserLIstAdapter.ViewHolder> {
    private List<User> users;
    private Handler handler = new Handler();
    public UserLIstAdapter() {
        users = new ArrayList<>();
    }

    public void addAll(List<User> userList){
        users.addAll(userList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.initRow(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView avatar;
        private TextView login;
        private CancelableTask task;
        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            login = (TextView) itemView.findViewById(R.id.login);
            task = new CancelableTask(avatar);
        }

        public void initRow(User user) {
            login.setText(user.getLogin());
            task.execute(user.getAvatarUrl());
        }
    }
}
