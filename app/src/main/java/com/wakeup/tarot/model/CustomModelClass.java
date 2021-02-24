package com.wakeup.tarot.model;

import com.wakeup.tarot.listener.OnChangeBackground;

import java.util.ArrayList;
import java.util.Iterator;

public class CustomModelClass {

    private static CustomModelClass mInstance;
    private ArrayList<OnChangeBackground> mListenerArr = new ArrayList<>();

    private CustomModelClass() {
    }

    public static CustomModelClass getInstance() {
        if (mInstance == null) {
            mInstance = new CustomModelClass();
        }
        return mInstance;
    }

    public void setListener(OnChangeBackground onChooseColorListener) {
        this.mListenerArr.add(onChooseColorListener);
    }

    public void changeState() {
        Iterator it = this.mListenerArr.iterator();
        while (it.hasNext()) {
            ((OnChangeBackground) it.next()).refreshAppBg();
        }
    }
}
