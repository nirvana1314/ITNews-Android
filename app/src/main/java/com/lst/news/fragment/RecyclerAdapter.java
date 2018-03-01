package com.lst.news.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.lst.news.R;

import java.util.List;

/**
 * 项目名称：RecyclerViewTest
 * 创建人：Double2号
 * 创建时间：2016/4/18 8:12
 * 修改备注：
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<ListModel> mData;

    public RecyclerAdapter(List<ListModel> data) {
        mData = data;
    }

    //定义一个监听对象，用来存储监听事件
    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    //定义OnItemClickListener的接口,便于在实例化的时候实现它的点击效果
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvViewHolder;
        public TextView tvViewDesc;
        public ImageView ivViewHolder;
        public RelativeLayout rlViewHolder;

        //初始化viewHolder，此处绑定后在onBindViewHolder中可以直接使用
        public ViewHolder(View itemView){
            super(itemView);
            tvViewHolder = (TextView)itemView.findViewById(R.id.tv_view_holder);
            tvViewDesc = (TextView)itemView.findViewById(R.id.tv_desc);
            ivViewHolder = (ImageView)itemView.findViewById(R.id.iv_view_holder);
            rlViewHolder = (RelativeLayout) itemView;
            rlViewHolder.setOnClickListener(this);
        }

        //通过接口回调来实现RecyclerView的点击事件
        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null) {
                //此处调用的是onItemClick方法，而这个方法是会在RecyclerAdapter被实例化的时候实现
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View views= LayoutInflater.from(parent.getContext()).inflate(
                R.layout.rc_item,parent,false);
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListModel model = mData.get(position);
        //建立起ViewHolder中试图与数据的关联
        holder.tvViewHolder.setText(model.getTitle());
        holder.tvViewDesc.setText(model.getCate_name());
        Log.v("lst", model.getThumb());
        if (TextUtils.isEmpty(model.getThumb())) {
            Log.w("lst", "url为空");
            Glide.with(holder.ivViewHolder.getContext()).load(R.mipmap.ic_launcher).into(holder.ivViewHolder);
        }else {
            GlideUrl glideUrl = new GlideUrl(model.getThumb(), new LazyHeaders.Builder()
                    .addHeader("Referer", "http://api.m123.me/")
                    .build());
            Glide.with(holder.ivViewHolder.getContext()).load(glideUrl).into(holder.ivViewHolder);
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
