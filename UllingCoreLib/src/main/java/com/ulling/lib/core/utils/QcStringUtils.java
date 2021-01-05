package com.ulling.lib.core.utils;

import android.graphics.Paint;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QcStringUtils {
    public static int NUMBER_TEN = 1;
    public static int NUMBER_HUNDRED = 2;
    public static int NUMBER_THOUSAND = 3;

    private static DecimalFormat df = new DecimalFormat("###,###.##");

    public static Date GetDate(int year, int month, int date, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, date, hour, minute, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static long GetUnixTime(long time) {
        return time / 1000;
    }

    /**
     * number자리수 반올림
     * 1 : 십단위
     * 2 : 백단위
     *
     * @param value
     * @param number
     * @return
     */
    public static double getRound(double value, int number) {
        if (number <= 0)
            return value;

        int intValue = (int) Math.ceil(value);
        int length = (int) (Math.log10(intValue) + 1);
        if (length <= number)
            return value;

        double roundNum = 1;
        for (int i = 0; i < number; i++) {
            roundNum = roundNum * 10d;
        }
        return Math.round(value / roundNum) * roundNum;
    }


    public static BigDecimal formatBigDecimal(double value) {
        try {
            return new BigDecimal(df.format(value));
        } catch (Exception e) {
            return new BigDecimal(0);
        }
    }

    public static String toNumFormat(int num) {
        return df.format(num);
    }

    public static String toNumFormat(long num) {
        return df.format(num);
    }

    public static String toNumFormat(double num) {
        return df.format(num);
    }

    /**
     * 콤마
     * @param value
     * @return
     */
    public static String setConvertComma(String value) {
        int v = 0;
        if (Pattern.matches("^[0-9]*$", value)) {
            v = Integer.parseInt(value);
        }
        return setConvertComma(v);
    }
    public static String setConvertComma(int value) {
        return NumberFormat.getNumberInstance(Locale.KOREA).format(value);
    }

    public static boolean isIntegerFromStr(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * 가격 콤마 추가
     */
    public static String setConvertPrice(String value, String unit) {
//        int v = 0;
//        if (Pattern.matches("^[0-9]*$", value)) {
//            v = Integer.parseInt(value);
//        }
//        return NumberFormat.getNumberInstance(Locale.KOREA).format(v) + unit;
        return setConvertComma(value) + unit;
    }

    /**
     * 세자리 컴마 + 소수점 두자리
     *
     * @param num
     * @param decimal 소수점 표시
     * @return
     */
    public static String setConvertComma(float num, boolean decimal) {
        if (decimal) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(num);
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(num);
        }
    }

    public static String setConvertComma(double num, boolean decimal) {
        if (decimal) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(num);
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(num);
        }
    }

    public static String setConvertComma(int num, boolean decimal) {
        if (decimal) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(num);
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(num);
        }
    }

    public static String setConvertComma(String num, boolean decimal) {
        if (decimal) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(num);
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(num);
        }
    }




    public static BigDecimal GetDoubleAdd(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.add(su2);   // 더하기
    }

    public static BigDecimal GetDoubleSubtract(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.subtract(su2);   // 빼기
    }

    public static BigDecimal GetDoubleMultiply(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.multiply(su2);   // 곱하기
    }

    public static BigDecimal GetDoubleDivide(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.divide(su2, BigDecimal.ROUND_UP);  // 나누기 - 소수점 4번째 자리에서 반올림.
    }

    public static BigDecimal GetDoubleDivide(double value1, double value2, int number) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.divide(su2, number, BigDecimal.ROUND_UP);  // 나누기 - 소수점 n번째 자리에서 반올림.
    }

    public static BigDecimal GetDoubleRemainder(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.remainder(su2);  // 나머지 % 연산
    }

    /**
     * 문자열내 특정 문자색 지정
     *
     * @param src
     * @param target
     * @param baseColor
     * @param targetColor
     * @return
     */
//    public static String getHighlightedText(String src, String target, String baseColor, String targetColor) {
//        String html = "";
//
//        while (src != null && src.length() > 0 && src.indexOf(target) > -1) {
//            int targetIdx = src.indexOf(target);
//
//            if (targetIdx == 0) {
//                html += "<font color=" + targetColor + ">";
//                html += target;
//                html += "</font>";
//            } else {
//                html += "<font color=" + baseColor + ">";
//                html += src.substring(0, targetIdx);
//                html += "</font>";
//
//                html += "<font color=" + targetColor + ">";
//                html += target;
//                html += "</font>";
//            }
//
//            src = src.substring(targetIdx + target.length(), src.length());
//        }
//
//        if (src != null && src.length() > 0) {
//            html += "<font color=" + baseColor + ">";
//            html += src;
//            html += "</font>";
//        }
//
//        return html;
//    }


    /**
     * TextView 취소선 적용
     *
     * @param tv
     */
    public static void setCancelLine(TextView tv) {
        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    /**
     * 특수문자 제외
     */
    public InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.isLetterOrDigit(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

    //특수문자 제거 하기
    public static String StringReplace(String str){
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        str =str.replaceAll(match, " ");
        return str;
    }

    public static String stringReplace(String str) {
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        return str.replaceAll(match, " ");
    }

    //이메일 유효성
    public static boolean isEmailPattern(String email){
        Pattern pattern= Pattern.compile("\\w+[@]\\w+\\.\\w+");
        Matcher match=pattern.matcher(email);
        return match.find();
    }

    //연속 스페이스 제거
    public static String continueSpaceRemove(String str){
        String match2 = "\\s{2,}";
        str = str.replaceAll(match2, " ");
        return str;
    }


//    public static int getColor(Context context, int id) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return context.getResources().getColor(id);
//        } else {
//            return context.getColor(id);
//        }
//    }
//
//    public static Drawable getDrawable(Context context, int id) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            return context.getResources().getDrawable(id);
//        } else {
//            return context.getDrawable(id);
//        }
//    }

}
