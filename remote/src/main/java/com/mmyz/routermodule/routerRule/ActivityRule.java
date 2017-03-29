package com.mmyz.routermodule.routerRule;

import android.support.v7.app.AppCompatActivity;

import com.mmyz.routermodule.exception.NotActivityRuleException;

/**
 * ==============================================
 * <p>
 * 类名：ActivityRule
 * &nbsp Activity路由
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public class ActivityRule extends BaseIntentRule<AppCompatActivity> {

    public static final String PROTOCOL = "activity://";

    /**
     * {@inheritDoc}
     */
    @Override
    public void throwException(String uri) {
        throw new NotActivityRuleException(uri);
    }
}
