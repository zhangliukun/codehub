package zalezone.retrofitlibrary.model;

import java.util.Arrays;

/**
 * Created by zale on 2017/1/3.
 */

public class Authorization extends GithubBaseModel{

    private int id;
    private String url;

    private AppBean app;
    private String token;
    private String hashed_token;
    private String token_last_eight;
    private String note;
    private String note_url;
    private String created_at;
    private String updated_at;
    private String fingerprint;
    private String[] scopes;

    public static class AppBean{
        private String name;
        private String url;
        private String client_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_last_eight() {
        return token_last_eight;
    }

    public void setToken_last_eight(String token_last_eight) {
        this.token_last_eight = token_last_eight;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", app=" + app +
                ", token='" + token + '\'' +
                ", hashed_token='" + hashed_token + '\'' +
                ", token_last_eight='" + token_last_eight + '\'' +
                ", note='" + note + '\'' +
                ", note_url='" + note_url + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                ", scopes=" + Arrays.toString(scopes) +
                '}';
    }
}
