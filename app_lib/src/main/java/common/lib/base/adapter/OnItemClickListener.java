package common.lib.base.adapter;

/**
 * @项目名称 JackFruit
 * @类：common.lib.base.adapter
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/16 10:42
 * @修改
 * @修改时期 2017/1/16 10:42
 */
public interface OnItemClickListener<T> {
    void onItemClick(T t, int position);
}
