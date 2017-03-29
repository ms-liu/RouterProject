package com.mmyz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RouterCompiler {

    public static void compile(){
        try {
            Class<?> clazz = Class.forName(Config.PACKAGE_NAME + "." + Config.ROUTER_OPERATOR);
            Method declaredMethod = clazz.getDeclaredMethod(Config.ROUTER_OPERATOR_METHOD);
            declaredMethod.invoke(null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
