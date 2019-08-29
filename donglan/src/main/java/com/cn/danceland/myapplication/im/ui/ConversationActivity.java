package com.cn.danceland.myapplication.im.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cn.danceland.myapplication.R;

/**
 * Created by shy on 2018/6/25 15:18
 * Email:644563767@qq.com
 */


public class ConversationActivity extends FragmentActivity {


    private ConversationFragment chatFragment;
    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);

        //get user id or group id

        //use EaseChatFratFragment
        chatFragment = new ConversationFragment();
        //pass parameters to chat fragment
      //  chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

}
