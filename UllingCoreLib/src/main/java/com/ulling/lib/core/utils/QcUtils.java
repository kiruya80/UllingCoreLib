package com.ulling.lib.core.utils;

import static android.content.Context.WINDOW_SERVICE;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.ulling.lib.core.base.QcBaseApplication;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class QcUtils {

    public static final String PLAY_STORE_MARKET_DETAIL = "market://details?id=";
    public static final String PLAY_STORE_MARKET_DETAIL_APPS = "https://play.google.com/store/apps/details?id=";

    public static final int UPDATE_PASS = 0;
    public static final int UPDATE_MAJOR = 1;
    public static final int UPDATE_BUG = 2;
    public static final int UPDATE_MINOR = 3;

    /**
     * 앱 업데이트가 필요한지 체크 ex) 1.2.30 ㄴ 1 : 메이져 ㄴ 2 : 버그 ㄴ 3 : 마이너
     *
     * @param context
     * @param localAppVer
     * @param svrAppVer
     * @return
     */
    public static int isUpdateVersionApp(Context context, String localAppVer, String svrAppVer) {
        if (localAppVer != null && !"".equals(localAppVer)) {
            if (svrAppVer != null && !"".equals(svrAppVer)) {
                String[] localAppVerSplit = localAppVer.split("\\.", -1);
                String[] svrAppVerSplit = svrAppVer.split("\\.", -1);

                for (int i = 0; i < localAppVerSplit.length; i++) {
                    QcLog.e("localAppVerSplit === " + localAppVerSplit[i]);
                }
                for (int i = 0; i < svrAppVerSplit.length; i++) {
                    QcLog.e("svrAppVerSplit === " + svrAppVerSplit[i]);
                }
                if (localAppVerSplit == null || svrAppVerSplit == null) {
                    return UPDATE_PASS;
                }

                try {
                    int localAppMajor = Integer.parseInt(localAppVerSplit[0]);
                    int svrAppMajor = Integer.parseInt(svrAppVerSplit[0]);
                    if (localAppMajor < svrAppMajor) {
                        // 서버 버젼이 높은 경우 필수 업데이트
                        return UPDATE_MAJOR;
                    }

                    int localAppBug = Integer.parseInt(localAppVerSplit[2]);
                    int svrAppBug = Integer.parseInt(svrAppVerSplit[2]);
                    if (localAppBug < svrAppBug) {
                        // 버그수정 업데이트로 사용자 선택적 업데이트
                        return UPDATE_BUG;
                    }

                    int localAppMinor = Integer.parseInt(localAppVerSplit[1]);
                    int svrAppMinor = Integer.parseInt(svrAppVerSplit[1]);
                    if (localAppMinor < svrAppMinor) {
                        // 넘어가도 상관없는 마이너한
                        return UPDATE_MINOR;
                    }

                } catch (NumberFormatException e) {
                    return UPDATE_PASS;
                } catch (NullPointerException e) {
                    return UPDATE_PASS;
                }

            } else {
                // 서버 버젼이 없는 경우 패스
                return UPDATE_PASS;
            }

        } else {
            // 로컬 버젼이 없는 경우 패스
            return UPDATE_PASS;
        }
        return UPDATE_PASS;
    }

    public static DisplayMetrics getDisplay() {
        DisplayMetrics dm = QcBaseApplication.getInstance().getResources().getDisplayMetrics();

//        int width = dm.widthPixels;
//
//        int height = dm.heightPixels;
        return dm;
    }

    /**
     * 소프트키 감추기
     *
     * @param context
     * @param editText
     */
    public static void hiddenSoftKey(Context context, EditText editText) {
        if (editText != null) {
            editText.clearFocus();
        }
        InputMethodManager im = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 소프트키 열기
     *
     * @param context
     * @param editText
     */
    public static void showSoftKey(Context context, EditText editText) {
        if (editText != null) {
            editText.requestFocus();
        }
        InputMethodManager im = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 소프트키 유무체크
     *
     * @param context
     * @return
     */
    public static boolean hasSoftMenu(Context context) {
        //메뉴버튼 유무
        boolean hasMenuKey = ViewConfiguration.get(context.getApplicationContext())
            .hasPermanentMenuKey();

        //뒤로가기 버튼 유무
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        // lg폰 소프트키일 경우
// 삼성폰 등.. 메뉴 버튼, 뒤로가기 버튼 존재
        QcLog.e("hasSoftMenu ===== hasMenuKey = " + hasMenuKey + " , hasBackKey = " + hasBackKey);
//        return !hasMenuKey && !hasBackKey;
        if (!hasMenuKey && !hasBackKey) { // lg폰 소프트키일 경우
            return true;
        } else { // 삼성폰 등.. 메뉴 버튼, 뒤로가기 버튼 존재
            return false;
        }
    }


    /**
     * 소프트키 높이 가져오기
     *
     * @param context
     * @return
     */
    public static int getSoftMenuHeight(Context context) {
        if (!hasSoftMenu(context)) {
            QcLog.e("hasSoftMenu !hasSoftMenu(context =====  = ");
            return 0;
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int softKeyHeight = 0;
        if (resourceId > 0) {
            softKeyHeight = resources.getDimensionPixelSize(resourceId);
        }
        return softKeyHeight;
    }

    public static float getRatioLength(float length, float ratioWidth, float ratioHeight) {
        return length * ratioHeight / ratioWidth;

    }

    public static int getPixelToDp(Context context, float dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
            context.getResources().getDisplayMetrics());
        return px;
    }

    /**
     * 스테이터스바 높이 가져오기
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(final Context context) {
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        } else {
            return (int) Math.ceil((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25) *
                resources.getDisplayMetrics().density);
        }
    }

    /**
     * 안드로이드 단말 아이디 가져오기
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 테블릿 체크
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        boolean bTablet = false;
        int screenSizeType = context.getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSizeType) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                bTablet = true;
                break;
            default:
                break;
        }

        return bTablet;
    }

    /**
     * 화면 방향 가져오기
     *
     * @param context
     * @return
     */
    public static int getScreenOrientation(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getWindowManager()
                .getDefaultDisplay().getMetrics(displayMetrics);

        } else if (context instanceof Application) {
            WindowManager windowManager = (WindowManager) context.getSystemService
                (WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        } else if (context instanceof Service) {
            WindowManager windowManager = (WindowManager) context.getSystemService
                (WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }

        return (displayMetrics.widthPixels < displayMetrics.heightPixels) ?
            Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 숫자형인지 체크
     *
     * @param num
     * @return
     */
    public static boolean isIntegerFromStr(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Statusbar Drawable 및 Icon 밝기 적용
     *
     * @param activity
     * @param drawableId
     */
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public static void setStatusBarDrawable(Activity activity, int drawableId, boolean isDark, boolean isFullLayout) {
//        Window window = activity.getWindow();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            if (isFullLayout) {
//                window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            }
//
//            Drawable background = activity.getResources().getDrawable(drawableId);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
//            window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
//            window.setBackgroundDrawable(background);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            View decor = window.getDecorView();
//            if (isDark) {
//                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            } else {
//                decor.setSystemUiVisibility(0);
//            }
//        }
//    }

    /**
     * Status bar color 및 Icon 밝기 적용
     */
    public static void setChangeStatusBar(AppCompatActivity act, int colorId, boolean isDark) {
        Window window = act.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(act, colorId));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            if (isDark) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(0);
            }
        }
    }


    public static int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return context.getResources().getColor(id);
        } else {
            return context.getColor(id);
        }
    }

    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id);
        } else {
            return context.getDrawable(id);
        }
    }

    /**
     * 구글 스토어 이동
     *
     * @param activity
     * @param isFinish
     */
    public static void startGoogleStore(AppCompatActivity activity, boolean isFinish) {
        final String appPackageName = activity.getPackageName();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(PLAY_STORE_MARKET_DETAIL + appPackageName));
            activity.startActivity(intent);
            if (isFinish) {
                activity.finish();
            }
        } catch (android.content.ActivityNotFoundException anfe) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(PLAY_STORE_MARKET_DETAIL_APPS + appPackageName));
            activity.startActivity(intent);
            if (isFinish) {
                activity.finish();
            }
        }
    }

    /**
     * 패키지 이름으로 구글 스토어 이동
     *
     * @param activity
     * @param appPackageName
     */
    public static void startGoogleStore(AppCompatActivity activity, String appPackageName,
        boolean isFinish) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(PLAY_STORE_MARKET_DETAIL + appPackageName));
            activity.startActivity(intent);
            if (isFinish) {
                activity.finish();
            }

        } catch (android.content.ActivityNotFoundException anfe) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(PLAY_STORE_MARKET_DETAIL_APPS + appPackageName));
            activity.startActivity(intent);
            if (isFinish) {
                activity.finish();
            }
        }
    }

    /**
     * 안드로이드 디바이스에 최신 버전의 Google Play Services가 설치되어 있는지 확인
     */
//    public static boolean isGooglePlayServicesAvailable(Context context) {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//
//        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context);
//        return connectionStatusCode == ConnectionResult.SUCCESS;
//    }

//    /*
//     * Google Play Services 업데이트로 해결가능하다면 사용자가 최신 버전으로 업데이트하도록 유도하기위해
//     * 대화상자를 보여줌.
//     */
//    public static void acquireGooglePlayServices(Context context) {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context);
//
//        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
//            Dialog dialog = apiAvailability.getErrorDialog(
//                    (AppCompatActivity) context,
//                    connectionStatusCode,
//                    QcDefine.REQUEST_GOOGLE_PLAY_SERVICES
//            );
//            dialog.show();
//        }
//    }

//    public static int getActionBarHeight(AppCompatActivity activity) {
//        int actionBarHeight = activity.getSupportActionBar().getHeight();
//        if (actionBarHeight != 0)
//            return actionBarHeight;
//        final TypedValue tv = new TypedValue();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
//                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
//        } else if (activity.getTheme().resolveAttribute(com.actionbarsherlock.R.attr.actionBarSize, tv, true))
//            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
//        return actionBarHeight;
//    }

    /**
     * https://blog.asamaru.net/2015/12/15/android-app-finish/ http://duongame.blogspot.com/2018/11/android.html
     * 앱 완전 종료
     *
     * @param activity
     */
    public static void killAppRemoveTask(AppCompatActivity activity) {
        ActivityCompat.finishAffinity(activity);
        System.runFinalization();
        System.exit(0);
        activity.moveTaskToBack(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            activity.finishAndRemoveTask();
        } else {
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static String getNowTime(String pattern) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String getTime = sdf.format(date);

        return getTime;
    }

    /**
     * intent 값 가져오기
     *
     * @param intent
     */
    public static void getIntentAll(Intent intent) {
        if (intent == null) {
            QcLog.e("Intent is null");
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();

            QcLog.e("    ┌──── ■ Get Intent Extra ■ ────┐\n");
            QcLog.e("    ┌──────────────────────────────────────────────────────────────────────────────────────────────────────\n");

            while (it.hasNext()) {
                String key = it.next();
                QcLog.e("[" + key + "=" + bundle.get(key) + "]");
            }
            QcLog.e("    └──────────────────────────────────────────────────────────────────────────────────────────────────────");

        }
    }

    public static void getArgumentsAll(Bundle bundle) {
        if (bundle == null) {
            QcLog.e("bundle is null");
            return;
        }

        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();

            QcLog.e("    ┌──── ■ Get Intent Extra ■ ────┐\n");
            QcLog.e("    ┌──────────────────────────────────────────────────────────────────────────────────────────────────────\n");

            while (it.hasNext()) {
                String key = it.next();
                QcLog.e("[" + key + "=" + bundle.get(key) + "]");
            }
            QcLog.e("    └──────────────────────────────────────────────────────────────────────────────────────────────────────");

        }
    }
}