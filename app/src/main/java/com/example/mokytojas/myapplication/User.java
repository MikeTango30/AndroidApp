package com.example.mokytojas.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    private String username;
    private String password;
    private String email;

    private SharedPreferences sharedPreferences;
    private static final String PREFERENCES_FILE_NAME ="com.example.mokytojas.myapplication";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String REMEMBER_ME_KEY = "rememberMe";

    //Naujo vartotojo registracija
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    //Vartotojo prisijungimuj
    public User(Context context) {
        this.sharedPreferences = context.getSharedPreferences(User.PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE);

    }

    public String getUsernameForRegistration() {
        return username;
    }

    public void setUsernameForRegistration(String username) {
        this.username = username;
    }

    public String getPasswordForRegistration() {
        return password;
    }

    public void setPasswordForRegistration(String password) {
        this.password = password;
    }

    public String getEmailForRegistration() {
        return email;
    }

    public void setEmailForRegistration(String email) {
        this.email = email;
    }

    public String getUsernameForLogin() {
        return this.sharedPreferences.getString(USERNAME_KEY,"");
    }

    public void setUsernameForLogin(String username) {
        this.sharedPreferences.edit().putString(USERNAME_KEY,username).commit();
    }

    public String getPasswordForLogin() {
        return this.sharedPreferences.getString(PASSWORD_KEY,"");
    }

    public void setPasswordForLogin(String password) {
        this.sharedPreferences.edit().putString(PASSWORD_KEY,password).commit();
    }

    public boolean isRemembered(){
        return this.sharedPreferences.getBoolean(REMEMBER_ME_KEY,false);
    }

    public void setRememberMeKey(boolean rememberMeKey){
        this.sharedPreferences.edit().putBoolean(REMEMBER_ME_KEY,rememberMeKey).commit();
    }

}