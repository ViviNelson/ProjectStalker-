package com.appspot.blacktenure188118.telegram;

import com.appspot.blacktenure188118.system.Constants;
import com.appspot.blacktenure188118.system.Network;
import com.appspot.blacktenure188118.telegram.model.MessageSimple;
import com.appspot.blacktenure188118.telegram.model.Webhook;
import com.google.appengine.repackaged.com.google.gson.Gson;

import java.io.IOException;

public class TelegramSystem {

    private static Gson gson = new Gson();

    private static String getURL(String method){
        return String.format("https://api.telegram.org/bot%s/%s", Constants.BOT_TOKEN,method);
    }

    public static void sendMessage(long chat, String text) throws IOException {


        String url = getURL("sendMessage");
//        url+="disable_web_page_preview=true";

        MessageSimple message = new MessageSimple();
        message.chat_id = chat;
        message.text = text;

        String setMessage = Network.sendPost(url, gson.toJson(message));
        System.out.println(url);
        System.out.println(setMessage);
        System.out.println(gson.toJson(message));

    }

    public static void startWebHooks() throws IOException {
        String url = TelegramSystem.getURL("setWebhook");

        Webhook webhook = new Webhook();
        webhook.url = Constants.WEBHOOK_FULL_PATH;

        String setWebhook = Network.sendPost(url, gson.toJson(webhook));
        System.out.println(webhook);
    }

}
