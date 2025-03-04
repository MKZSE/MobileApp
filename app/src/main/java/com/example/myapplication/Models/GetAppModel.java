package com.example.myapplication.Models;

public class GetAppModel {
    private int id;
    private String app_Name;
    private String app_UrlAddress;
    private String iiS_AppName;
    private String iiS_AppPoolName;
    private String pgsqL_ConnectionString;


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getApp_Name() { return app_Name; }
    public void setApp_Name(String app_Name) { this.app_Name = app_Name; }

    public String getApp_UrlAddress() { return app_UrlAddress; }
    public void setApp_UrlAddress(String app_UrlAddress) { this.app_UrlAddress = app_UrlAddress; }

    public String getIiS_AppName() { return iiS_AppName; }
    public void setIiS_AppName(String iiS_AppName) { this.iiS_AppName = iiS_AppName; }

    public String getIiS_AppPoolName() { return iiS_AppPoolName; }
    public void setIiS_AppPoolName(String iiS_AppPoolName) { this.iiS_AppPoolName = iiS_AppPoolName; }

    public String getPgsqL_ConnectionString() { return pgsqL_ConnectionString; }
    public void setPgsqL_ConnectionString(String pgsqL_ConnectionString) { this.pgsqL_ConnectionString = pgsqL_ConnectionString; }
}

