package msz.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import msz.myapplication.Constant;
import msz.myapplication.R;
import msz.myapplication.adapter.base.BaseRecylerAdapter;
import msz.myapplication.entity.ItemsEntity;
import msz.myapplication.ui.activity.MainActivity;
import msz.myapplication.ui.activity.PhotoViewActivity;
import msz.myapplication.ui.view.MyCallback;

/**
 * @Title: LatestRecyclerAdapter
 * @Package msz.myapplication.adapter
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/27 13:56
 */
public class LatestRecyclerAdapter extends BaseRecylerAdapter {
    private final static int TYPE1 = 0, TYPE2 = 1;
    private static final String TAG = "LatestRecyclerAdapter";
    private List<ItemsEntity> list = new ArrayList<>();

    public LatestRecyclerAdapter(List list, Context context) {
        super(list, context);
        this.list=list;
    }

    @Override
    public int getItemViewType(int position) {
        String imageUrl = getImageUrl(list.get(position).getImage()+"");
        return imageUrl.equals("") ? TYPE1 : TYPE2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE1){
            View view1 = inflater.inflate(R.layout.item_latest_recyclerview_1, parent, false);
            return new ViewHolder1(view1);
        }else {
            View view2 = inflater.inflate(R.layout.item_latest_recyclerview_2, parent, false);
            return new ViewHolder2(view2);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder1){
            ((ViewHolder1) holder).textView_item_commentscount.setText(list.get(position).getComments_count()+"");
            ((ViewHolder1) holder).textView_item_content.setText(list.get(position).getContent());
            if (list.get(position).getUser() != null){
                ((ViewHolder1) holder).textView_item_login.setText(list.get(position).getUser().getLogin());
            }
            ((ViewHolder1) holder).textView_item_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mContext instanceof MainActivity){
                        ((MainActivity) mContext).startActionMode(new MyCallback(mContext,list.get(position).getContent()));
                    }
                    return false;
                }
            });
        }else if (holder instanceof ViewHolder2){
            final String imageUrl = getImageUrl(list.get(position).getImage()+"");
            Picasso.with(mContext).load(Uri.parse(imageUrl))
                    //无淡入淡出，快速加载
                    .noFade()
                    //下载图片的大小
                    //.resize(parent.getWidth(), 0)
                    //.resizeDimen(int targetWidthResId, int targetHeightResId)
                    //图片裁切
                    //.centerInside()
                    //占位图片，就是下载中的图片
                    .placeholder(R.mipmap.ic_empty)
                    //错误图片
                    .error(R.mipmap.ic_empty)
                    .into(((ViewHolder2) holder).imageView_item_show);
            ((ViewHolder2) holder).textView_item_commentscount.setText(list.get(position).getComments_count()+"");
            ((ViewHolder2) holder).textView_item_content.setText(list.get(position).getContent());
            if (list.get(position).getUser() != null){
                ((ViewHolder2) holder).textView_item_login.setText(list.get(position).getUser().getLogin());
            }
            ((ViewHolder2) holder).textView_item_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mContext instanceof MainActivity){
                        ((MainActivity) mContext).startActionMode(new MyCallback(mContext,list.get(position).getContent()));
                    }
                    return false;
                }
            });

            ((ViewHolder2) holder).imageView_item_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    PhotoViewActivity.launch((Activity) mContext,imageUrl,((ViewHolder2) holder).imageView_item_show);
                    int[] screenLocation = new int[2];
                    ((ViewHolder2) holder).imageView_item_show.getLocationOnScreen(screenLocation);
                    int left = screenLocation[0];
                    int top = screenLocation[1];
                    int width = ((ViewHolder2) holder).imageView_item_show.getWidth();
                    int height = ((ViewHolder2) holder).imageView_item_show.getHeight();
                    PhotoViewActivity.launch((Activity) mContext,imageUrl,left,top,width,height);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 根据图片的名称拼凑图片的网络访问地址
    private String getImageUrl(String imageName) {
        String urlFirst = "", urlSecond = "";
        if (imageName.indexOf('.') > 0) {
            if (imageName.indexOf("app") == 0) {
                urlSecond = imageName.substring(3, imageName.indexOf('.'));
                switch (urlSecond.length()) {
                    case 8:
                        urlFirst = imageName.substring(3, 7);
                        break;
                    case 9:
                        urlFirst = imageName.substring(3, 8);
                        break;
                    case 10:
                        urlFirst = imageName.substring(3, 9);
                        break;
                }
            } else {
                urlSecond = imageName.substring(0, imageName.indexOf('.'));
                urlFirst = imageName.substring(0, 6);
            }
            String s=String.format(Constant.URL_IMAGE,urlFirst,urlSecond,imageName);
            return s;
        } else {
            return "";
        }
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView textView_item_content;
        private TextView textView_item_login;
        private TextView textView_item_commentscount;

        public ViewHolder1(View view) {
            super(view);
            textView_item_content = ((TextView) view.findViewById(R.id.textView_item_content));
            textView_item_login = ((TextView) view.findViewById(R.id.textView_item_login));
            textView_item_commentscount = ((TextView) view.findViewById(R.id.textView_item_commentscount));
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView imageView_item_show;
        private TextView textView_item_content;
        private TextView textView_item_login;
        private TextView textView_item_commentscount;

        public ViewHolder2(View view) {
            super(view);
            imageView_item_show = ((ImageView) view.findViewById(R.id.imageView_item_show));
            textView_item_content = ((TextView) view.findViewById(R.id.textView_item_content));
            textView_item_login = ((TextView) view.findViewById(R.id.textView_item_login));
            textView_item_commentscount = ((TextView) view.findViewById(R.id.textView_item_commentscount));
        }
    }
}
