package zalezone.retrofitlibrary.model;

/**
 * Created by zale on 2017/1/3.
 */

public class Authorization {

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
}
