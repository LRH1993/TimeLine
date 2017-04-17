package com.lvr.timeline.home.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.lvr.timeline.R;
import com.lvr.timeline.adapter.TimelineAdapter;
import com.lvr.timeline.anims.AlphaInAnimationAdapter;
import com.lvr.timeline.anims.LandingAnimator;
import com.lvr.timeline.base.BaseFragment;
import com.lvr.timeline.bean.TimeInfo;
import com.lvr.timeline.home.ui.EditActivity;
import com.lvr.timeline.widget.ItemDragHelperCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by lvr on 2017/4/8.
 */

public class TodayFragment extends BaseFragment implements TimelineAdapter.OnItemClickListener{
    @BindView(R.id.rv_record)
    RecyclerView mRvRecord;
    private TimelineAdapter mAdapter;
    private List<TimeInfo> mInfos = new ArrayList<>();
    private ItemTouchHelper itemTouchHelper;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_today;
    }

    @Override
    protected void initView() {
        mAdapter = new TimelineAdapter(getActivity(),mInfos);
        mRvRecord.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(mAdapter,mAdapter);
        itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRvRecord);
        mAdapter.setOnItemClickListener(this);
        mRvRecord.setItemAnimator(new LandingAnimator());
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        mRvRecord.setAdapter(alphaAdapter);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe
    public void onTimeInfoEvent(TimeInfo info) {
        if(info.isNew()){
            mInfos.add(info);
            mAdapter.notifyItemRangeInserted(mInfos.size(),1);
        }else{
           if(info.getPosition()!=-1){
               mInfos.remove(info.getPosition());
               mInfos.add(info.getPosition(),info);
               mAdapter.notifyItemChanged(info.getPosition());
           }
        }

    }


    @Override
    public void onItemClick(TimeInfo mInfo,int position) {
        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra("TimeInfo",mInfo);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
