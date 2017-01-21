package com.lib.imageselector.beans;

import java.io.Serializable;

/**
 * @项目名称 JackFruit
 * @类：com.lib.imageselector.beans
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/19 13:54
 * @修改
 * @修改时期 2017/1/19 13:54
 */
public class MediaInfo implements Serializable {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaInfo mediaInfo = (MediaInfo) o;

        return path.equals(mediaInfo.path);

    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
