package com.mmyz.routermodule.routerRule;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * ==============================================
 * <p>
 * 类名：BaseIntentRule
 * &nbsp 基类Intent路由规则
 * <p>
 * 作者：M-Liu
 * <p>
 * 时间：2017/3/23
 * <p>
 * 邮箱：ms_liu163@163.com
 * <p>
 * ==============================================
 */

public abstract class BaseIntentRule<T> implements IRule<T,Intent> {

    private HashMap<String,Class<T>> mIntentRules ;

    /**
     * Construct
     * 内部创建路由路线管理容器 mIntentRules
     */
    public BaseIntentRule(){
        mIntentRules = new HashMap<>();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void putRouter(String uri, Class<T> clazz) {
        if (mIntentRules != null){
            mIntentRules.put(uri,clazz);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Intent invokeRouter(Context context, String uri) {
        Class<T> clazz = null;
        if (checkRouter(uri)){
            clazz = mIntentRules.get(uri);
        }
        if (clazz == null){
            throwException(uri);
        }
        return new Intent(context,clazz);
    }

    /**
     * 抛出异常
     * @param uri 异常路由地址
     */
    public abstract void throwException(String uri);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkRouter(String uri) {
        return mIntentRules!=null && mIntentRules.keySet().contains(uri);
    }
}

