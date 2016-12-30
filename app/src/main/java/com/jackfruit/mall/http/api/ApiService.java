package com.jackfruit.mall.http.api;

import com.jackfruit.mall.bean.DemoBean;
import com.jackfruit.mall.bean.DemoResult;
import com.jackfruit.mall.bean.HttpResult;
import com.jackfruit.mall.bean.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.http
 * @描述 网络请求接口
 * @创建者 kh
 * @日期 2016/12/27 14:02
 * @修改
 * @修改时期
 */
public interface ApiService {

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    @GET("abc/abc")
    Observable<HttpResult<User>> getLoginResult(@Query("account") String account, @Field("password") String password);

    @GET("api/version/android_new")
    Observable<DemoResult<DemoBean>> getDemoResult();

    //通用get请求
    @GET
    Observable<ResponseBody> httpGet(@Url String url, @FieldMap Map map);

    //通用post请求
    @POST
    Observable<ResponseBody> httpPost(@Url String url, @QueryMap Map map);

    //多文件上传
    @Multipart
    @POST
    Observable<ResponseBody> httpPostMulti(@Url String url, @QueryMap Map map);
}
