package com.lib.imageselector.beans;

/**
 * @项目名称 JackFruit
 * @类：com.lib.imageselector.beans
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/19 13:54
 * @修改
 * @修改时期 2017/1/19 13:54
 */
public class MediaInfo {
    private String path;
    private String name;
    private MediaType type = MediaType.IMAGE;
    private String dateModified;
    private boolean isChecked = false;

    public MediaInfo(String path, String name, MediaType type, String dateModified) {
        this.path = path;
        this.name = name;
        this.type = type;
        this.dateModified = dateModified;
    }

    public enum MediaType {
        IMAGE, VIDEO
    }
}
