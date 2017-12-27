package com.appspot.blacktenure188118.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class TelegramUser {

    @Id
    public Long id;

    public ArrayList<String> urls = new ArrayList<>();

    public Date created = new Date();

}
