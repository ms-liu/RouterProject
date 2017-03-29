package com.mmyz.routermodule.routerRule;

import android.content.Context;

/**
 * ==============================================
 * <p>
 * 类名：Rule<br>
 * &nbsp 路由接口<br>
 * &nbsp T 路由类型<br>
 * &nbsp K 路由执行后最终返回类型
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public interface IRule<T,K> {
    /**
     * 添加路由路线
     * @param uri 路由地址
     * @param clazz 路由类型
     */
    void putRouter(String uri,Class<T> clazz);

    /**
     * 执行路由路线
     * @param context Context
     * @param uri 路由地址
     * @return {@link BaseIntentRule#invokeRouter(Context, String)}
     */
    K invokeRouter(Context context,String uri);

    /**
     * 检查当前路由路线 是否存在
     * @param uri 路由地址
     * @return
     */
    boolean checkRouter(String uri);
}
