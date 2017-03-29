package com.mmyz.routermodule;

import android.content.Context;
import android.os.Parcelable;

import com.mmyz.routermodule.exception.NotRuleException;
import com.mmyz.routermodule.routerRule.ActivityRule;
import com.mmyz.routermodule.routerRule.IRule;
import com.mmyz.routermodule.routerRule.ReceiverRule;
import com.mmyz.routermodule.routerRule.ServiceRule;

import java.util.HashMap;
import java.util.Set;

/**
 * ==============================================
 * <p>
 * 类名：RouterManager
 * &nbsp 路由规则管理 单例模式 single instance
 * &nbsp 可通过{@link RuleManager#get()}获取对象
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public class RuleManager {

    private static RuleManager mRouterManagerInstance;

    private HashMap<String,IRule> mRules;

    private RuleManager(){
        mRules = new HashMap<>();
        initDefaultRouter();
    }

    private void initDefaultRouter() {
        mRules.put(ActivityRule.PROTOCOL,new ActivityRule());
        mRules.put(ServiceRule.PROTOCOL,new ServiceRule());
        mRules.put(ReceiverRule.PROTOCOL,new ReceiverRule());
    }

    static RuleManager get(){
        if (mRouterManagerInstance == null){
            synchronized (RuleManager.class){
                if (mRouterManagerInstance == null){
                    mRouterManagerInstance = new RuleManager();
                }
            }
        }
        return mRouterManagerInstance;
    }

    public Set<String> getProtocol(){
        return mRules.keySet();
    }


    /**
     * 添加 自定义路由及其路由协议
     * @param protocol 协议 Example{@link ActivityRule#PROTOCOL}
     * @param rule 新路由
     * @return {@link RuleManager}
     */
    public final RuleManager putRule(String protocol, IRule rule){
        mRules.put(protocol,rule);
        return this;
    }

    /**
     * 根据Uri获取路由
     * @param uri 路由Uri
     * @param <T> 路由类型
     * @param <V> 路由最后返回对象类型
     * @return {@link IRule}
     */
    private <T,V> IRule<T,V> getRule(String uri){
        Set<String> protocols = mRules.keySet();
        IRule<T,V> iRule = null;
        for (String protocol :
                protocols) {
            if (uri.startsWith(protocol)){
                iRule = mRules.get(protocol);
                break;
            }
        }
        if (iRule == null){
            throw new NotRuleException(uri);
        }
        return iRule;
    }

    /**
     * 向对应的路由规则中添加路由地址
     * @param uri 路由地址
     * @param clazz 最后路由返回类型
     * @return
     */
    public final <T> RuleManager rulePutRouter(String uri , Class<T> clazz){
        IRule<T, ?> rule = getRule(uri);
        rule.putRouter(uri,clazz);
        return this;
    }

    /**
     * 通过对应的路由规则 执行路由
     * @param ctx Context
     * @param uri 路由地址
     * @return
     */
    final <K> K ruleInvokeRouter(Context ctx,String uri){
        IRule<?,K> rule = getRule(uri);
        return rule.invokeRouter(ctx,uri);
    }

    /**
     * 通过对应路由规则 执行路由
     * @param uri 路由地址
     * @return
     */
    public boolean ruleCheckRouter(String uri){
        IRule<?,?> rule = getRule(uri);
        return rule.checkRouter(uri);
    }
}
