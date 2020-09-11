package com.wakeup.tarot.data;

import android.content.Context;

import com.wakeup.tarot.util.FileEncryptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class CardsDetailJasonHelper {
    // JSON Node names for Cards Detail
    private static final String CARD_ID = "idCard";
    private static final String SUIT_ID = "idSuit";
    private static final String STAR_ID = "idStar";
    private static final String NUMBER_ID = "idNumber";
    private static final String SYMBOL_ID = "idSymbol";
    private static final String NAME_CARD = "nameCard";
    private static final String ENGLISH_NAME = "English";
    private static final String VIETNAMESE_NAME = "Vietnamese";
    private static final String KEYWORD_FORWARD = "KeywordForward";
    private static final String KEYWORD_REVERSE = "KeywordReverse";
    private static final String FORWARD = "Forward";
    private static final String REVERSE = "Reverse";

    // contacts JSONArray
    private static JSONArray cards_detail = null;

    /**
     * Load Cards detail from asset folder
     *
     * @return true if success and false if otherwise
     */
    public static boolean loadJSon(Context appContext) {
        if (cards_detail == null) {
            // Read file from assets folder
            try {
                InputStream is;
                is = appContext.getAssets().open("CardsDetail.json.enc");
                String bufferString;

                bufferString = FileEncryptor.decrypt(is);

                // Convert string to JSONArray
                cards_detail = new JSONArray(bufferString);

                return true;
            } catch (Throwable e) {
            }

        }
        return false;
    }

    public static String getEnglishCardName(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);

            return jo.getString(ENGLISH_NAME);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getVietNameseCardName(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);

            return jo.getString(VIETNAMESE_NAME);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getKeywordForward(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);

            return jo.getString(KEYWORD_FORWARD);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getKeywordReverse(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);

            return jo.getString(KEYWORD_REVERSE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getForward(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);

            return jo.getString(FORWARD);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String getReverse(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);

            return jo.getString(REVERSE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get suit id of card
     *
     * @param cardId
     * @return suit Id if not found return -1
     */
    public static String[] getSuitIds(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);
            String[] arrSuit = new String[1];
            arrSuit[0] = jo.getString(SUIT_ID);
            return arrSuit;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get suit id of card
     *
     * @param cardId
     * @return suit Id if not found return null
     */
    public static String[] getStarIds(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);
            String[] arrStar = new String[1];
            arrStar[0] = jo.getString(STAR_ID);
            if ("NULL".equals(arrStar[0])) {
                return null;
            } else {
                return arrStar;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get list number of card
     *
     * @param cardId
     * @return array list of number id of card | null if not found
     */
    public static String[] getNumberIds(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);

            // get number id field
            JSONArray arrNumber = jo.getJSONArray(NUMBER_ID);

            // get value of number id and return
            String[] numberIds = new String[arrNumber.length()];
            for (int i = 0; i < arrNumber.length(); i++) {
                numberIds[i] = arrNumber.getString(i);
            }
            return numberIds;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get list symbol card
     *
     * @param cardId
     * @return array list of symbol id of card | null if not found
     */
    public static String[] getSymbolIds(int cardId) {
        JSONObject jo;
        try {
            jo = cards_detail.getJSONObject(cardId);

            // get symbol id field
            JSONArray arrSymbol = jo.getJSONArray(SYMBOL_ID);

            // get value of number id and return
            String[] symbolIds = new String[arrSymbol.length()];
            for (int i = 0; i < arrSymbol.length(); i++) {
                symbolIds[i] = arrSymbol.getString(i);
            }
            return symbolIds;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
