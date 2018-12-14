package me.mikasa.science.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.base.BaseToolbarActivity;
import me.mikasa.science.listener.DownloadListener;
import me.mikasa.science.utils.GlideUtil;
import static me.mikasa.science.utils.GlideUtil.FILEPATH;

public class WebViewActivity extends BaseToolbarActivity implements DownloadListener{
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.loadingProgress)
    ProgressBar loadingProgress;

    private String receivedUrl;
    private Context mContext;
    private WebSettings webSettings;
    private GlideUtil glideUtil;
    private Snackbar snackbar;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initData() {
        mContext=this;
        Intent intent=getIntent();
        receivedUrl=intent.getStringExtra("url");
    }

    @Override
    protected void initView() {
        webSettings=webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(chromeClient);
        webView.loadUrl(receivedUrl);
    }

    @Override
    protected void initListener() {
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final WebView.HitTestResult hitTestResult=webView.getHitTestResult();
                if (hitTestResult.getType()==WebView.HitTestResult.IMAGE_TYPE||hitTestResult.getType()==WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE){
                    AlertDialog dialog=new AlertDialog.Builder(mContext).create();
                    dialog.setMessage("下载图片");
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();//关闭对话框
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();//关闭对话框
                            downloadBitmap(hitTestResult.getExtra());
                        }
                    });
                    dialog.show();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview,menu);
        return true;
    }
    private void openInBrowser(){
        Uri uri=Uri.parse(receivedUrl);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.browser:
                openInBrowser();
                return true;
            case R.id.download:
                snackbar=Snackbar.make(webView,"长按图片可下载图片",Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundResource(R.color.snackbar_success);
                snackbar.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    WebViewClient webViewClient=new WebViewClient(){

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };
    WebChromeClient chromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            loadingProgress.setProgress(newProgress);
            if (newProgress==100){
                loadingProgress.setVisibility(View.GONE);
            }
        }
    };
    private void downloadBitmap(String url){
        glideUtil=new GlideUtil(mContext,this);
        glideUtil.downloadBitmap(url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    @Override
    public void downloadSuccess() {
        snackbar=Snackbar.make(webView,"图片已保存到"+FILEPATH,Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundResource(R.color.snackbar_success);
        snackbar.show();
    }

    @Override
    public void downloadFailed() {
        snackbar=Snackbar.make(webView,"请检查SD卡是否可用",Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundResource(R.color.snackbar_error);
        snackbar.show();
    }
}
