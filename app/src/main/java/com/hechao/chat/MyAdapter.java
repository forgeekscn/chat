package com.hechao.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 会话列表 适配器
 */


public class MyAdapter extends BaseAdapter {

    List<String> friendList;
    Context context;

    public MyAdapter(List<String> friendList, Context context) {
        this.friendList = friendList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.frienditem, null);
        final TextView friendItem = (TextView) view.findViewById(R.id.friendItemName);
        final ImageView frienitempic = (ImageView) view.findViewById(R.id.friendItemPic);

        String url1="http://"+App.ip+"/chat/getname.php?username="+friendList.get(position);
        client.get(url1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response=new String(bytes);
                friendItem.setText(response);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

        final String name = friendList.get(position);
        String url = "http://"+App.ip+"/chat/pic/"+friendList.get(position)+".png";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                frienitempic.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });


        friendItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("hechao", name + "  to talk ?");
            }
        });

        return view;
    }


}
