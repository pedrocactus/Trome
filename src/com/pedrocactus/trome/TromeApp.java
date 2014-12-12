package com.pedrocactus.trome;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TromeApp extends Application{
    private static TromeApp instance;
    
           
    public TromeApp(){
        instance = this;
    }
    @Override
    public void onCreate()
    {
      super.onCreate();
    }
    
    public static TromeApp getInstance(){
        return instance;
    }
    
    public String getPreferences(String tag){
        SharedPreferences preferences=getSharedPreferences(getPackageName(), getApplicationContext().MODE_PRIVATE);
        String name=preferences.getString(tag,null);
        return name;
        
    }
    
 public void setPreferences(String key, String value){
     SharedPreferences.Editor prefsEditor =  getSharedPreferences(getPackageName(), Context.MODE_PRIVATE).edit();
     prefsEditor.putString(key, value);
     prefsEditor.commit();
    }
    
    

}
