package com.githubTrendRepos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.githubTrendRepos.R;
import com.githubTrendRepos.activity.ListDetailActivity;
import com.githubTrendRepos.model.GithubRepoModel;
import com.githubTrendRepos.utils.Global;

import java.util.ArrayList;

import static com.githubTrendRepos.utils.Global.bold;
import static com.githubTrendRepos.utils.Global.regular;

public class MainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<GithubRepoModel> repoList;

    public MainListAdapter(Activity activity, Context context, ArrayList<GithubRepoModel> arrayList) {
        this.activity = activity;
        this.context = context;
        this.repoList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_repository, parent, false);
        viewHolder = new ViewHolder(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GithubRepoModel bean = repoList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(bean, position);
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, star, lang, desc;
        ViewGroup viewGroup;
        LinearLayout detailLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            viewGroup = itemView.findViewById(R.id.item_repository);
            name = itemView.findViewById(R.id.item_title);
            star = itemView.findViewById(R.id.item_time);
            lang = itemView.findViewById(R.id.item_lang);
            desc = itemView.findViewById(R.id.item_desc);
            detailLayout = itemView.findViewById(R.id.contact_layout);

            itemView.setOnClickListener(this);
            detailLayout.setOnClickListener(this);

        }

        public void bind(GithubRepoModel bean, int position) {
            Global.setFont(viewGroup, regular);
            Global.setCustomFont(bold, lang);

            name.setText(bean.getUsername() + "/" + bean.getRepositoryName());
            String time = "";
            if (bean.getSince().equals("daily")) {
                time = "today";
            }
            star.setText(String.valueOf(bean.getStarsSince()) + " stars " + time);
            lang.setText(bean.getLanguage());
            desc.setText(bean.getDescription());

            if (bean.isSelected()) {
                Global.setCustomFont(Global.bold, name);
                viewGroup.setBackground(context.getResources().getDrawable(R.drawable.rect_solid_stoke));
            } else {
                Global.setCustomFont(regular, name);
                viewGroup.setBackground(context.getResources().getDrawable(R.drawable.rect_trans_white_stoke));
            }

        }

        @Override
        public void onClick(View v) {
            final int pos = getAbsoluteAdapterPosition();
            GithubRepoModel model = repoList.get(pos);
            switch (v.getId()) {

                case R.id.contact_layout:
                    Intent detail = new Intent(activity, ListDetailActivity.class);
                    detail.putExtra("model", model);
                    activity.startActivity(detail);
                    break;

                default:
                    setSelection(pos);
                    break;


            }
        }
    }

    private void setSelection(int pos) {
        repoList.get(pos).setSelected(true);
        notifyDataSetChanged();

    }
}
