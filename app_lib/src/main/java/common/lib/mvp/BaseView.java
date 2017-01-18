package common.lib.mvp;

/**
 * @项目名称 JackFruit
 * @类：common.lib.mvp
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/18 14:31
 * @修改
 * @修改时期 2017/1/18 14:31
 */
public interface BaseView {
    void showLoading(String msg);
    void hideLoading();
    void showError(String msg);
    void showExcepiton(String msg);
    void showNetError();
}
