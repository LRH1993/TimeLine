package com.lvr.timeline.home.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.lvr.timeline.R;
import com.lvr.timeline.base.BaseActivity;
import com.lvr.timeline.bean.TimeInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * Created by lvr on 2017/4/8.
 */

public class EditActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ed_title)
    EditText mEdTitle;
    @BindView(R.id.ed_content)
    EditText mEdContent;
    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_today)
    TextView mTvToday;
    private TimeInfo mInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mToolbar.setTitle("添加任务");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        initDate();
        setPreInformation(intent);
        mTvTime.setOnClickListener(this);

    }

    /**
     * 初始化时间
     */
    private void initDate() {
        mInfo = new TimeInfo();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String allTime = format.format(new Date());
        int index = allTime.indexOf(" ");
        String ymd = allTime.substring(0, index);
        String hm = allTime.substring(index + 1);
        setDate(ymd, hm);
    }

    /**
     * 设置启动Intent时，携带的信息
     *
     * @param intent
     */
    private void setPreInformation(Intent intent) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                TimeInfo info = (TimeInfo) extras.get("TimeInfo");
                if (info != null) {
                    if (info.getTitle() != null) {
                        mEdTitle.setText(info.getTitle());
                    }
                    if (info.getContent() != null) {
                        mEdContent.setText(info.getContent());
                    }
                    if (info.getYmD() != null) {
                        mTvToday.setText(info.getYmD());
                    }
                    if (info.getAddTime() != null) {
                        mTvTime.setText(info.getAddTime());
                    }
                }

            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolabr, menu);
        MenuItem item = menu.findItem(R.id.mn_it_edit);
        item.setIcon(R.drawable.mn_right);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String title = mEdTitle.getText().toString();
                String content = mEdContent.getText().toString();
                mInfo.setTitle(title);
                mInfo.setContent(content);
                mInfo.setYmD(mTvToday.getText().toString());
                mInfo.setAddTime(mTvTime.getText().toString());
                System.out.println(mInfo);
                EventBus.getDefault().post(mInfo);
                finish();
                return true;
            }
        });
        return true;
    }

    /**
     * 设置具体时间信息
     *
     * @param ymd
     * @param hm
     */
    private void setDate(String ymd, String hm) {
        //获得当前小时时间
        String hour = hm.substring(0, 2);
        int cur_hour = Integer.parseInt(hour);
        if (cur_hour > 22) {
            Date date = new Date();//取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);
            date = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ymd = formatter.format(date);
        }
        mTvTime.setText(hm + "-" + hm);
        mTvToday.setText(ymd);
    }


    @Override
    public void onClick(View v) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                EditActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false);
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0" + hourOfDayEnd : "" + hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0" + minuteEnd : "" + minuteEnd;
        String time = hourString + "：" + minuteString + " - " + hourStringEnd + "：" + minuteStringEnd;

        mTvTime.setText(time);
    }

}
