package msz.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import msz.myapplication.R;
import msz.myapplication.adapter.base.BaseRecylerAdapter;
import msz.myapplication.entity.ItemsEntity;
import msz.myapplication.ui.activity.VideoActivity;
import msz.myapplication.util.PixelUtil;

/**
 * @Title: HeaderRecyclerAdapter
 * @Package msz.myapplication.adapter
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/29 13:56
 */
public class HeaderRecyclerAdapter extends BaseRecylerAdapter {
    private static final String TAG = "HeaderRecyclerAdapter";
    private List<ItemsEntity> list = new ArrayList<>();
    private HashMap<String, Integer> indexMap = new HashMap<String, Integer>();

    public HeaderRecyclerAdapter(List list, Context context) {
        super(list, context);
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_header_recyclerview, parent, false);
        return new HeaderRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderRecyclerAdapter.ViewHolder) {
            int imageWidth = 0;
            if (indexMap.containsKey(list.get(position).getPic_url())){
                imageWidth = indexMap.get(list.get(position).getPic_url());
            }
            if(imageWidth != 0){
                ViewGroup.LayoutParams layoutParams = ((HeaderRecyclerAdapter.ViewHolder) holder).imageView_item_show.getLayoutParams();
                layoutParams.width = imageWidth;
                ((HeaderRecyclerAdapter.ViewHolder) holder).imageView_item_show.setLayoutParams(layoutParams);
            }else{
                int width= PixelUtil.dp2px(134*list.get(position).getPic_size().get(0)/list.get(position).getPic_size().get(1));
                indexMap.put(list.get(position).getPic_url(),width);
                ViewGroup.LayoutParams layoutParams = ((HeaderRecyclerAdapter.ViewHolder) holder).imageView_item_show.getLayoutParams();
                layoutParams.width = width;
                ((HeaderRecyclerAdapter.ViewHolder) holder).imageView_item_show.setLayoutParams(layoutParams);
            }
            Picasso.with(mContext).load(Uri.parse(list.get(position).getPic_url()))
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
                    .into(((HeaderRecyclerAdapter.ViewHolder) holder).imageView_item_show);
            ((ViewHolder) holder).imageView_item_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoActivity.launch((Activity) mContext,list.get(position).getHigh_url());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_item_show;

        public ViewHolder(View view) {
            super(view);
            imageView_item_show = ((ImageView) view.findViewById(R.id.imageView_item_show));
        }
    }
}