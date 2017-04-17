package com.lvr.timeline.home.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.lvr.timeline.R;
import com.lvr.timeline.app.AppConstantValue;
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
    private int mPosition = -2;
    private AlertDialog mDialog;

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
        setPreInformation(intent);
        mTvTime.setOnClickListener(this);

    }

    /**
     * 初始化时间
     */
    private void initDate() {
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
                mInfo = (TimeInfo) extras.get("TimeInfo");
                if (mInfo != null) {
                    if (mInfo.getTitle() != null) {
                        mEdTitle.setText(mInfo.getTitle());
                    }
                    if (mInfo.getContent() != null) {
                        mEdContent.setText(mInfo.getContent());
                    }
                    if (mInfo.getYmD() != null) {
                        mTvToday.setText(mInfo.getYmD());
                    }
                    if (mInfo.getAddTime() != null) {
                        mTvTime.setText(mInfo.getAddTime());
                    }
                    mInfo.setNew(false);
                }
                mPosition = (int) extras.get("position");
                if (mPosition != -2) {
                    mInfo.setPosition(mPosition);
                }

            } else {
                initDate();
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
                if (verfyContent()) {
                    String title = mEdTitle.getText().toString();
                    System.out.println("title的内容：" + title);
                    String content = mEdContent.getText().toString();
                    if (mInfo == null) {
                        mInfo = new TimeInfo();
                    }
                    mInfo.setTitle(title);
                    mInfo.setContent(content);
                    mInfo.setYmD(mTvToday.getText().toString());
                    mInfo.setAddTime(mTvTime.getText().toString());
                    System.out.println(mInfo);
                    EventBus.getDefault().post(mInfo);
                    finish();
                }
                return true;
            }
        });
        return true;
    }

    /**
     * 任务内容格式是否符合
     */
    private boolean verfyContent() {
        if (TextUtils.isEmpty(mEdTitle.getText().toString())) {
            //开启AlertDialog提示
            showErrorDialog(AppConstantValue.HINT_TITLE);
            return false;
        } else {
            boolean flag = verfyTime();
            return flag;
        }
    }

    /**
     * 创建AlertDialog提示
     *
     * @param hint
     */
    private void showErrorDialog(final String hint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_view, null);
        Button btn_hint = (Button) view.findViewById(R.id.btn_hint);
        TextView tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        tv_hint.setText(hint);
        builder.setView(view, 0, 0, 0, 0);
        btn_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                if (hint.equals(AppConstantValue.HINT_TITLE)) {
                    verfyTime();
                }
            }
        });
        mDialog = builder.create();
        mDialog.show();


    }

    /**
     * 检查时间段设置是否正确
     */
    private boolean verfyTime() {
        String time = mTvTime.getText().toString();
        String[] split = time.split("-");
        String begin = split[0];
        String end = split[1];
        String[] begin_split = begin.split(":");
        String[] end_split = end.split(":");
        int hour_begin = Integer.parseInt(begin_split[0]);
        int minute_begin = Integer.parseInt(begin_split[1]);
        int hour_end = Integer.parseInt(end_split[0]);
        int minute_end = Integer.parseInt(end_split[1]);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String cur_time = format.format(new Date());
        String[] cur_split = cur_time.split(":");
        int hour_cur = Integer.parseInt(cur_split[0]);
        int minute_cur = Integer.parseInt(cur_split[1]);
        //获取当前的年月日信息
        format = new SimpleDateFormat("yyyy-MM-dd");
        String cur_date = format.format(new Date());
        String[] date_split = cur_date.split("-");
        int cur_day = Integer.parseInt(date_split[2]);
        String show_date = mTvToday.getText().toString();
        String[] show_split = show_date.split("-");
        int show_day = Integer.parseInt(show_split[2]);
        if (show_day == cur_day) {
            if (hour_cur > hour_begin) {
                showErrorDialog(AppConstantValue.HINT_MISS);
                return false;
            } else if (hour_cur == hour_begin) {
                if (minute_cur > minute_begin) {
                    showErrorDialog(AppConstantValue.HINT_MISS);
                    return false;
                }
            }
        }

        if (hour_begin > hour_end) {
            showErrorDialog(AppConstantValue.HINT_TIME);
            return false;
        } else if (hour_begin == hour_end) {
            if (minute_begin > minute_end) {
                showErrorDialog(AppConstantValue.HINT_TIME);
                return false;
            }
        }
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
        System.out.println("hourOfDay:" + hourOfDay);
        System.out.println("minute:" + minute);
        System.out.println("hourOfDayEnd:" + hourOfDayEnd);
        System.out.println("minuteEnd:" + minuteEnd);
        //这个日历库有个BUG 12点与24点显示有误
        int temp = hourOfDay;
        if (temp == 0) {
            hourOfDay = 12;
        }
        if (temp == 12) {
            hourOfDay = 0;
        }
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0" + hourOfDayEnd : "" + hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0" + minuteEnd : "" + minuteEnd;
        String time = hourString + ":" + minuteString + "-" + hourStringEnd + ":" + minuteStringEnd;

        mTvTime.setText(time);
    }

}
