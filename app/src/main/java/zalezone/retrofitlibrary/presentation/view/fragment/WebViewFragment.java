package zalezone.retrofitlibrary.presentation.view.fragment;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import zalezone.retrofitlibrary.R;
import zalezone.retrofitlibrary.common.base.BaseFragment;

/**
 * Created by zale on 2017/2/23.
 */

public class WebViewFragment extends BaseFragment{

    WebView mWebView;
    ProgressBar progressBar;
    LinearLayout webContainer;

    public static WebViewFragment newInstance(String url){
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mWebView = new WebView(mActivity);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(lp);
        addView(mWebView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        initWebViewSettings();
        initWebViewClient();
        initWebViewChromeClient();

        Bundle bundle = getArguments();
        if (bundle!=null ){
            String url = bundle.getString("url");
            if (!TextUtils.isEmpty(url)){
                mWebView.loadUrl(url);
            }
        }
    }

    private void initWebViewSettings() {
        WebSettings settings = mWebView.getSettings();
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //支持适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(false);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());
        //设置可访问文件
        settings.setAllowContentAccess(true);
        //当webView调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >=19){
            settings.setLoadsImagesAutomatically(true);
        }else {
            settings.setLoadsImagesAutomatically(false);
        }
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
    }

    private void initWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }
        });
    }

    private void initWebViewChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                return super.getDefaultVideoPoster();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public int getContainerLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mWebView!=null){

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
