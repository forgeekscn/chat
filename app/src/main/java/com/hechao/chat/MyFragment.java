package com.hechao.chat;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/31.
 */
public class MyFragment extends Fragment {

    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.myfragment,container,false);
        textView= (TextView) view.findViewById(R.id.textview1);
        textView.setText( getArguments().getString("textView") );
        return view;
    }

}
