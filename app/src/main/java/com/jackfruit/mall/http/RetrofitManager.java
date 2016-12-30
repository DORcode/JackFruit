package com.jackfruit.mall.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jackfruit.mall.bean.DemoBean;
import com.jackfruit.mall.bean.DemoResult;
import com.jackfruit.mall.http.api.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.http
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/27 14:11
 * @修改
 * @修改时期 2016/12/27 14:11
 */
public class RetrofitManager {

    //请求超时
    private static final int CONNECT_TIMEOUT = 10;
    //写超时
    private static final int WRITE_TIMEOUT = 10;
    //读超时
    private static final int READ_TIMEOUT = 300;
    public static final String baseUrl = "http://bobo.yimwing.com/";
    private static RetrofitManager retrofitManager;
    private Retrofit retrofit;

    public static RetrofitManager getRetrofitManager() {
        if(retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if(retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    public RetrofitManager() {
        getRetrofit();
    }

    public Retrofit getRetrofit() {
        Log.i("", "getRetrofit: ");
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //.client(getClient().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(getClient().build())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    public OkHttpClient.Builder getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())//添加打印拦截器
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                ;
    }

    private Gson getGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd hh:mm:ss:SSS")
                .create();

    }

    /**
     * 登录
     * @return
     */
    public ApiService getLoginService() {
        Log.i("", "getLoginService: ");
        return retrofit.create(ApiService.class);
    }
}
