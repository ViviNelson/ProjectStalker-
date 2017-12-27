package com.appspot.blacktenure188118.servlets;

import com.appspot.blacktenure188118.entities.TelegramUser;
import com.appspot.blacktenure188118.instagram.InstagramSystem;
import com.appspot.blacktenure188118.system.Network;
import com.appspot.blacktenure188118.telegram.BotUtils;
import com.appspot.blacktenure188118.telegram.TelegramSystem;
import com.appspot.blacktenure188118.telegram.model.Update;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class WebHookServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        String body = Network.getBody(request);
        System.out.println(body);

        Update update = BotUtils.parseUpdate(body);

        String out = "";

        if(update.message()!=null && update.message().text()!=null && !update.message().from().isBot()) {
            String text = update.message().text();
            Long id = update.message().chat().id();

            Pattern p = Pattern.compile("https://www\\.instagram\\.com/[a-zA-Z0-9._]*");
            Matcher m = p.matcher(text);
            if (m.matches()) {
                TelegramUser telegramUser = ofy().load().type(TelegramUser.class).id(id).now();
                if (telegramUser == null) {
                    telegramUser = new TelegramUser();
                    telegramUser.id = id;
                    out = "Добро пожаловать.\n";
                }

                try {

                    if(!telegramUser.urls.contains(text)){
                        long postsCount = InstagramSystem.getPostsCount(text, id);
                        telegramUser.urls.add(text);
                        out += "Пользователь добавлен в список.\n";
                        out += String.format("Число публикаций  %d.", postsCount);
                    }else{
                        out += "Пользователь уже есть в списке\n";
                    }
                } catch (IOException ignored) {
                    out += "Не получилось разобрать профиль пользователя";
                }

                ofy().save().entities(telegramUser).now();

            }else if(text.equals("/start")){
                out = "Что- то...";
            } else {
                out = "Не удалось разобрать сообщение...";
            }

            TelegramSystem.sendMessage(id, out);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TelegramSystem.startWebHooks();
    }
}
