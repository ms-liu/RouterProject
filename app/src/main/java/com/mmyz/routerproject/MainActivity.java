package com.mmyz.routerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mmyz.routermodule.Router;
import com.mmyz.routermodule.RuleManager;
import com.mmyz.routermodule.routerRule.ActivityRule;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = ActivityRule.PROTOCOL+"com.mmyz.accountmodule.LoginActivity";
                Intent invoke = Router.invoke(MainActivity.this, uri);
                startActivity(invoke);
            }
        });

    }
}
