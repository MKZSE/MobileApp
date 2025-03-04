package com.example.myapplication.Models;

public class GetLogsModel {
    private int id;
    private String date;
    private int appid;
    private String message;


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getAppid() { return appid; }
    public void setAppid(int appid) { this.appid = appid; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

