package com.lvr.timeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class TimelineAdapter extends  RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private List<TimeInfo> list;
    private Context context;
    public TimelineAdapter(Context context, List<TimeInfo> list) {
        this.list=list;
        this.context=context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimeInfo info = list.get(position);
        holder.yMDText.setText(info.getYmD());
        holder.hourText.setText(info.getHour()+"");
        holder.titleText.setText(info.getTitle());
        holder.contentText.setText(info.getContent());
        holder.timeText.setText(info.getAddTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleTextView hourText;
        private TextView yMDText;
        private TextView titleText;
        private TextView timeText;
        private TextView contentText;
        public ViewHolder(View v) {
            super(v);
            hourText = (CircleTextView) v.findViewById(R.id.tv_hour);
            titleText = (TextView) v.findViewById(R.id.tv_title);
            yMDText = (TextView) v.findViewById(R.id.tv_yMD);
            timeText = (TextView) v.findViewById(R.id.tv_time);
            contentText = (TextView) v.findViewById(R.id.tv_content);
        }
    }

}
