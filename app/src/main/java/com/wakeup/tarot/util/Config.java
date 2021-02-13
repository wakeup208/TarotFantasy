package com.wakeup.tarot.util;


import com.wakeup.tarot.R;

import java.util.ArrayList;

public class Config {

    static String[] colorList = {"#008B8B", "#00FF00", "#48D1CC", "#556B2F", "#696969", "#6B8E23", "#8FBC8F", "#AFEEEE", "#B8860B", "#BDB76B", "#D8BFD8", "#DEB887", "#FFFF00", "#FFF0F5", "#EE82EE", "#DC143C", "#C0C0C0", "#FFFFFF"};
    static int timmerDeley = 300;
    public static int[] imageId_blur = {R.drawable.p_saturnus, R.drawable.p_luna, R.drawable.p_pluto, R.drawable.p_mercurius, R.drawable.p_mars, R.drawable.p_uranus, R.drawable.pents14, R.drawable.s_dua_than, R.drawable.s_wands};
    public static String[] cunghoangdao = {"Bạch dương", "Bảo bình", "Bò cạp", "Cự giải", "Kim ngưu", "Ma kết", "Nhân mã", "Song ngư", "Song tử", "Sư tử", "Thiên bình","Xử nữ",};
    public static ArrayList<String> CunghoangdaoAndDathanhtay = new ArrayList<>();
    public static String[] cunghoangdaoAndDathanhtay;
    public static int[] img_cung_hoang_dao = {R.drawable.hoangdao_bachduong, R.drawable.hoangdao_baobinh, R.drawable.hoangdao_bocap, R.drawable.cancer, R.drawable.hoangdao_kimnguu, R.drawable.hoangdao_maket, R.drawable.hoangdao_nhanma, R.drawable.hoangdao_songngu, R.drawable.hoangdao_songsinh, R.drawable.hoangdao_sutu, R.drawable.hoangdao_thienbinh, R.drawable.hoangdao_xunu};

}
