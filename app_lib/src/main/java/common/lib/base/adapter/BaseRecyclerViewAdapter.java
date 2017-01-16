package common.lib.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名称 JackFruit
 * @类：common.lib.base.adapter
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/16 10:37
 * @修改
 * @修改时期 2017/1/16 10:37
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected List<T> data = new ArrayList<T>();
    protected OnItemClickListener<T> onItemClickListener;
    protected OnItemLongClickListener<T> onItemLongClickListener;

    protected void addList(List<T> list) {
        data.addAll(list);
    }

    protected void add(T t) {
        data.add(t);
    }

    protected void clear() {
        data.clear();
    }

    protected void remove(int position) {
        data.remove(position);
    }

    protected void remove(T t) {
        data.remove(t);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
