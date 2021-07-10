package com.example.android1_module3_tmdb.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Response;

public class Utils {
    private static final  String SESSION_ID_KEY = "session_id_key";

    public  static  void saveSessionId(String sessionId, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(SESSION_ID_KEY, sessionId)
                .apply();
    }

    public static String getSessionId(Context context) {
        return  PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SESSION_ID_KEY, null);
    }

    public static void showErrorFromServer(Response response, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            Toast.makeText(context, jsonObject.getString("status_message"), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
