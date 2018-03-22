package com.anb.mywishlist.Models;

/**
 * Created by Agung Nursatria on 3/21/2018.
 */

public class Todo {

    public static final String TABLE = "todo";

    public static final String _ID = "id";
    public static final String _TITLE = "title";
    public static final String _DESCRIPTION = "desctiption";
    public static final String _COMPLETED = "completed";
    public static final String _TIME = "time";

    public int id;
    public String title;
    public String description;
    public Long time;
    public boolean completed;

    public Todo() {
    }
}
