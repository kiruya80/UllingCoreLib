package com.ulling.lib.core.utils;

import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * 텍스트뷰 유틸들
 */
public class QcTextViewUtils {

    /**
     * 특정 글자만 색상 변경하기
     *
     * @param textView
     * @param allText
     * @param coloredText
     * @param color
     * @return
     */
    public static TextView setColoredTextView(@NonNull TextView textView, @NonNull String allText,
                                              @NonNull String coloredText, @NonNull int color) {
        int start = allText.indexOf(coloredText);
        int end = start + coloredText.length();
        if (start >= 0 && end > 0) {
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(allText);
            spannableStringBuilder.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableStringBuilder);
        } else {
            textView.setText(allText);
        }
        return textView;
    }

    /**
     * 줄 바꿈 만들기
     *
     * @param textPaint
     * @param strText
     * @param breakWidth
     * @return
     */
    public static String makeBreakText(Paint textPaint, String strText, int breakWidth) {
        StringBuilder sb = new StringBuilder();
        int endValue = 0;
        String strText_ = strText.toString().replaceAll(" ", "\u00A0");
        String strText2 = strText_.toString().replaceAll("\n", "\u00A0");

        do {
            endValue = textPaint.breakText(strText2, true, breakWidth, null);
            if (endValue > 0) {
                sb.append(strText2.substring(0, endValue)).append("\n");
                strText2 = strText2.substring(endValue);
            }
        } while (endValue > 0);
        return sb.toString().substring(0, sb.length() - 1);  // 마지막 "\n"를 제거
    }

//    public static void addDotImageText(@NonNull final Context context, @NonNull TextView textView, String msg) {
//        if (!msg.contains(context.getResources().getString(R.string.str_dot))) {
//            textView.setText(msg);
//            return;
//        }
//        String newText = msg.toString().replace(context.getResources().getString(R.string.str_dot), context.getResources().getString(R.string.str_dot_change));
//        textView.setText(newText);
//    }
}
