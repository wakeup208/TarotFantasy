package com.wakeup.tarot.data;

import android.content.Context;

import com.wakeup.tarot.util.FileEncryptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class StarJasonHelper {
    // JSON Node names for Number
    private static final String STAR_ID = "idStar";
    private static final String NAME_IMAGE = "nameImage";
    private static final String TITLE = "title";
    private static final String FIRST_INFO = "firstInfo";
    private static final String SECOND_INFO = "secondInfo";

    // contacts JSONArray
    private static JSONArray stars = null;

    /**
     * Load Cards detail from asset folder
     *
     * @return true if success and false if otherwise
     */
    public static boolean loadJSon(Context appContext) {
        if (stars == null) {
            // Read file from assets folder
            try {
                InputStream is;
                is = appContext.getAssets().open("Star.json.enc");
                String bufferString;

                bufferString = FileEncryptor.decrypt(is);

                // Convert string to JSONArray
                stars = new JSONArray(bufferString);

                return true;
            } catch (Throwable e) {
            }
        }

        return false;
    }

    public static String getNameImage(int cardId) {
        JSONObject jo;
        try {
            jo = stars.getJSONObject(cardId);

            return jo.getString(NAME_IMAGE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getTitle(int cardId) {
        JSONObject jo;
        try {
            jo = stars.getJSONObject(cardId);

            return jo.getString(TITLE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getFirstInfo(int cardId) {
        JSONObject jo;
        try {
            jo = stars.getJSONObject(cardId);

            return jo.getString(FIRST_INFO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getSecondInfo(int cardId) {
        JSONObject jo;
        try {
            jo = stars.getJSONObject(cardId);

            return jo.getString(SECOND_INFO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
