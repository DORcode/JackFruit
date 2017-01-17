package com.jackfruit.mall.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.bean
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/17 10:39
 * @修改
 * @修改时期 2017/1/17 10:39
 */
@Entity
public class Demo {
    @Unique
    private String name;

    @Unique
    @Id
    private String id;

    private String city;

    @Generated(hash = 939843553)
    public Demo(String name, String id, String city) {
        this.name = name;
        this.id = id;
        this.city = city;
    }

    @Generated(hash = 571290164)
    public Demo() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
