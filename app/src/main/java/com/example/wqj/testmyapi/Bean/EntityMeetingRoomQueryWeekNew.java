package com.example.wqj.testmyapi.Bean;

/**
 * Created by wqj on 2017/9/4.
 */

import org.json.JSONArray;

import java.io.Serializable;

/**
 * class_name: EntityMeetingRoomQueryWeekNew
 * package: com.snsoft.partner.MeetingManagement.entity
 * describe: 重写会场查询本周实体类
 * creat_user: 温清洁
 * creat_date: 2017/8/23
 * creat_time: 14:21
 **/

public class EntityMeetingRoomQueryWeekNew implements Serializable {
    private String time;
    private String week;
    private JSONArray mJSONArray;

    public EntityMeetingRoomQueryWeekNew(String time, String week, JSONArray JSONArray) {
        this.time = time;
        this.week = week;
        mJSONArray = JSONArray;
    }
//    private List<EntityMeetingQueryTodayList> mEntityMeetingQueryTodayLists = new ArrayList<>();



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public JSONArray getJSONArray() {
        return mJSONArray;
    }

    public void setJSONArray(JSONArray JSONArray) {
        mJSONArray = JSONArray;
    }
}

