package com.appspot.blacktenure188118.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class InstagramUser {

    @Id
    public String id;

    public String username;

    public String url;

    public long postsCount;

    public Date created = new Date();

}
