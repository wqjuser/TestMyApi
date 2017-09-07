package com.example.wqj.testmyapi.Bean;

import java.util.List;

/**
 * ================================================
 * 作    者：温清洁
 * 版    本：1.0
 * 创建日期：2017/9/4 15:34
 * 描    述：listview 实体类
 * 修订历史：
 * ================================================
 */

public class BeanListData {
    public String dateTime;
    public String zhou;
    public List<roomData> data1;

    public class roomData {
        public String REG_ID;
        public String Meeting_Room_ID;
        public String Meeting_Room_Name;
        public String MeetingPic;
        public List<timeData> data2;
    }

    public class timeData {
        public String DateTime;
        public String isused;
        public String canuse;
        public String meetingid;
        public String sortno;
    }
}
