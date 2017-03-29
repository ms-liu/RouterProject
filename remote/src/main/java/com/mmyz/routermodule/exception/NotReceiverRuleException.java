package com.mmyz.routermodule.exception;

/**
 * ==============================================
 * <p>
 * 类名：NotReceiverRuleException
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public class NotReceiverRuleException extends NotRuleException {
    public NotReceiverRuleException(String uri) {
        super("Receiver", uri);
    }
}
