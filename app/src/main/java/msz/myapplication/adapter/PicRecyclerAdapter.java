package msz.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import msz.myapplication.R;
import msz.myapplication.adapter.base.BaseRecylerAdapter;
import msz.myapplication.entity.ItemsEntity;
import msz.myapplication.ui.activity.PhotoViewActivity;
import msz.myapplication.util.PixelUtil;

/**
 * @Title: PicRecyclerAdapter
 * @Package msz.myapplication.adapter
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/28 17:35
 */
public class PicRecyclerAdapter extends BaseRecylerAdapter{
    private static final String TAG = "PicRecyclerAdapter";
    private List<ItemsEntity> list = new ArrayList<>();
    private HashMap<String, Integer> indexMap = new HashMap<String, Integer>();

    public PicRecyclerAdapter(List list, Context context) {
        super(list, context);
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pic_recyclerview, parent, false);
        return new PicRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PicRecyclerAdapter.ViewHolder) {
            int imageHeight = 0;
            if (indexMap.size()>=position+1&&indexMap.containsKey(list.get(position).getHigh_loc())){
                imageHeight = indexMap.get(list.get(position).getHigh_loc());
            }
            if(imageHeight != 0||list.get(position).getImage_size()==null){
                ViewGroup.LayoutParams layoutParams = ((PicRecyclerAdapter.ViewHolder) holder).imageView_item_show.getLayoutParams();
                layoutParams.height = imageHeight;
                ((PicRecyclerAdapter.ViewHolder) holder).imageView_item_show.setLayoutParams(layoutParams);
            }else{
                DisplayMetrics metric = new DisplayMetrics();
                ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
                int width = metric.widthPixels;     // 屏幕宽度（像素）
                int imageWidth=(width-PixelUtil.dp2px(32))/2;
                int height= imageWidth*list.get(position).getImage_size().getM().get(1)/list.get(position).getImage_size().getM().get(0);
                indexMap.put(list.get(position).getHigh_loc(),height);
                ViewGroup.LayoutParams layoutParams = ((PicRecyclerAdapter.ViewHolder) holder).imageView_item_show.getLayoutParams();
                layoutParams.height = height;
                ((PicRecyclerAdapter.ViewHolder) holder).imageView_item_show.setLayoutParams(layoutParams);
            }
            final String imageUrl = getImageUrl(list.get(position).getHigh_loc());
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
                    .into(((PicRecyclerAdapter.ViewHolder) holder).imageView_item_show);
            ((PicRecyclerAdapter.ViewHolder) holder).textView_item_commentscount.setText(list.get(position).getComments_count() + "");
            if (list.get(position).getUser() != null) {
                ((PicRecyclerAdapter.ViewHolder) holder).textView_item_login.setText(list.get(position).getUser().getLogin());
            }
            ((ViewHolder) holder).imageView_item_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    PhotoViewActivity.launch((Activity) mContext,imageUrl,((ViewHolder) holder).imageView_item_show);
                    int[] screenLocation = new int[2];
                    ((ViewHolder) holder).imageView_item_show.getLocationOnScreen(screenLocation);
                    int left = screenLocation[0];
                    int top = screenLocation[1];
                    int width = ((ViewHolder) holder).imageView_item_show.getWidth();
                    int height = ((ViewHolder) holder).imageView_item_show.getHeight();
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
        if (imageName == null) {
            return "";
        }
        if (imageName.indexOf('.') > 0) {
            String s="http:"+imageName;
            return s;
        } else {
            return "";
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_item_show;
        private TextView textView_item_login;
        private TextView textView_item_commentscount;

        public ViewHolder(View view) {
            super(view);
            imageView_item_show = ((ImageView) view.findViewById(R.id.imageView_item_show));
            textView_item_login = ((TextView) view.findViewById(R.id.textView_item_login));
            textView_item_commentscount = ((TextView) view.findViewById(R.id.textView_item_commentscount));
        }
    }
}
