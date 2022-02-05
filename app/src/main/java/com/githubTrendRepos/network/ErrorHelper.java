package com.githubTrendRepos.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.githubTrendRepos.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ErrorHelper {

    public static String getErrorResponse(VolleyError error, Context context) {
        String json = null;
        JSONArray jsonArray = new JSONArray();

        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {

                case 401:
                case 409:
                case 500:
                case 404:
                    json = new String(response.data);
                    Log.e("error_json :", json);
                    try {
                        JSONObject obj = new JSONObject(json);
                        Log.e("error :", json);
                    } catch (Exception e) {
                        json = "Unknown Error";
                    }

                    if (json != null) {
                        Log.e("error :", json);
                        return json;
                    }
                    break;

                case 422:
                    json = new String(response.data);
                    Log.e("error_json :", json);
                    try {
                        JSONObject obj = new JSONObject(json);
                        Log.e("error :", json);
                    } catch (Exception e) {
                        json = "Unknown Error";
                    }

                    if (json != null) {
                        Log.e("error :", json);
                        return json;
                    }
                    break;
            }
            //Additional cases
        }

        if (error instanceof TimeoutError) {
            return context.getString(R.string.connection_time_out);
        } else if (error instanceof NoConnectionError) {
            return context.getString(R.string.connection_setting);
        } else if (error instanceof AuthFailureError) {
            return context.getString(R.string.auth_failed);
        } else if (error instanceof ServerError) {
            return context.getString(R.string.server_failure);
        } else if (error instanceof NetworkError) {
            return context.getString(R.string.network_failure);
        } else if (error instanceof ParseError) {
            return context.getString(R.string.parsing_failure);
        }
        return context.getString(R.string.server_failure);
    }

    public static String getErrorResponse(Exception exception) {
        if (exception instanceof SocketTimeoutException) {
            return "The connection has timed out. Please try again.";
        } else if (exception instanceof UnknownHostException) {
            return "Please check your connection setting.";
        } else
            return "The connection has timed out. Please try again.";
    }
}
