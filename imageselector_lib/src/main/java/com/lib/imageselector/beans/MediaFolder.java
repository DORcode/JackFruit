package com.lib.imageselector.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名称 JackFruit
 * @类：com.lib.imageselector.beans
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/19 13:54
 * @修改
 * @修改时期 2017/1/19 13:54
 */
public class MediaFolder {
    private String path;
    private String name;
    private List<MediaInfo> list;

    public MediaFolder(String path, String name) {
        this.path = path;
        this.name = name;
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

    public List<MediaInfo> getList() {
        if(list == null) {
            list = new ArrayList<MediaInfo>();
        }
        return list;
    }

    public void setList(List<MediaInfo> list) {
        this.list = list;
    }

    public void addMedia(MediaInfo mi) {
        if(list == null) {
            list = new ArrayList<MediaInfo>();
        }
        if(!list.contains(mi)) {
            list.add(mi);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaFolder that = (MediaFolder) o;

        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return  path.hashCode();
    }
}
