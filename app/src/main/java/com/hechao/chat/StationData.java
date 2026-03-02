package com.hechao.chat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import cz.msebera.android.httpclient.Header;

/**
 * 加油站数据管理
 * Created by Administrator on 2016/3/30.
 */
public class StationData {

    // Fix memory leak: use static inner class with WeakReference
    private static class SafeHandler extends Handler {
        private final WeakReference<StationData> outerClass;

        SafeHandler(StationData outer) {
            outerClass = new WeakReference<>(outer);
        }

        @Override
        public void handleMessage(Message msg) {
            StationData data = outerClass.get();
            if (data == null) return;

            switch (msg.what) {
                case 0x123:
                    for (int j = 0; j < msg.getData().getInt("stationNumber"); j++) {
                        Station station = (Station) msg.getData().getSerializable("station" + (j + 1));
                        Log.e("hechao", station.toString());
                        data.stationList.add(station);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private SafeHandler handler = new SafeHandler(this);
    List<Station> stationList = null;

    public void print(Object o) {
        Log.e("hechao", o.toString());
    }

    public List<Station> getStationList() {
        return stationList;
    }

    // 获取周边加油站信息
    public void juheOil() {
        stationList = new ArrayList<Station>();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("city", "武汉");
        params.add("key", BuildConfig.API_KEY_JUHE);
        String url = "http://apis.juhe.cn/oil/region";
        
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    print(response);
                    int code = jsonObject.getInt("resultcode");
                    print(code);
                    if (code == 200) {
                        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

                        Message msg = new Message();
                        msg.what = 0x123;
                        Bundle b = new Bundle();

                        b.putInt("stationNumber", jsonArray.length());

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i1);
                            Station station = new Station();
                            station.setName(jsonObject1.getString("name"));
                            station.setLat(jsonObject1.getDouble("lat"));
                            station.setLon(jsonObject1.getDouble("lon"));
                            station.setAddress(jsonObject1.getString("address"));
                            station.setBrandname(jsonObject1.getString("brandname"));
                            station.setPrice(jsonObject1.getJSONObject("price").getDouble("E93"));

                            b.putSerializable("station" + (i1 + 1), station);
                        }

                        msg.setData(b);
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    Log.e("hechao", "Failed to parse station data", e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e("hechao", "Failed to fetch station data", throwable);
            }
        });
    }

    public void getStationData(double lat, double lon, int distance) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("lat", lat);
        params.put("lon", lon);
        params.put("distance", distance);
        params.put("key", BuildConfig.API_KEY_JUHE);
        String url = "http://apis.juhe.cn/oil/local";
        
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("hechao", "stationlocal response : " + response);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e("hechao", "Failed to fetch local station data", throwable);
            }
        });
    }
}


