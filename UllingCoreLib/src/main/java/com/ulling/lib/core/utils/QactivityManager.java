package com.ulling.lib.core.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * 액티비티 매니져
 * 로그 아웃 등 액티비티 관리
 *
 * @author kiruya80@gmail.com
 */
public class QactivityManager {
    private static QactivityManager activityManager = null;
    private ArrayList<Activity> activityList = null;

    private QactivityManager() {
        activityList = new ArrayList<Activity>();
    }

    public static QactivityManager getInstance() {
        if (QactivityManager.activityManager == null) {
            activityManager = new QactivityManager();
        }
        return activityManager;
    }

    /**
     * 액티비티 리스트에 추가.
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityList == null)
            activityList = new ArrayList<Activity>();
        activityList.add(activity);
    }

    /**
     * 액티비티 리스트에서 삭제.
     *
     * @param activity
     * @return boolean
     */
    public boolean removeActivity(Activity activity) {
        if (activityList != null) {
            return activityList.remove(activity);
        } else {
            return false;
        }
    }

    /**
     * 액티비티 리스트 getter.
     *
     * @return activityList
     */
    public ArrayList<Activity> getActivityList() {
        if (activityList != null) {
            return activityList;
        } else {
            return null;
        }
    }

    /**
     * 삭제할 Activity를 찾아서 종료해주는 함수 (onBackPressed 함수에 셋팅)
     *
     * @param activity
     */
    public void finishedActivity(Activity activity) {
        if (activityList == null) {
            return;
        }
        int index = activityList.indexOf(activity);
        if (index >= 0) {
            Activity at = activityList.remove(index);
            at.finish();
        }
    }

    /**
     * Activity 이름으로 Activity를 찾아오는 함수
     *
     * @param className
     * @return
     */
    public Activity findActivity(String className) {
        if (activityList == null) {
            return null;
        }
        Activity activity = null;
        for (Activity at : activityList) {
            if (className.equals(at.getClass())) {
                activity = at;
                break;
            }
        }
        return activity;
    }

    /**
     * 모든 액티비티 종료.
     */
    public void finishAllActivity() {
        if (activityList == null) {
            return;
        }
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        activityList = null;
    }
}