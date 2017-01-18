package common.lib.mvp;

/**
 * @项目名称 JackFruit
 * @类：common.lib.mvp
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/18 14:34
 * @修改
 * @修改时期 2017/1/18 14:34
 */
public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {

    private V mView;
    private M mModel;

    public void attacth(V v, M m) {
        mView = v;
        mModel = m;
    }

    public void detach() {
        mView = null;
        mModel = null;
    }
}
