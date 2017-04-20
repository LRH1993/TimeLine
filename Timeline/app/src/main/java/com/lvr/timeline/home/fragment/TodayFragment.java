package com.lvr.timeline.home.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.lvr.timeline.R;
import com.lvr.timeline.adapter.TimelineAdapter;
import com.lvr.timeline.anims.LandingAnimator;
import com.lvr.timeline.anims.ScaleInAnimationAdapter;
import com.lvr.timeline.app.AppApplication;
import com.lvr.timeline.base.BaseFragment;
import com.lvr.timeline.bean.TimeInfo;
import com.lvr.timeline.home.ui.EditActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lvr on 2017/4/8.
 */

public class TodayFragment extends BaseFragment implements TimelineAdapter.OnItemClickListener{
    @BindView(R.id.rv_record)
    RecyclerView mRvRecord;
    private TimelineAdapter mAdapter;
    private List<TimeInfo> mInfos = new ArrayList<>();
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_today;
    }

    @Override
    protected void initView() {
        mAdapter = new TimelineAdapter(getActivity(),mInfos);
        mRvRecord.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter.setOnItemClickListener(this);
        mRvRecord.setItemAnimator(new LandingAnimator());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mAdapter);
        scaleAdapter.setFirstOnly(false);
        scaleAdapter.setDuration(500);
        mRvRecord.setAdapter(scaleAdapter);
        mRvRecord.getItemAnimator().setAddDuration(300);
        mRvRecord.getItemAnimator().setChangeDuration(300);
        mRvRecord.getItemAnimator().setMoveDuration(300);
        mRvRecord.getItemAnimator().setRemoveDuration(300);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        coverData();
    }

    /**
     * 恢复数据库中数据
     */
    private void coverData() {
        // TODO: 2017/4/17  异步查询
        RealmResults<TimeInfo> dogs = AppApplication.REALM_INSTANCE.where(TimeInfo.class).findAll();
        List<TimeInfo> infos = AppApplication.REALM_INSTANCE.copyFromRealm(dogs);
        System.out.println("从数据库中查找到的条目："+infos.size());
        if(infos==null){
            return;
        }
        mInfos.addAll(infos);
        System.out.println("复制后的数据条目:"+mInfos.size());
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onTimeInfoEvent(final TimeInfo info) {
        if(info.isNew()){
            mInfos.add(info);
            mAdapter.notifyItemRangeInserted(mInfos.size(),1);
            AppApplication.REALM_INSTANCE.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(info);
                    System.out.println("增加到数据库中了");
                }
            });
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
