package com.utehy.timviec247.utils;

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
    public static  String timKiem;

    public static String xoaDauTiengViet(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase().trim();
    }
}
