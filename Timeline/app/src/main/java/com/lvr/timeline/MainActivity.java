package com.lvr.timeline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv_record;
    private List<TimeInfo>  mInfos;
    private TimelineAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv_record = (RecyclerView) findViewById(R.id.rv_record);
        initData();
        mAdapter = new TimelineAdapter(this,mInfos);
        mRv_record.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRv_record.setAdapter(mAdapter);

    }

    private void initData() {
        mInfos = new ArrayList<>();
        for(int i=0;i<10;i++){
            TimeInfo info = new TimeInfo();
            info.setYmD("2017\n04/05");
            info.setHour("0"+i+":00");
            info.setTitle("标题党");
            info.setContent("内容新颖，干货满满");
            info.setAddTime("16:4"+i);
            mInfos.add(info);
        }
    }
}
