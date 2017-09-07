package com.example.wqj.testmyapi.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wqj.testmyapi.Bean.EntityMeetingRoomQueryWeekNew;
import com.example.wqj.testmyapi.R;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqj on 2017/9/4.
 */

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    List<EntityMeetingRoomQueryWeekNew> mEntityMeetingRoomQueryWeekNews = new ArrayList<>();

    public ListViewAdapter(Context context, List<EntityMeetingRoomQueryWeekNew> entityMeetingRoomQueryWeekNews) {
        mContext = context;
        mEntityMeetingRoomQueryWeekNews = entityMeetingRoomQueryWeekNews;
    }

    @Override
    public int getCount() {
        return mEntityMeetingRoomQueryWeekNews.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvDate.setText(mEntityMeetingRoomQueryWeekNews.get(position).getTime());
        viewHolder.mTvWeekDay.setText(mEntityMeetingRoomQueryWeekNews.get(position).getWeek());
        JSONArray js = mEntityMeetingRoomQueryWeekNews.get(position).getJSONArray();
        int length = js.length();
        for (int i = 0; i < mEntityMeetingRoomQueryWeekNews.size(); i++) {
            if (i == position) {
                viewHolder.mLayoutContainer.removeAllViews();
                for (int j = 0; j < length; j++) {
                    View viewOne = LayoutInflater.from(mContext).inflate(R.layout.contatinerlayout, null);
                    ViewHolder1 viewHolder1 = new ViewHolder1(viewOne);
                    String imgurl = "http://www.surenetsoft.com:90/snsoft2/";
                    String name = "";
                    viewHolder1.mMeetingRoomPic.setImageResource(R.mipmap.ic_launcher);
                    try {
                        name = js.getJSONObject(j).getString("Meeting_Room_Name");
                        String picUrl = js.getJSONObject(j).getString("MeetingPic");
                        JSONArray jsarray = js.getJSONObject(j).getJSONArray("data2");
                        for (int k = 0; k < jsarray.length(); k++) {
                            View viewTwo = LayoutInflater.from(mContext).inflate(R.layout.timeschedule, null);
                            ViewHolder3 viewholder3 = new ViewHolder3(viewTwo);
                            String time = jsarray.getJSONObject(k).getString("DateTime");
                            String isused = jsarray.getJSONObject(k).getString("isused");
                            String canuse = jsarray.getJSONObject(k).getString("canuse");
                            String meetingid = jsarray.getJSONObject(k).getString("meetingid");
                            String sortno = jsarray.getJSONObject(k).getString("sortno");
                            if (isused.equals("0")) {
                                viewholder3.mTvIsFree.setText("空");
                                if (canuse.equals("0")) {
                                    viewholder3.mLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(mContext, "无法发起会议", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
                                    viewholder3.mLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(mContext, "可以发起会议", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } else {
                                viewholder3.mTvIsFree.setText("占");
                                viewholder3.mLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(mContext, "点击进入详情", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            viewholder3.mTvTime.setText(time);
                            viewHolder1.mLayoutMeeitngRoomTime.addView(viewTwo);
                        }
                        if (!picUrl.equals("")) {
                            picUrl = js.getJSONObject(j).getString("MeetingPic").substring(0, js.getJSONObject(j).getString("MeetingPic").length() - 1);
                            Uri uri = Uri.parse(imgurl + picUrl);
//                            Log.w("图片地址",imgurl + picUrl);
                            viewHolder1.mMeetingRoomPic.setImageURI(uri);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    viewHolder1.mMeetingRoomName.setText(name);
                    viewHolder.mLayoutContainer.addView(viewOne);
                }
            }
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.img)
        ImageView mImg;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.tv_weekDay)
        TextView mTvWeekDay;
        @BindView(R.id.re_week)
        RelativeLayout mReWeek;
        @BindView(R.id.list_meeitngRoomWeek)
        LinearLayout mLayoutContainer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 {
        @BindView(R.id.meetingRoomPic)
        SimpleDraweeView mMeetingRoomPic;
        @BindView(R.id.meetingRoomName)
        TextView mMeetingRoomName;
        @BindView(R.id.layout_meeitngRoomTime)
        LinearLayout mLayoutMeeitngRoomTime;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder3 {
        @BindView(R.id.tv_isFree)
        TextView mTvIsFree;
        @BindView(R.id.tv_Time)
        TextView mTvTime;
        @BindView(R.id.tv_meetingID)
        TextView mTvMeetingID;
        @BindView(R.id.layout)
        LinearLayout mLayout;

        ViewHolder3(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
