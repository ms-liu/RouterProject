package com.mmyz.routermodule;

import android.content.Context;

import com.mmyz.routermodule.exception.NotFoundClassException;
import com.mmyz.routermodule.routerRule.ActivityRule;
import com.mmyz.routermodule.routerRule.IRule;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * ==============================================
 * <p>
 * 类名：Router
 * &nbsp  路由类 对路由的 实际操作
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public class Router {
    /**
     * 添加 自定义路由及其路由协议
     * @param protocol 协议 Example{@link ActivityRule#PROTOCOL}
     * @param rule 新路由
     * @return {@link RuleManager}
     */
    public static RuleManager putCustomRule(String protocol, IRule rule){
        return RuleManager.get().putRule(protocol,rule);
    }

    /**
     * 根据默认规则自动解析Uri
     * @param uri 路由地址 Ac
     */
    public static RuleManager putRemoteUriDefaultPattern(String uri) throws NotFoundClassException {
        // (activity://com.mmyz.account.LoginActivity)
        String[] infos = parserUri(uri);
        String protocol = infos[0];
        String page = infos[1];
        try {
            putRouter(uri,Class.forName(page));
        } catch (ClassNotFoundException e) {
            throw new NotFoundClassException(page, uri);
        }
        return RuleManager.get();
    }

    private static String[] parserUri(String uri) {
        Pattern pattern = Pattern.compile("[/]+");
        return pattern.split(uri);
    }

    /**
     * 添加路由地址
     * @param uri 路由地址
     * @param clazz 最后路由返回类型
     * @return
     */
    public static <T> RuleManager putRouter(String uri,Class<T> clazz){
        return RuleManager.get().rulePutRouter(uri,clazz);
    }

    /**
     * 通过对应的路由规则 执行路由
     * @param ctx Context
     * @param uri 路由地址
     * @return
     */
    public static <K> K invoke(Context ctx,String uri){
        return RuleManager.get().ruleInvokeRouter(ctx,uri);
    }

    public static void invokeAndHref(Context context,String uri){
        Set<String> protocols = RuleManager.get().getProtocol();
        String[] infos = parserUri(uri);
        String protocol = infos[0];
        String page = infos[1];

    }

    /**
     * 检查路由地址是否存在
     * @param uri 路由地址
     * @return
     */
    public static boolean checkRouter(String uri){
        return RuleManager.get().ruleCheckRouter(uri);
    }
}
