package com.githubTrendRepos.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.githubTrendRepos.R;
import com.githubTrendRepos.adapter.BuildByAdapter;
import com.githubTrendRepos.model.GithubRepoModel;
import com.githubTrendRepos.utils.Global;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.githubTrendRepos.utils.Global.regular;

public class ListDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.activity_list_detail)
    ViewGroup viewGroup;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.item_title)
    TextView name;
    @BindView(R.id.item_stars)
    TextView stars;
    @BindView(R.id.item_forks)
    TextView forks;
    @BindView(R.id.back_image)
    ImageView backButton;
    @BindView(R.id.item_watcher)
    TextView watcher;
    @BindView(R.id.item_language)
    TextView language;
    @BindView(R.id.build_layout)
    LinearLayout buildLayout;
    @BindView(R.id.btn_visit)
    Button btnVisit;
    @BindView(R.id.btn_share)
    Button btShare;
    @BindView(R.id.build_list)
    RecyclerView buildList;

    private GithubRepoModel githubRepoModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        ButterKnife.bind(this);

        Global.setFont(viewGroup, regular);

        if (getIntent().hasExtra("model")) {
            githubRepoModel = (GithubRepoModel) getIntent().getSerializableExtra("model");
        }

        backButton.setOnClickListener(this);
        btShare.setOnClickListener(this);
        btnVisit.setOnClickListener(this);

        setData();


    }

    private void setData() {
        title.setText(githubRepoModel.getRepositoryName());
        name.setText(githubRepoModel.getRepositoryName() + "/" + githubRepoModel.getUsername());
        stars.setText(String.valueOf(githubRepoModel.getTotalStars()));
        forks.setText(String.valueOf(githubRepoModel.getForks()));
        watcher.setText(String.valueOf(githubRepoModel.getStarsSince()));
        language.setText(githubRepoModel.getLanguage());

        if (githubRepoModel.getBuiltBy().size() > 0) {
            buildLayout.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.exm);
            buildList.setLayoutManager(layoutManager);
            BuildByAdapter buildByAdapter = new BuildByAdapter(this, this, githubRepoModel.getBuiltBy());
            buildList.setLayoutAnimation(animation);
            buildList.setAdapter(buildByAdapter);
        } else {
            buildLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_image:
                onBackPressed();
                break;

            case R.id.btn_share:
                ShareCompat.IntentBuilder
                        .from(this)
                        .setType("text/plain")
                        .setChooserTitle("Share URL")
                        .setText(githubRepoModel.getUrl())
                        .startChooser();
                break;

            case R.id.btn_visit:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(githubRepoModel.getUrl()));
                startActivity(i);
                break;
        }
    }
}