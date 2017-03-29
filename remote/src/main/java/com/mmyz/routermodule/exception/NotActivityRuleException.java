package com.mmyz.routermodule.exception;

/**
 * ==============================================
 * <p>
 * 类名：NotActivityRuleException
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public class NotActivityRuleException extends NotRuleException {
    public NotActivityRuleException(String uri) {
        super("Activity", uri);
    }
}
