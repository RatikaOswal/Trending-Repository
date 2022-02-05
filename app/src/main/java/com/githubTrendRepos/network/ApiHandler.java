package com.githubTrendRepos.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.githubTrendRepos.interfaces.DataHandlerCallback;
import com.githubTrendRepos.utils.Global;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ApiHandler {

    private HashMap<String, Object> map = new HashMap<>();
    private DataHandlerCallback mDataHandler;

    public ApiHandler(DataHandlerCallback mDataHandler) {
        this.mDataHandler = mDataHandler;
    }

    public void getResponseAndError
            (final Context context, String url, final String responseKey) {
        Log.d("url", "" + ApiURL.BASE_URL + url);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiURL.BASE_URL + url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response", "" + response);
                map.put(responseKey, response);
                mDataHandler.onSuccess(map);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("error", "Error: " + error.toString());
                map.put(Global.VOLLEY_ERROR, error);
                mDataHandler.onFailure(map);
                error.printStackTrace();
            }
        }) {
            @Override
            public Map getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                Log.d("headers...", headers.toString());
                return headers;
            }

        };
        jsonArrayRequest.setShouldCache(false);
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(200 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}