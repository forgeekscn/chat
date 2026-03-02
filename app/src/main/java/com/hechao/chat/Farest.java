package com.hechao.chat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Farest extends Activity {

    List<String> namelist = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farest);

        namelist = new ArrayList<String>();


        final ListView listView = (ListView) findViewById(R.id.ranklistview);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return namelist.size();
            }

            @Override
            public Object getItem(int position) {
                return namelist.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(Farest.this);
                View view = inflater.inflate(R.layout.rankitem, null);

                TextView name = (TextView) view.findViewById(R.id.rankitemname);
                name.setText(namelist.get(position));
                return view;
            }
        });


        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://" + App.ip + "/chat/getFarestUser.php";
        Log.e("hechao",url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("hechao",response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject= (JSONObject) jsonArray.get(j);


                        String name=jsonObject.getString("name");
                        Double totalDistance=jsonObject.getDouble("totalDistance");

                        if(totalDistance.toString().length()>6){
                            name=name+"  "+ totalDistance.toString().substring(0,5);
                        }else
                            name=name+"  "+ totalDistance.toString();
                        namelist.add(name);
                    }

                    ((BaseAdapter) (listView.getAdapter())).notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }


}
