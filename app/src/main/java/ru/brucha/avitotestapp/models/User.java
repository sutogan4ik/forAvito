package ru.brucha.avitotestapp.models;

/**
 * Created by Prog on 06.07.2015.
 */
public class User {
    private String login;
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatar_url = avatarUrl;
    }
}
