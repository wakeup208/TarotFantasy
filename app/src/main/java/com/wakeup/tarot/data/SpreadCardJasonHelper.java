package com.wakeup.tarot.data;

import android.content.Context;

import com.wakeup.tarot.util.FileEncryptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class SpreadCardJasonHelper {
    // JSON Node names for Number
    private static final String SPREAD_ID = "spreadID";
    private static final String SPREAD_INFO = "spreadInfo";
    private static final String SPREAD_NAME = "spreadName";
    private static final String STEP = "step";

    // contacts JSONArray
    private static JSONArray spread_cards = null;

    /**
     * Load Cards detail from asset folder
     *
     * @return true if success and false if otherwise
     */
    public static boolean loadJSon(Context appContext) {
        if (spread_cards == null) {

            // Read file from assets folder
            try {
                InputStream is;
                is = appContext.getAssets().open("SpreadCard.json.enc");
                String bufferString;

                bufferString = FileEncryptor.decrypt(is);

                // Convert string to JSONArray
                spread_cards = new JSONArray(bufferString);

                return true;
            } catch (Throwable e) {
            }

        }

        return false;
    }

    public static String getSpreadName(int spreadId) {
        JSONObject jo;
        try {
            jo = spread_cards.getJSONObject(spreadId);

            return jo.getString(SPREAD_NAME);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getSpreadInfo(int spreadId) {
        JSONObject jo;
        try {
            jo = spread_cards.getJSONObject(spreadId);

            return jo.getString(SPREAD_INFO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get step array by id of spread
     *
     * @param spreadId
     * @return
     */
    public static String[] getStepArray(int spreadId) {
        JSONObject jo;
        try {
            // get one spread detail
            jo = spread_cards.getJSONObject(spreadId);

            // get step field
            JSONArray arrStep = jo.getJSONArray(STEP);

            // get value of step and return
            String[] arrStepDetail = new String[arrStep.length()];
            for (int i = 0; i < arrStep.length(); i++) {
                arrStepDetail[i] = arrStep.getString(i);
            }

            return arrStepDetail;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
