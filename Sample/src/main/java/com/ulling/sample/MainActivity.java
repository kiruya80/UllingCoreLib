/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.ulling.lib.util.QcBackPressClose;
import com.ulling.lib.util.QcLog;
import com.ulling.lib.util.QcToast;
import com.ulling.lib.view.LetterSpacingTextView;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
  private LetterSpacingTextView textView1;
  private LetterSpacingTextView textView2;
  private LetterSpacingTextView textView3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    QcLog.DEBUG_MODE = true;
    QcLog.e("TSET !!!!1");
    QcLog.d("TSET !!!!1");
    QcLog.i("TSET !!!!1");
    QcLog.v("TSET !!!!1");
    QcLog.w("TSET !!!!1");
//        QcLog.nativeHeap();
    QcToast.with(getApplication(), "Toast 111111");

//    textView1 = (LetterSpacingTextView) findViewById(R.id.textView1);
//    textView2 = (LetterSpacingTextView) findViewById(R.id.textView2);
//    textView3 = (LetterSpacingTextView) findViewById(R.id.textView3);

//    textView1.setSpacing(LetterSpacingTextView.Spacing.NORMALBIG);
//    textView2.setLetterSpacing(LetterSpacingTextView.LetterSpacing.BIG);
//    textView3.setLetterSpacing(LetterSpacingTextView.LetterSpacing.BIGGEST);


    LetterSpacingTextView textView = new LetterSpacingTextView(getApplicationContext());
    textView.setSpacing(LetterSpacingTextView.Spacing.BIGGEST); //Or any float. To reset to normal, use 0 or LetterSpacingTextView.Spacing.NORMAL
    textView.setText("다시 한번 말하지만 lineSpacingMultiplier 이런 것을 기억할 필요가 전혀 없다.");
    textView.setTextColor(getResources().getColor(R.color.color_black));
//Add the textView in a layout, for instance:
    ((LinearLayout) findViewById(R.id.myLinearLayout)).addView(textView);



    LetterSpacingTextView textView2 = new LetterSpacingTextView(getApplicationContext());
//    textView2.setSpacing(20); //Or any float. To reset to normal, use 0 or LetterSpacingTextView.Spacing.NORMAL
    textView2.setSpacing(10); //Or any float. To reset to normal, use 0 or LetterSpacingTextView.Spacing.NORMAL
    textView2.setText("다시 한번 말하지만 lineSpacingMultiplier 이런 것을 기억할 필요가 전혀 없다.");
    textView2.setTextColor(getResources().getColor(R.color.color_black));
//Add the textView in a layout, for instance:
    ((LinearLayout) findViewById(R.id.myLinearLayout)).addView(textView2);


    LetterSpacingTextView textView3 = new LetterSpacingTextView(getApplicationContext());
    textView3.setSpacing(30); //Or any float. To reset to normal, use 0 or LetterSpacingTextView.Spacing.NORMAL
    textView3.setText("다시 한번 말하지만 lineSpacingMultiplier 이런 것을 기억할 필요가 전혀 없다.");
    textView3.setTextColor(getResources().getColor(R.color.color_black));
//Add the textView in a layout, for instance:
    ((LinearLayout) findViewById(R.id.myLinearLayout)).addView(textView3);



//    http://stackoverflow.com/questions/1640659/how-to-adjust-text-kerning-in-android-textview/16429758#16429758
//    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//      paint.setLetterSpacing(-0.04f);  // setLetterSpacing is only available from LOLLIPOP and on
//      canvas.drawText(text, xOffset, yOffset, paint);
//    } else {
//      float spacePercentage = 0.05f;
//      drawKernedText(canvas, text, xOffset, yOffset, paint, spacePercentage);
//    }

  }

  /**
   *
   * @param dfef
   */
  private void setfdfef(int dfef) {

  }

  /**
   *
   */
  @Override
  public void onBackPressed() {
    if (QcBackPressClose.with(getApplicationContext()).isBackPress()) {
      super.onBackPressed();
    }
  }
}
