package common.lib.netstatus;

import common.lib.utils.NetUtils;

/**
 * @项目名称 JackFruit
 * @类：common.lib.netstatus
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/5 10:30
 * @修改
 * @修改时期 2017/1/5 10:30
 */
public interface NetChangeObserver {
    void onNetConnect(NetUtils.NetType type);
    void onNetDisConnect();
}
