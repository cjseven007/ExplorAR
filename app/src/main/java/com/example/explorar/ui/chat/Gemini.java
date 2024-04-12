package com.example.explorar.ui.chat;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

public class Gemini {

    public void getResponse(String query, ResponseCallback callback, List<Message> messageList, JSONObject knowledgeBase){
        if (!messageList.isEmpty()) {
            Message lastMessage = messageList.get(messageList.size() - 1);
            lastMessage.setLoading(true);
        }
        GenerativeModelFutures model = getModel();

        StringBuilder fullQuery = new StringBuilder(query);
        // Append knowledge base content
        fullQuery.append(" ").append(convertJsonObjectToString(knowledgeBase));
        for (Message message : messageList) {
            fullQuery.append(" ").append(message.message);
        }
        Content content = new Content.Builder().addText(fullQuery.toString()).build();
        Executor executor = Runnable::run;

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {

            @Override
            public void onSuccess(GenerateContentResponse result){
                String resultText = result.getText();
                callback.onResponse(resultText);
                if (!messageList.isEmpty()) {
                    Message lastMessage = messageList.get(messageList.size() - 1);
                    lastMessage.setLoading(false);
                }
            }

            @Override
            public void onFailure(Throwable throwable){
                throwable.printStackTrace();
                callback.onError(throwable);
                if (!messageList.isEmpty()) {
                    Message lastMessage = messageList.get(messageList.size() - 1);
                    lastMessage.setLoading(false);
                }
            }
        }, executor);
    }


    private GenerativeModelFutures getModel(){
        String apiKey = "AIzaSyBxz_XQlwK1c4X4Od-f0tg2HQrpSHZoy2w";



        SafetySetting harrassmentSafety = new SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH);

        GenerationConfig.Builder configBuilder = new GenerationConfig.Builder();
        configBuilder.temperature = 0.9f;
        configBuilder.topK = 16;
        configBuilder.topP = 0.1f;
        configBuilder.maxOutputTokens = 300;
        GenerationConfig generationConfig = configBuilder.build();

        GenerativeModel gm = new GenerativeModel(
                "gemini-pro",
                apiKey,
                generationConfig,
                Collections.singletonList(harrassmentSafety)
        );

        return GenerativeModelFutures.from(gm);
    }

    private String convertJsonObjectToString(JSONObject jsonObject) {
        if (jsonObject == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                stringBuilder.append("\"").append(key).append("\":\"").append(jsonObject.getString(key)).append("\",");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Remove the trailing comma if exists
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
