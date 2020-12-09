package com.wakeup.tarot.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.wakeup.tarot.R;
import com.wakeup.tarot.util.RecyclingBitmapDrawable;

import java.util.ArrayList;
import java.util.Random;

public class ConfigData {
    public static final String FEED_BACK_LINK = "https://www.facebook.com/greendream.ait.vn";

    // Media player to control and represent sound effect in app
    public static MediaPlayer mp;
    private static Context appContext;

    // Animation
    public static Animation animation_rotation_180;
    public static Animation animation_rotation_90;
    public static Animation animation_spread_card;
    public static Animation animation_button_press;
    public static Animation animation_select_card;
    public static Animation animation_zoom_in;
    public static Animation animation_rotate_zoom_in;

    // Save data
    private static SharedPreferences preferences;
    public static float FONT_SIZE;
    public static boolean IS_SOUND_ON;
    public static boolean IS_REVERSE_CARD;
    public static int BROWSER_MODE = 0;
    public static RecyclingBitmapDrawable rbdBackground = null;

    // Data change don't need save
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static Typeface UVNCatBien_R = null;
    public static Typeface UVNCatBien_Sub = null;


    // Has 4 levels (0 --> 3) default with level 1 with 5 columns
    public static int ZOOM_LEVEL;

    // Random data for choose one card
    public static int randOneCardId;
    public static int randOneCardDimension;

    // Random data for choose multiple cards
    public static int[] randomCardIdArray; // current random List card draw
    public static int[] randomCardDimensionsArray; // current random dimension
    // List of card

    public static boolean IS_USER_DESTROY_BY_BACK_BUTTON = false; // Determine
    // whether
    // user
    // destroy
    // by rotate

    public static void loadSettingData(Activity a) {
        // get application context
        appContext = a.getApplicationContext();

        // load animation
        animation_rotation_180 = AnimationUtils.loadAnimation(appContext,
                R.anim.rotate_180);
        animation_rotation_90 = AnimationUtils.loadAnimation(appContext,
                R.anim.rotate_90);
        animation_spread_card = AnimationUtils.loadAnimation(appContext,
                R.anim.spread_card);
        animation_button_press = AnimationUtils.loadAnimation(appContext,
                R.anim.button_press);
        animation_select_card = AnimationUtils.loadAnimation(appContext,
                R.anim.select_card);
        animation_zoom_in = AnimationUtils.loadAnimation(appContext,
                R.anim.zoom_in);
        animation_rotate_zoom_in = AnimationUtils.loadAnimation(appContext,
                R.anim.rotate_zoom_in);

        // Fetch screen height and width, to use as our max size when loading
        // images as this
        // activity runs full screen
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int h = displayMetrics.heightPixels;
        final int w = displayMetrics.widthPixels;
        SCREEN_WIDTH = (w < h) ? w : h;
        SCREEN_HEIGHT = (h > w) ? h : w;

        if (UVNCatBien_R == null) {
            UVNCatBien_R = Typeface.createFromAsset(a.getApplicationContext()
                    .getAssets(), "red_m.ttf");
        }

        if (UVNCatBien_Sub == null) {
            UVNCatBien_Sub = Typeface.createFromAsset(a.getApplicationContext()
                    .getAssets(), "red_m.ttf");
        }

        // Get data UI saved
        preferences = PreferenceManager.getDefaultSharedPreferences(a);
        FONT_SIZE = preferences.getFloat("FONT_SIZE",
                getDimensionWithUnitSP(R.dimen.commom_font_size));
        IS_REVERSE_CARD = preferences.getBoolean("IS_REVERSE_CARD", true);
        IS_SOUND_ON = preferences.getBoolean("IS_SOUND_ON", true);
        ZOOM_LEVEL = preferences.getInt("ZOOM_LEVEL", 1);

        // Load Background
        if (rbdBackground == null) {
            rbdBackground = new RecyclingBitmapDrawable(appContext.getResources(), R.drawable.mainback, ConfigData.SCREEN_WIDTH, ConfigData.SCREEN_HEIGHT);
        }

        // Load JSON
        CardsDetailJasonHelper.loadJSon(appContext);
        NumberJasonHelper.loadJSon(appContext);
        SpreadCardJasonHelper.loadJSon(appContext);
        StarJasonHelper.loadJSon(appContext);
        SuitJasonHelper.loadJSon(appContext);
        SymbolJasonHelper.loadJSon(appContext);
    }

    public static void saveSettingData() {
        // Retrieve an editor to modify the shared preferences.
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat("FONT_SIZE", FONT_SIZE);
        editor.putBoolean("IS_REVERSE_CARD", IS_REVERSE_CARD);
        editor.putBoolean("IS_SOUND_ON", IS_SOUND_ON);
        editor.putInt("ZOOM_LEVEL", ZOOM_LEVEL);

        // Commit changes.
        editor.commit();
    }

    public static void playSound(int soundId) {
        if (IS_SOUND_ON) {
            mp = MediaPlayer.create(appContext, soundId);
            mp.setLooping(false);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }
            });
        }
    }

    public static void stopSound() {
        try {
            if (mp != null) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
        } catch (Exception e) {
        }
    }

    // Check Inbox to search active message from author.
    public static boolean checkIsActivated(Activity a) {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = a.getContentResolver().query(uri, null, null, null, null);
        a.startManagingCursor(c);

        // Read the sms data and store it in the list
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                String Body = c.getString(c.getColumnIndexOrThrow("body"))
                        .toString();
                String Number = c.getString(c.getColumnIndexOrThrow("address"))
                        .toString();

                if ("WELCOME TO TAROT VIET".equals(Body)
                        && "01696016830".equals(Number)) {
                    c.close();
                    return true;
                }

                c.moveToNext();
            }
        }

        c.close();

        return false;
    }

    public static void resetDefault() {
        FONT_SIZE = getDimensionWithUnitSP(R.dimen.commom_font_size);
        IS_SOUND_ON = true;
        IS_REVERSE_CARD = true;
    }

    private static float getDimensionWithUnitSP(int id) {
        float scaledDensity = appContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (appContext.getResources().getDimension(id) / scaledDensity);
    }

    public static void randomOneCard() {
        Random rand = new Random();
        randOneCardId = rand.nextInt(78); // card id [0,77]

        ConfigData.randOneCardDimension = 0;
        if (ConfigData.IS_REVERSE_CARD) {
            ConfigData.randOneCardDimension = rand.nextInt(2);
        }
    }

    public static void randomMultipleCards(int theNummberOfCard) {
        randomCardIdArray = new int[theNummberOfCard];
        randomCardDimensionsArray = new int[theNummberOfCard];
        ArrayList<Integer> randArrayList = new ArrayList<Integer>();
        for (int i = 0; i < 78; i++)
            randArrayList.add(i);
        Random rand = new Random();
        for (int j = 0; j < theNummberOfCard; j++) {
            // random for card Id
            int k = rand.nextInt(randArrayList.size());
            randomCardIdArray[j] = randArrayList.get(k);
            randArrayList.remove(k);

            // random for dimension of card
            randomCardDimensionsArray[j] = rand.nextInt(2); // [0,1]: 1 is
            // enable reverse
            // card and 0 is
            // otherwise

            if (ConfigData.IS_REVERSE_CARD == false) {
                randomCardDimensionsArray[j] = 0;
            }
        }

    }

    /**
     * Reload screen size and background
     */
    public static void reloadScreen(Activity a) {
        // Fetch screen height and width, to use as our max size when loading
        // images as this
        // activity runs full screen
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int h = displayMetrics.heightPixels;
        final int w = displayMetrics.widthPixels;
        SCREEN_WIDTH = (w < h) ? w : h;
        SCREEN_HEIGHT = (h > w) ? h : w;

        // Load Background
        rbdBackground = new RecyclingBitmapDrawable(appContext.getResources(),
                R.drawable.mainback, ConfigData.SCREEN_WIDTH,
                ConfigData.SCREEN_HEIGHT);
    }
}
