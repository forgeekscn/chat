package com.hechao.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/4/9.
 */
public class FriendStatusActivity extends Activity {


    @OnClick(R.id.runnersworld)
    void runnerworld() {

        Intent intent = new Intent(FriendStatusActivity.this, RunnerWorld.class);
        startActivity(intent);

    }

    @OnClick(R.id.myrunnersfriends)
    void friends() {
        Intent intent = new Intent(FriendStatusActivity.this, FriendListActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.runnerConversation)
    void setConversation() {
        RongIM.getInstance().startConversationList(FriendStatusActivity.this);
    }

    @OnClick(R.id.farest)
    void setFarest() {
        Intent intent = new Intent(FriendStatusActivity.this, Farest.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendstatus);

        ButterKnife.inject(FriendStatusActivity.this);

    }


}
