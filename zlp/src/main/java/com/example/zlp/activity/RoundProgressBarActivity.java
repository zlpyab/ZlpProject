package com.example.zlp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.zlp.R;
import com.example.zlp.wight.RoundProgressBar;

public class RoundProgressBarActivity extends AppCompatActivity {

    private RoundProgressBar roundProgressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_progress_bar);

//        roundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgress);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                roundProgressBar.setProgress(50);
//
//            }
//        }).start();

        webView = (WebView) findViewById(R.id.webview);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setSupportZoom(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        //WebView加载web资源
       // webView.loadUrl("http://www.fenqibei.cn");
        webView.loadUrl("https://mclient.alipay.com/home/exterfaceAssign.htm?alipay_exterface_invoke_assign_client_ip=59.41.65.154&subject=%E5%A4%A7%E7%BE%8E%E8%BF%98%E6%AC%BE&sign_type=RSA&notify_url=http%3A%2F%2Fapp.dameistore.com%2Fweb%2FpayBillAlipayCallBack&out_trade_no=1488182033012-77db2f91ecb60bf71fe5a12a0db20514&return_url=gap%3A%2F%2FrepaymentCompleted&sign=URYOB1Gln4qRUlFxrNBm%2FnyQvmqW9BxfTthAAWeyBZfJjA2gJWSqzR1Wj6Z272mOnOlQEmMPQsvVF5XIdHpbvKnVmi6Z07fCXyZrOt%2B62AdupYIA26Q1upJzDVWKJOEW0As2Tr%2F0On95Ob5xYoeonLTLYUVahcYvsibKcUccO0A%3D&_input_charset=utf-8&it_b_pay=30m&alipay_exterface_invoke_assign_target=mapi_direct_trade.htm&alipay_exterface_invoke_assign_model=cashier&total_fee=613.00&service=alipay.wap.create.direct.pay.by.user&partner=2088121807915626&seller_id=2088121807915626&alipay_exterface_invoke_assign_sign=_je%2F_e_x_e_i_s_laar_c_u98_d_z_fio6_m_y_r%2B_vzcv9_a_tx_u_jz0_j_i%2B_tm_x3_bho4_xu3tg%3D%3D&payment_type=1");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制WebView去打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });



    }


}
