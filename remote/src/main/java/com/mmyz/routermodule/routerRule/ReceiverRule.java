package com.mmyz.routermodule.routerRule;

import android.content.BroadcastReceiver;

import com.mmyz.routermodule.exception.NotReceiverRuleException;

/**
 * ==============================================
 * <p>
 * 类名：ReceiverRule
 * &nbsp Receiver路由
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public class ReceiverRule extends BaseIntentRule<BroadcastReceiver> {

    public static final String PROTOCOL = "receiver://";

    /**
     * {@inheritDoc}
     */
    @Override
    public void throwException(String uri) {
        throw new NotReceiverRuleException(uri);
    }
}
