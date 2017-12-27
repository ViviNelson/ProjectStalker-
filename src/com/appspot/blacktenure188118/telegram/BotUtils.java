package com.appspot.blacktenure188118.telegram;

import com.appspot.blacktenure188118.telegram.model.Update;
import com.google.appengine.repackaged.com.google.gson.Gson;

import java.io.Reader;

public class BotUtils {

    private static Gson gson = new Gson();

    // парсинг json`а в объекты
    public static Update parseUpdate(String update) {
        return gson.fromJson(update, Update.class);
    }

    public static Update parseUpdate(Reader reader) {
        return gson.fromJson(reader, Update.class);
    }

}
