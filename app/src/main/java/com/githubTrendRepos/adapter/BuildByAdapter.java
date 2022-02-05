package com.githubTrendRepos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.githubTrendRepos.R;
import com.githubTrendRepos.model.UserModel;
import com.githubTrendRepos.utils.Global;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.githubTrendRepos.utils.Global.regular;

public class BuildByAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<UserModel> userlist;

    public BuildByAdapter(Activity activity, Context context, ArrayList<UserModel> userlist) {
        this.activity = activity;
        this.context = context;
        this.userlist = userlist;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_build_by, parent, false);
        viewHolder = new ViewHolder(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserModel bean = userlist.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(bean, position);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView userImg;
        TextView name;
        ViewGroup viewGroup;

        public ViewHolder(View itemView) {
            super(itemView);

            viewGroup = itemView.findViewById(R.id.item_build_by);
            name = itemView.findViewById(R.id.name);
            userImg = itemView.findViewById(R.id.profile_pic);
            itemView.setOnClickListener(this);

        }

        public void bind(UserModel bean, int position) {
            Global.setFont(viewGroup, regular);

            name.setText(bean.getUsername());

            Picasso.get().load(bean.getAvatar())
                    .placeholder(R.drawable.ic_profile_black)
                    .into(userImg);


        }

        @Override
        public void onClick(View v) {
            final int pos = getAbsoluteAdapterPosition();
            UserModel model = userlist.get(pos);
            switch (v.getId()) {
                default:
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(model.getUrl()));
                    activity.startActivity(i);
                    break;

            }
        }
    }

}
