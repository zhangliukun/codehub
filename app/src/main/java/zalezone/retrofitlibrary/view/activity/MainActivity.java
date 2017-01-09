package zalezone.retrofitlibrary.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Headers;
import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.githubapi.GithubApi;
import zalezone.retrofitlibrary.manager.AccountManager;
import zalezone.retrofitlibrary.model.Authorization;
import zalezone.retrofitlibrary.network.IDataCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.testTV);
//        final String text = null;
//        Toast.makeText(this,text+"",Toast.LENGTH_LONG).show();


        findViewById(R.id.btnT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                GithubApi.authentication();
//                GithubApi.users("zhangliukun", new IDataCallback<String>() {
//                    @Override
//                    public void onSuccess(String object, Headers headers) {
//                        Toast.makeText(MainActivity.this,object,Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(int code, String message) {
//                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
//                    }
//                });
                GithubApi.login("test", "test", new IDataCallback<Authorization>() {
                    @Override
                    public void onSuccess(Authorization object, Headers headers) {
                        if (object!=null){
                            textView.setText(object.toString()+"");
                            AccountManager.saveLoginToken(MainActivity.this,"token "+object.getToken());

                        }
                    }

                    @Override
                    public void onError(int code, String message) {
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GithubApi.user(new IDataCallback<String>() {
                    @Override
                    public void onSuccess(String object, Headers headers) {
                        if (!TextUtils.isEmpty(object)){
                            textView.setText(object+"");

                        }
                    }

                    @Override
                    public void onError(int code, String message) {

                    }
                });
            }
        });

    }
}
