package com.mmyz.routermodule.routerRule;

import android.app.Service;

import com.mmyz.routermodule.exception.NotServiceRuleException;

/**
 * ==============================================
 * <p>
 * 类名：ServiceRule
 * &nbsp Service路由
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public class ServiceRule extends BaseIntentRule<Service> {
    public static final String PROTOCOL = "service://";

    /**
     * {@inheritDoc}
     */
    @Override
    public void throwException(String uri) {
        throw new NotServiceRuleException(uri);
    }
}
