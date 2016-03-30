package com.hechao.chat;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/3/30.
 */
public class StationData {

    public void print(Object o) {
        Log.e("hechao", o.toString());
    }

    //获取周边加油站信息
    public void juheOil() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("city", "黄冈");
        params.add("key", "10eaa1e59ceeb2cbcd1ac446da43e3a9");
//        params.add("keywords","");
//        params.add("page","");
//        params.add("format","");
        String url = "http://apis.juhe.cn/oil/region";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
//                Log.e("hechao", "response->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    print(response);
                    int code = jsonObject.getInt("resultcode");
                    print(code);
                    if (code==200) {
                        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i1);
                            Station station = new Station();

                            station.setName(jsonObject1.getString("name"));
                            station.setLat(jsonObject1.getDouble("lat"));
                            station.setLon(jsonObject1.getDouble("lon"));
                            station.setAddress(jsonObject1.getString("address"));
                            station.setBrandname(jsonObject1.getString("brandname"));
                            station.setPrice(jsonObject1.getJSONObject("price").getDouble("E93") );
//                            station.setDistance(jsonObject1.getString("distance"));
                            //-----------------油价尚未获取-------------------

//                        JSONArray array= jsonObject1.getJSONArray("price");
//                        ArrayList<Petrol> priceList= new ArrayList<Petrol>();
//                        for(int j=0;j<array.length();j++){
//                            print(array.getJSONObject(j).getDouble("price"));
//                            Petrol petrol= new Petrol();
//                            petrol.setPrice(array.getJSONObject(j).getDouble("price"));
//                            petrol.setType(array.getJSONObject(j).getString("type"));
//                            priceList.add(petrol);
//                        }
//
//                        station.setPriceList(priceList);

//                            Log.e("hechao", "station" + i1 + "->" + station.toString());


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Log.e("hechao", "聚合加油站post->response" + response);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }


    public void getStationData(double lat, double lon, int distance) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("lat", lat);
        params.put("lon", lon);
        params.put("distance", distance);
        String url = "http://apis.juhe.cn/oil/local";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);

                Log.e("hechao", "stationlocal response : " + response);

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e("hechao", "error");
            }


        });


    }


}


