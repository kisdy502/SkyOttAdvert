package cn.sdt.ottadvert.db.upgrade;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * Created by SDT13411 on 2018/3/22.
 */

public class VersionFactory {
    private final static String TAG = "VersionFactory";

    public static ArrayMap<Integer, Upgrade> getUpgradeList(int oldVersion, int newVersion, Context context) {
        long start = System.currentTimeMillis();
        ArrayMap<Integer, Upgrade> upgradeList = new ArrayMap<>();
        Upgrade obj = null;
        List<String> classNameList = getClsses(context);
        try {
            for (String clsName : classNameList) {
                Class clz = Class.forName(clsName);
                if (clz.getSuperclass() == Upgrade.class) {
                    DbVersionCode vCode = (DbVersionCode) clz.getAnnotation(DbVersionCode.class);
                    if (null == vCode) {
                        throw new IllegalStateException(clz.getName()
                                + "类必须使用DbVersionCode类注解");
                    }
                    int value = vCode.value();
                    Log.d(TAG, "value:" + value);
                    if (value > oldVersion && value <= newVersion) {
                        obj = (Upgrade) clz.newInstance();
                        upgradeList.put(value, obj);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        Log.d(TAG, "take time:" + (end - start));
        return upgradeList;
    }


    private static List<String> getClsses(Context context) {
        String pkg = VersionFactory.class.getPackage().getName();
        Log.d(TAG, "pkg:" + pkg);
        List<String> classNameList = new ArrayList<String>();
        try {
            //通过DexFile查找当前的APK中可执行文件
            DexFile df = new DexFile(context.getPackageCodePath());
            //获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            Enumeration<String> enumeration = df.entries();
            while (enumeration.hasMoreElements()) {                         //遍历
                String className = (String) enumeration.nextElement();
                if (className.contains(pkg)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classNameList.add(className);
                    Log.d(TAG, "className:" + className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classNameList;
    }
}
