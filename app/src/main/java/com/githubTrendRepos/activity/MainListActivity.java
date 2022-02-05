package com.githubTrendRepos.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.error.VolleyError;
import com.githubTrendRepos.R;
import com.githubTrendRepos.adapter.MainListAdapter;
import com.githubTrendRepos.application.AppController;
import com.githubTrendRepos.interfaces.DataHandlerCallback;
import com.githubTrendRepos.model.GithubRepoModel;
import com.githubTrendRepos.network.ApiHandler;
import com.githubTrendRepos.network.ApiURL;
import com.githubTrendRepos.network.ErrorHelper;
import com.githubTrendRepos.utils.ConnectivityReceiver;
import com.githubTrendRepos.utils.Global;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainListActivity extends AppCompatActivity implements DataHandlerCallback, TextWatcher,
        ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.activity_main_list)
    ViewGroup viewGroup;
    @BindView(R.id.recyclerView)
    RecyclerView listView;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;

    //background
    @BindView(R.id.blank_txt)
    TextView blankTxt;
    @BindView(R.id.loader_layout)
    RelativeLayout loaderLayout;
    @BindView(R.id.blank_layout)
    RelativeLayout blankLayout;

    private MainListAdapter mainListAdapter;
    private ArrayList<GithubRepoModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);

        AppController.getInstance().setConnectivityListener(this);

        getRepositoryList();
        editSearch.addTextChangedListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void getRepositoryList() {
        isListVisibility(false, true, null);
        new ApiHandler(this).getResponseAndError(this, ApiURL.MAIN_LIST, ApiURL.MAIN_LIST);
    }

    private void setRepositoryAdapter(ArrayList<GithubRepoModel> arrayList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.exm);
        listView.setLayoutManager(layoutManager);
        mainListAdapter = new MainListAdapter(this, this, arrayList);
        listView.setLayoutAnimation(animation);
        listView.setAdapter(mainListAdapter);

    }

    private void isListVisibility(boolean isVisible, boolean isProgress, String text) {
        if (isVisible) {
            blankLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            loaderLayout.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.GONE);
            if (isProgress) {
                loaderLayout.setVisibility(View.VISIBLE);
                blankLayout.setVisibility(View.GONE);
            } else {
                loaderLayout.setVisibility(View.GONE);
                blankLayout.setVisibility(View.VISIBLE);
                blankTxt.setText(text);
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppController.getInstance().setConnectivityListener(this);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        loaderLayout.setVisibility(View.GONE);
        if (isConnected) {
            blankLayout.setVisibility(View.VISIBLE);
            blankTxt.setText(getString(R.string.no_connection));
            listView.setVisibility(View.GONE);
        } else {
            blankLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onSuccess(HashMap<String, Object> map) {
        isListVisibility(true, false, null);

        JSONArray listJson = (JSONArray) map.get(ApiURL.MAIN_LIST);
        if (listJson != null) {
            Type fooType = new TypeToken<ArrayList<GithubRepoModel>>() {
            }.getType();
            ArrayList<GithubRepoModel> array = new Gson().fromJson(listJson.toString(), fooType);
            if (array.size() > 0) {
                list = array;
                setRepositoryAdapter(list);
            } else {
                isListVisibility(false, false, "No repository. Try again later.");
            }
        }


    }

    @Override
    public void onFailure(HashMap<String, Object> map) {
        String text = "";
        VolleyError error = (VolleyError) map.get(Global.VOLLEY_ERROR);
        text = ErrorHelper.getErrorResponse(error, this);
        isListVisibility(false, false, text);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (s.length() > 0 && list != null && list.size() > 0) {
            ArrayList<GithubRepoModel> arrayList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String opponentName;
                opponentName = (list.get(i).getRepositoryName());

                if (opponentName.toLowerCase().contains(s.toString().toLowerCase()))
                    arrayList.add(list.get(i));
            }
            setRepositoryAdapter(arrayList);
        } else {
            setRepositoryAdapter(list);
            mainListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}