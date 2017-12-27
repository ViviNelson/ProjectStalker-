package com.appspot.blacktenure188118.servlets;

import com.appspot.blacktenure188118.entities.TelegramUser;
import com.appspot.blacktenure188118.instagram.InstagramSystem;
import com.appspot.blacktenure188118.instagram.model.Post;
import com.appspot.blacktenure188118.instagram.model.PostsAndUsername;
import com.appspot.blacktenure188118.telegram.TelegramSystem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CronServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        List<TelegramUser> telegramUsers = ofy().load().type(TelegramUser.class).list();

        for(TelegramUser telegramUser:telegramUsers){
            for (String url: telegramUser.urls){
                try{
                    PostsAndUsername postsAndUsername = InstagramSystem.getNewPosts(url, telegramUser.id);
                    for(Post post: postsAndUsername.posts){
                        String s = String.format("@%s\n%s\n\n%s", postsAndUsername.username, post.display_src, post.caption);
                        TelegramSystem.sendMessage(telegramUser.id,s);
                    }
                }catch (Exception ignored){

                }
            }
        }

    }
}
