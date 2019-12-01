package com.example.verificarpep;

import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class VerificActivity extends AppCompatActivity {
    String url;
    ImageView status;
    WebView webView;
    String migracion = "http://apps.migracioncolombia.gov.co/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            url = parametros.getString("url");
        }
        setContentView(R.layout.activity_verific);
        webView = findViewById(R.id.web1);
        status = findViewById(R.id.status);
        String verf =url.substring(0, 37);
        if(verf.equals(migracion)){
            status.setImageResource(R.drawable.verif);
        }else{
            status.setImageResource(R.drawable.falseimg);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(70);// webPlanSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR); webPlanSettings.setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
        public void onReceivedSslError(WebView view, SslErrorHandler
                handler, SslError error)
        {
            handler.proceed();
        }
        });
        url = url.replace(" ", "%20");
        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback)
            {
                callback.invoke(origin,true,false);
            }
        });

    }
}
