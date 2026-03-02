package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.rong.imkit.RongIM;


/**
 * 好友列表
 */
public class FriendListActivity extends Activity {

    List<String> friendList = new ArrayList<String>();

    @InjectView(R.id.friendlist1)
    ListView listView;

    @InjectView(R.id.refresh)
    Button refresh;

    MyAdapter myAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist);
        ButterKnife.inject(FriendListActivity.this);
        refreshFriendList();
        myAdapter = new MyAdapter(friendList, FriendListActivity.this);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String friendUsername = friendList.get(position);

                AsyncHttpClient client = new AsyncHttpClient();
                String url = "http://" + App.getServerIp() + "/chat/getname.php";
                
                // Fix SQL injection: use POST with RequestParams
                RequestParams params = new RequestParams();
                params.put("username", friendUsername);
                
                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Intent intent = new Intent(FriendListActivity.this, FriendProfile.class);
                        Bundle args = new Bundle();
                        args.putString("username", friendUsername);
                        args.putString("name", new String(bytes));
                        intent.putExtras(args);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Log.e("hechao", "Failed to get friend name", throwable);
                        Toast.makeText(FriendListActivity.this, 
                            "获取好友信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @OnClick(R.id.refresh)
    void refresh() {
        refreshFriendList();
    }

    private void refreshFriendList() {
        AsyncHttpClient client = new AsyncHttpClient();

        String url = "http://" + App.getServerIp() + "/chat/getFriendList.php";
        
        // Fix SQL injection: use POST with RequestParams
        RequestParams params = new RequestParams();
        params.put("username", App.username);
        
        Log.e("hechao", "Fetching friend list for: " + App.username);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.e("hechao", "friend list response:" + response);
                friendList.clear();

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i1 = 0; i1 < array.length(); i1++) {
                        friendList.add((String) array.get(i1));
                        Log.e("hechao", (String) array.get(i1));
                    }

                    myAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("hechao", "Failed to parse friend list", e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e("hechao", "Failed to fetch friend list", throwable);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshFriendList();
    }
}
