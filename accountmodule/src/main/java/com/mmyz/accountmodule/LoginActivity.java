package com.mmyz.accountmodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mmyz.annotation.Component;
import com.mmyz.annotation.StaticRouter;
import com.mmyz.routermodule.routerRule.ActivityRule;

/**
 * ==============================================
 * <p>
 * 类名：登录
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/24
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */
@Component("account")
@StaticRouter(ActivityRule.PROTOCOL+"com.mmyz.accountmodule.LoginActivity")
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btn_check_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
