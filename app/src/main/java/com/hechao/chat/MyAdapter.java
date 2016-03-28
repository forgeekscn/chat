package com.hechao.chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 会话列表 适配器
 *
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("hechao","getview ...");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.frienditem, null);
        TextView friendItem = (TextView) view.findViewById(R.id.friendItem);
        friendItem.setText(friendList.get(position));

        final String name = friendList.get(position);
        friendItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("hechao", name + "  to talk ?");
            }
        });

        return view;
    }


}
