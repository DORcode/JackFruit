package com.jackfruit.mall.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jackfruit.mall.BuildConfig;
import com.jackfruit.mall.bean.DemoBean;
import com.jackfruit.mall.bean.DemoResult;
import com.jackfruit.mall.http.api.ApiService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
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
    private static RetrofitManager instance;
    private Retrofit retrofit;
    private ApiService apiService;

    public static RetrofitManager getInstance() {
        if(instance == null) {
            synchronized (RetrofitManager.class) {
                if(instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    public ApiService getApiService() {
        return apiService == null ? createService(ApiService.class) : apiService;
    }

    public <T> T createService(Class<T> service) {
        return getRetrofit().create(service);
    }

    public <T> T createService(Class<T> service, String url) {
        return getRetrofit(url).create(service);
    }

    public RetrofitManager() {
        getRetrofit();
    }

    public Retrofit getRetrofit() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }



    public OkHttpClient getClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        //添加头部header配置的拦截器
        Interceptor headInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = new Request.Builder();
                //Platform,Version

                //builder.addHeader();
                builder.removeHeader("Accept");
                //加入Token
                Request request = builder.build();

                return chain.proceed(request);
            }
        };

        //Log信息拦截器
        if(BuildConfig.DEBUG) {
            Interceptor logInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    ResponseBody responseBody = response.body();
                    BufferedSource bufferedSource = responseBody.source();
                    bufferedSource.request(Long.MAX_VALUE);
                    Buffer buffer = bufferedSource.buffer();
                    Charset UTF8 = Charset.forName("UTF-8");
                    Log.d("REQUEST_JSON", "intercept: " + buffer.clone().readString(UTF8));
                    Log.d("REQUEST_URL", request.toString());
                    return response;
                }
            };
            okHttpClient.addInterceptor(logInterceptor);
        }
        okHttpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        //okHttpClient.addInterceptor(headInterceptor);
        return okHttpClient.build();
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
        return retrofit.create(ApiService.class);
    }
}
