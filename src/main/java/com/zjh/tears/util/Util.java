package com.zjh.tears.util;

import org.apache.tika.Tika;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class Util {
    private Util() {
    }

    private static Tika tika = new Tika();

    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    public static String getContentType(File f) {
        try {
            return tika.detect(f);
        } catch (Exception e) {
            return "text/plain";
        }
    }

    public static String getGMTString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    public static String getGMTString() {
        return Util.getGMTString(new Date());
    }

    public static String stackTraceToString(Throwable e) {
        return Stream.of(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n"));
    }

    public static void closeFileInputStream(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
