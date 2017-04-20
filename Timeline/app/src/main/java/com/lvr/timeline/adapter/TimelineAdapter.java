package com.lvr.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lvr.timeline.R;
import com.lvr.timeline.bean.TimeInfo;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TimelineAdapter extends  RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private List<TimeInfo> list;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private int[] colors;
    public TimelineAdapter(Context context, List<TimeInfo> list) {
        this.list=list;
        this.context=context;
        this.colors = new int[]{context.getResources().getColor(R.color.color1), context.getResources().getColor(R.color.color2), context.getResources().getColor(R.color.color3)
                , context.getResources().getColor(R.color.color4), context.getResources().getColor(R.color.color5), context.getResources().getColor(R.color.color6), context.getResources().getColor(R.color.color7)
                , context.getResources().getColor(R.color.color8), context.getResources().getColor(R.color.color9), context.getResources().getColor(R.color.color10), context.getResources().getColor(R.color.color11)
                , context.getResources().getColor(R.color.color12), context.getResources().getColor(R.color.color13), context.getResources().getColor(R.color.color14), context.getResources().getColor(R.color.color15)
                , context.getResources().getColor(R.color.color16), context.getResources().getColor(R.color.color17), context.getResources().getColor(R.color.color18), context.getResources().getColor(R.color.color19)
                , context.getResources().getColor(R.color.color20), context.getResources().getColor(R.color.color21), context.getResources().getColor(R.color.color22), context.getResources().getColor(R.color.color23)
                , context.getResources().getColor(R.color.color24)};

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_line, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TimeInfo info = list.get(position);
        holder.yMDText.setText(info.getYmD());
        holder.titleText.setText(info.getTitle());
        holder.contentText.setText(info.getContent());
        // TODO: 2017/4/17 有问题 创建该条目的时间会出错 
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String allTime = format.format(new Date());
        holder.timeText.setText(allTime);
        String[] split = info.getAddTime().split("-");
        holder.hourText.setText(split[0]);
        String[] hours = split[0].split(":");
        System.out.println(hours);
        int hour = Integer.parseInt(hours[0]);
        System.out.println(hour);
        int color = colors[hour-1];
        holder.hourText.setBackgroundColor(color);
        holder.hourText.setTextColor(context.getResources().getColor(R.color.white));
        holder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(list.get(position),position);
            }
        });
        //这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑
        ((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(true);
        holder.deleteIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curPosition = holder.getPosition();
                System.out.println("当前位置："+curPosition);
                list.remove(curPosition);
                notifyItemRemoved(curPosition);
                System.out.println("list的数量："+list.size());
            }
        });
        holder.completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"完成了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView hourText;
        private RelativeLayout rl_root;
        private TextView yMDText;
        private TextView titleText;
        private TextView timeText;
        private TextView contentText;
        private ImageButton deleteIButton;
        private Button completeButton;
        public ViewHolder(View v) {
            super(v);
            titleText = (TextView) v.findViewById(R.id.tv_title);
            yMDText = (TextView) v.findViewById(R.id.tv_yMD);
            timeText = (TextView) v.findViewById(R.id.tv_time);
            contentText = (TextView) v.findViewById(R.id.tv_content);
            rl_root = (RelativeLayout) v.findViewById(R.id.rl_root);
            hourText = (TextView) v.findViewById(R.id.tv_hour);
            deleteIButton = (ImageButton) v.findViewById(R.id.ib_delete);
            completeButton = (Button) v.findViewById(R.id.btn_complete);
        }
    }
    public interface  OnItemClickListener{
        void onItemClick(TimeInfo mInfo,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }


}
