package com.example.explorar.ui.chat;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OpenAI {
    private Context context;

    private String stringURLEndPoint = "https://api.openai.com/v1/completions";

    private String theKey = "";

    String stringOutput;

    public OpenAI(Context context) {
        this.context = context;
    }
    public void getResponse(String query, ResponseCallback callback){
        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("model", "gpt-3.5-turbo");

            JSONArray jsonArrayMessage = new JSONArray();
            JSONObject jsonObjectMessage = new JSONObject();

            jsonObjectMessage.put("role","user");
            jsonObjectMessage.put("content", query);

            jsonArrayMessage.put(jsonObjectMessage);

            jsonObject.put("messages", jsonArrayMessage);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                stringURLEndPoint,jsonObject,new com.android.volley.Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                String stringText = null;

                try {
                    stringText = response.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                }catch (JSONException e){
                    throw new RuntimeException(e);
                }
                stringOutput = stringOutput + stringText;
                callback.onResponse(stringOutput);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                callback.onError(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders()throws AuthFailureError {

                Map<String, String> mapHeader = new HashMap<>();
                mapHeader.put("Authorization", "Bearer " + theKey);
                mapHeader.put("Content-Type", "application/json");
                return mapHeader;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){
                return super.parseNetworkResponse(response);
            }
        };

        int intTimeoutPeriod = 60000;
        RetryPolicy retryPolicy =  new DefaultRetryPolicy(intTimeoutPeriod, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjectRequest.setRetryPolicy(retryPolicy);

        Volley.newRequestQueue(context.getApplicationContext()).add(jsonObjectRequest);
    }

}
