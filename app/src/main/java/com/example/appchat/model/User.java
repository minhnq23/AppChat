package com.example.appchat.model;

public class User {
    private String name;
    private String email;
    private String password;
    private String image;


    public static final String KEY_COLLECTION_USER = "users";
    public static final String KEY_PREFERENCE_NAME = "chatAppPref";

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER_ID= "userId";
    public static final String KEY_IS_SIGN_IN = "isSignIn";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
