package com.jackfruit.mall.greendao;

import org.greenrobot.greendao.test.AbstractDaoTestStringPk;

import com.jackfruit.mall.bean.Demo;
import com.jackfruit.mall.greendao.DemoDao;

public class DemoTest extends AbstractDaoTestStringPk<DemoDao, Demo> {

    public DemoTest() {
        super(DemoDao.class);
    }

    @Override
    protected Demo createEntity(String key) {
        Demo entity = new Demo();
        entity.setId(key);
        return entity;
    }

}
