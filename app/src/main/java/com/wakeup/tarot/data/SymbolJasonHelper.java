package com.wakeup.tarot.data;

import android.content.Context;

import com.wakeup.tarot.util.FileEncryptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class SymbolJasonHelper {
    // JSON Node names for Number
    private static final String SYMBOL_ID = "idSymbol";
    private static final String NAME_IMAGE = "nameImage";
    private static final String TITLE = "title";
    private static final String INFO = "info";

    // contacts JSONArray
    private static JSONArray symbols = null;

    /**
     * Load Cards detail from asset folder
     *
     * @return true if success and false if otherwise
     */
    public static boolean loadJSon(Context appContext) {
        if (symbols == null) {
            // Read file from assets folder
            try {
                InputStream is;
                is = appContext.getAssets().open("Symbol.json.enc");
                String bufferString;

                bufferString = FileEncryptor.decrypt(is);

                // Convert string to JSONArray
                symbols = new JSONArray(bufferString);

                return true;
            } catch (Throwable e) {
            }
        }

        return false;
    }

    public static String getNameImage(int cardId) {
        JSONObject jo;
        try {
            jo = symbols.getJSONObject(cardId);

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
            jo = symbols.getJSONObject(cardId);

            return jo.getString(TITLE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getInfo(int cardId) {
        JSONObject jo;
        try {
            jo = symbols.getJSONObject(cardId);

            return jo.getString(INFO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
