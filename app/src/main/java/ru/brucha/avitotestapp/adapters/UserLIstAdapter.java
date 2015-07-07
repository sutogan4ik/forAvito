package ru.brucha.avitotestapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import ru.brucha.avitotestapp.R;
import ru.brucha.avitotestapp.models.User;

/**
 * Created by Prog on 06.07.2015.
 */
public class UserLIstAdapter extends RecyclerView.Adapter<UserLIstAdapter.ViewHolder> {
    private List<User> users;
    private DisplayImageOptions options;

    public UserLIstAdapter() {
        users = new ArrayList<>();
    }

    public void addAll(List<User> userList){
        users.addAll(userList);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.fail)
                .showImageOnFail(R.drawable.fail)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .cacheInMemory(true).build();
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
        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            login = (TextView) itemView.findViewById(R.id.login);
        }

        public void initRow(User user) {
            login.setText(user.getLogin());
            ImageLoader.getInstance().displayImage(user.getAvatarUrl(), avatar, options);
        }
    }
}
