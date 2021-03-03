package com.ulling.sample;

import com.ulling.lib.core.base.QcBaseApplication;

public class App extends QcBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        CLICK_INTERVAL = 2000l;
        IS_CLICK_ALL = true;
    }
}
