package com.appspot.blacktenure188118.instagram;

import com.appspot.blacktenure188118.entities.InstagramUser;
import com.appspot.blacktenure188118.instagram.model.Instmodel;
import com.appspot.blacktenure188118.system.Network;
import com.google.appengine.repackaged.com.google.gson.Gson;

import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class InstagramSystem {

    private static Gson gson = new Gson();

    private static Instmodel getModel(String url) throws IOException {

        Instmodel model;

        String body = Network.sendGet(url);
        model = gson.fromJson(body,Instmodel.class);

        return model;

    }

    public static long getPostsCount(String url,Long sender) throws IOException {

        Instmodel model = getModel(String.format("%s%s",url,"/?__a=1"));
        System.out.println("model.user.is_privated");
        System.out.println(model.user.is_private);
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
