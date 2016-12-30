package com.jackfruit.mall.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.bean
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/27 15:51
 * @修改
 * @修改时期 2016/12/27 15:51
 */

public class DemoBean {
    /**
     * versionName : 1.3.2
     * versionCode : 6
     * isQiangzhi : 0
     * url : http://bobo-sql.oss-cn-beijing.aliyuncs.com/app-bobo-release.apk
     * updateContent : 修复崩溃BUG
     */

    @SerializedName("versionName")
    private String versionName;
    @SerializedName("versionCode")
    private int versionCode;
    @SerializedName("isQiangzhi")
    private int isQiangzhi;
    @SerializedName("url")
    private String url;
    @SerializedName("updateContent")
    private String updateContent;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getIsQiangzhi() {
        return isQiangzhi;
    }

    public void setIsQiangzhi(int isQiangzhi) {
        this.isQiangzhi = isQiangzhi;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
}
