package com.mmyz.routerproject.application;

import android.app.Application;

import com.mmyz.RouterCompiler;
import com.mmyz.annotation.Components;

/**
 * ==============================================
 * <p>
 * 类名：
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/24
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */
@Components({"account"})
public class RouterApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        initRouter();
    }

    private void initRouter(){
        RouterCompiler.compile();
//        Router.putRouter(ActivityRule.PROTOCOL+"account.index", LoginActivity.class);
    }
}
