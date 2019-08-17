package msz.myapplication.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import msz.myapplication.R;
import msz.myapplication.adapter.base.BaseRecylerAdapter;
import msz.myapplication.entity.ItemsEntity;
import msz.myapplication.ui.activity.MainActivity;

/**
 * @Title: TextRecyclerAdapter
 * @Package msz.myapplication.adapter
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/28 18:23
 */
public class TextRecyclerAdapter extends BaseRecylerAdapter {
    private static final String TAG = "TextRecyclerAdapter";
    private List<ItemsEntity> list = new ArrayList<>();
    public TextRecyclerAdapter(List list, Context context) {
        super(list, context);
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_latest_recyclerview_1, parent, false);
            return new TextRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TextRecyclerAdapter.ViewHolder){
            ((TextRecyclerAdapter.ViewHolder) holder).textView_item_commentscount.setText(list.get(position).getComments_count()+"");
            ((TextRecyclerAdapter.ViewHolder) holder).textView_item_content.setText(list.get(position).getContent());
            if (list.get(position).getUser() != null){
                ((TextRecyclerAdapter.ViewHolder) holder).textView_item_login.setText(list.get(position).getUser().getLogin());
            }
            ((ViewHolder) holder).textView_item_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mContext instanceof MainActivity){
                        PopupMenu popupMenu=new PopupMenu(mContext,((ViewHolder) holder).textView_item_content);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_text_actionmode,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId()==R.id.copy_text){
                                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                    cm.setText(list.get(position).getContent());
                                    Toast.makeText(mContext,"已经复制到剪切板",Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
//                        ((MainActivity) mContext).startActionMode(new MyCallback(mContext,list.get(position).getContent()));
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_item_content;
        private TextView textView_item_login;
        private TextView textView_item_commentscount;

        public ViewHolder(View view) {
            super(view);
            textView_item_content = ((TextView) view.findViewById(R.id.textView_item_content));
            textView_item_login = ((TextView) view.findViewById(R.id.textView_item_login));
            textView_item_commentscount = ((TextView) view.findViewById(R.id.textView_item_commentscount));
        }
    }
}
