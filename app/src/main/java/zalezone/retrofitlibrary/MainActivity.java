package zalezone.retrofitlibrary;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Headers;
import zalezone.retrofitlibrary.githubapi.GithubApi;
import zalezone.retrofitlibrary.network.IDataCallback;

public class MainActivity extends AppCompatActivity {

    private Handler mHander = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.testTV);
        final String text = null;
        Toast.makeText(this,text+"",Toast.LENGTH_LONG).show();


        findViewById(R.id.btnT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                GithubApi.authentication();
                GithubApi.users("zhangliukun", new IDataCallback<String>() {
                    @Override
                    public void onSuccess(String object, Headers headers) {
                        Toast.makeText(MainActivity.this,object,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(int code, String message) {
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
