package com.lvr.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvr.timeline.R;
import com.lvr.timeline.bean.TimeInfo;
import com.lvr.timeline.widget.ItemDragHelperCallback;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class TimelineAdapter extends  RecyclerView.Adapter<TimelineAdapter.ViewHolder> implements ItemDragHelperCallback.OnItemSwipeListener,ItemDragHelperCallback.OnItemMoveListener {
    private List<TimeInfo> list;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private int[] colors = {};
    public TimelineAdapter(Context context, List<TimeInfo> list) {
        this.list=list;
        this.context=context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_line, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TimeInfo info = list.get(position);
        holder.yMDText.setText(info.getYmD());
        holder.titleText.setText(info.getTitle());
        holder.contentText.setText(info.getContent());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String allTime = format.format(new Date());
        holder.timeText.setText(allTime);
        String[] split = info.getAddTime().split("-");
        holder.hourText.setText(split[0]);
        holder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 删除某个Item
     * @param position
     */
    @Override
    public void onItemSwipe(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 移动
     * @param fromPosition 开始的位置
     * @param toPosition   结束的位置
     */
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView hourText;
        private RelativeLayout rl_root;
        private TextView yMDText;
        private TextView titleText;
        private TextView timeText;
        private TextView contentText;
        public ViewHolder(View v) {
            super(v);
            titleText = (TextView) v.findViewById(R.id.tv_title);
            yMDText = (TextView) v.findViewById(R.id.tv_yMD);
            timeText = (TextView) v.findViewById(R.id.tv_time);
            contentText = (TextView) v.findViewById(R.id.tv_content);
            rl_root = (RelativeLayout) v.findViewById(R.id.rl_root);
            hourText = (TextView) v.findViewById(R.id.tv_hour);

        }
    }
    public interface  OnItemClickListener{
        void onItemClick(TimeInfo mInfo);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }


}
