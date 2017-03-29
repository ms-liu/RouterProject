package com.mmyz.routermodule.exception;

/**
 * ==============================================
 * <p>
 * 类名：NotRouteException
 * $nbsp 没有当前路由异常
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public class NotRuleException extends RuntimeException {
    public NotRuleException(String uri){
        super(String.format("%s 未检测到当前路由地址路由规则 %s, 请检查当前路由地址是否有问题?", "UNKNOWN", uri));
    }
    public NotRuleException(String name, String uri) {
        super(String.format("%s 不能匹配到当前路由地址 %s, 请检查当前路由地址是否存有问题?", name, uri));
    }
}
