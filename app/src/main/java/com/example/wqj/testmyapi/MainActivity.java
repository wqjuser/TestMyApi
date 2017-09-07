package com.example.wqj.testmyapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wqj.testmyapi.Bean.EntityMeetingRoomQueryWeekNew;
import com.example.wqj.testmyapi.adapter.ListViewAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    List<EntityMeetingRoomQueryWeekNew> listEntityWeekNew = new ArrayList<>();
    ListViewAdapter adapter;
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDataByOkGo("http://www.surenetsoft.com:90/snsoft/partner/meeting");
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Toast.makeText(MainActivity.this, "刷新中", Toast.LENGTH_SHORT).show();
                mRefresh.finishRefresh(2000);
            }
        });
        mRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Toast.makeText(MainActivity.this, "加载中", Toast.LENGTH_SHORT).show();
                mRefresh.finishLoadmore(2000);
            }
        });
    }

    public void getDataByOkGo(String url) {
        OkGo.<String>post(url)
                .tag(this)
                .retryCount(3)
                .params("actiontype", "getdata_meetingplace")
                .params("user_id", "wqj")
                .params("type", "2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String state = jsonObject.getString("result");
                            if (state.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                int length = jsonArray.length();
                                if (length > 0) {
                                    for (int i = 0; i < length; i++) {
                                        String date = jsonArray.getJSONObject(i).getString("datevalue");
                                        String zhou = jsonArray.getJSONObject(i).getString("zhou");
                                        JSONArray jsonArray2 = jsonArray.getJSONObject(i).getJSONArray("data1");
                                        EntityMeetingRoomQueryWeekNew entityWeekNew = new EntityMeetingRoomQueryWeekNew(date, zhou, jsonArray2);
                                        listEntityWeekNew.add(entityWeekNew);
                                    }
                                }
                                if (adapter == null) {
                                    adapter = new ListViewAdapter(MainActivity.this, listEntityWeekNew);
                                    mListview.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }


}
