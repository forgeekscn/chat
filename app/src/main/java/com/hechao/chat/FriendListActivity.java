package com.hechao.chat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/3/27.
 */
public class FriendListActivity extends Activity {

    List<String> friendList = new ArrayList<String>();
    ListView listView;

    MyAdapter myAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist);

        listView = (ListView) findViewById(R.id.friendlist);
        refreshFriendList();
        myAdapter = new MyAdapter(friendList, FriendListActivity.this);
        listView.setAdapter(myAdapter);

        final Button refresh= (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshFriendList();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String target= (String) listView.getAdapter().getItem(position);
                RongIM.getInstance().startPrivateChat(FriendListActivity.this,target,null);
            }
        });




    }





    private void refreshFriendList() {

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://10.176.137.250/chat/getFriendList.php?username=" + App.username;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
//                Log.e("hechao", "response:" + response);
                friendList.clear();

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i1 = 0; i1 < array.length(); i1++) {
                        friendList.add((String) array.get(i1));
                        Log.e("hechao",(String) array.get(i1));
                    }

                    myAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e("hechao", "friendlist is not inited ");
            }
        });

    }







    @Override
    protected void onResume() {
        super.onResume();
        refreshFriendList();

    }






}
