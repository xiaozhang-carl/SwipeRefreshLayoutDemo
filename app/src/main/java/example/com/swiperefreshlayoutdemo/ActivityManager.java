package example.com.swiperefreshlayoutdemo;

import android.app.Activity;

import java.util.Stack;

/**
 * 单例模式管理activity，使其安全退出
 */

public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    /**
     * 单一实例
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                instance = new ActivityManager();
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            finishActivity(activity);
        }

    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        Stack<Activity> activities = new Stack<>();
        //找到任务stack的activity
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                activities.add(activity);
            }
        }
        finishAllActivity(activities);
    }
    /**
     * 结束指定类名的Activity
     */
    public boolean hasActivity(Class<?> cls) {
        //找到任务stack的activity
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        finishAllActivity(activityStack);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity(Stack<Activity> activityStack) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void exit() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
