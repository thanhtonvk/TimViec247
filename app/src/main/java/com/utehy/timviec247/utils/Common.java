package com.utehy.timviec247.utils;

import android.location.Location;

import com.google.firebase.auth.FirebaseUser;
import com.utehy.timviec247.models.Account;
import com.utehy.timviec247.models.Company;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.models.ThongTin;
import com.utehy.timviec247.models.UngTuyen;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Common {
    public static FirebaseUser user;
    public static Account account;
    public static Job job;
    public static Company company;
    public static ThongTin thongTin;
    public static UngTuyen ungTuyen;
    public static String timKiem;
    public static Location location;

    public static String xoaDauTiengViet(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase().trim();
    }

    private static final double r2d = 180.0D / 3.141592653589793D;
    private static final double d2r = 3.141592653589793D / 180.0D;
    private static final double d2km = 111189.57696D * r2d;

    public static double doKhoangCach(double lt1, double ln1, double lt2, double ln2) {
        double x = lt1 * d2r;
        double y = lt2 * d2r;
        return Math.acos(Math.sin(x) * Math.sin(y) + Math.cos(x) * Math.cos(y) * Math.cos(d2r * (ln1 - ln2))) * d2km;
    }
}
