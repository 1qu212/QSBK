package msz.myapplication.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * @Title: BaseRecylerAdapter
 * @Package msz.myapplication.adapter.base
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/27 15:29
 */
public class BaseRecylerAdapter<T> extends RecyclerView.Adapter {
    public List<T> list = null;
    public Context mContext;
    public LayoutInflater inflater = null;

    public BaseRecylerAdapter(List<T> list, Context context) {
        this.list = list;
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void reloadData(List<T> data , boolean isClear) {
        if (list != null) {
            if (isClear) {
                list.clear();
            }
            list.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearAll() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
