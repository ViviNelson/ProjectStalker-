package com.appspot.blacktenure188118.instagram;

import com.appspot.blacktenure188118.entities.InstagramUser;
import com.appspot.blacktenure188118.instagram.model.Instmodel;
import com.appspot.blacktenure188118.instagram.model.Post;
import com.appspot.blacktenure188118.instagram.model.PostsAndUsername;
import com.appspot.blacktenure188118.system.Network;
import com.google.appengine.repackaged.com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class InstagramSystem {

    private static Gson gson = new Gson();

    private static Instmodel getModel(String url) throws IOException {

        Instmodel model;

        String body = Network.sendGet(url);
        model = gson.fromJson(body,Instmodel.class);

        return model;

    }

    public static PostsAndUsername getNewPosts(String url,Long sender) throws IOException {
        Instmodel model = getModel(String.format("%s%s",url,"/?__a=1"));
        if(!model.user.is_private){
            String id = String.format("%s_%d",url,sender);
            InstagramUser instagramUser = ofy().load().type(InstagramUser.class).id(id).now();
            if(instagramUser!=null){
                long num = model.user.media.count - instagramUser.postsCount;

//                ArrayList<Post> posts = new ArrayList<>();
                PostsAndUsername postsAndUsername = new PostsAndUsername();

                if(num>12)
                    num=12;

                if(num>0){
                    List<Post> subList = model.user.media.nodes.subList(0, (int) num);
                    postsAndUsername.posts.addAll(subList);
                    postsAndUsername.username = model.user.username;
                }

                instagramUser.postsCount=model.user.media.count;
                ofy().save().entity(instagramUser);

                return postsAndUsername;
            }else{
                throw new IOException("No data in base");
            }
        }else{
            throw new IOException("Account is private");
        }

    }

    public static long getPostsCount(String url,Long sender) throws IOException {

        Instmodel model = getModel(String.format("%s%s",url,"/?__a=1"));
        if(!model.user.is_private){
            String id = String.format("%s_%d",url,sender);
            InstagramUser instagramUser = ofy().load().type(InstagramUser.class).id(id).now();
            if(instagramUser==null){
                instagramUser = new InstagramUser();
                instagramUser.id = id;
                instagramUser.username = model.user.username;
                instagramUser.url = url;
                instagramUser.postsCount = model.user.media.count;
            }else{
                instagramUser.postsCount = model.user.media.count;
            }

            ofy().save().entity(instagramUser);
            return instagramUser.postsCount;
        }else{
            throw new IOException("Account is private");
        }
    }

}
