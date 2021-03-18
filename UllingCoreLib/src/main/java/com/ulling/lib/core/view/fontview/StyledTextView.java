package com.ulling.lib.core.view.fontview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.ulling.lib.core.R;

/**
 * @author : KILHO
 * @ProjectName : HummingPlayer_1.0
 * @FileName : StyledTextView.java
 * @FilePath : com.ullim.view.fontview
 * @Date : 2015. 5. 12.
 * @프로그램
 * @변경이력
 */
public class StyledTextView extends AppCompatTextView {
    //	public String TAG = getClass().getSimpleName();

    public StyledTextView(Context context) {
        super(context);
    }

    public StyledTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getTypefaceFont(context, attrs);
    }

    public StyledTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getTypefaceFont(context, attrs);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }

    public void getTypefaceFont(Context context, AttributeSet attrs) {
        Typeface typeFaceFont_ = null;
        TypedArray arr = context.getTheme()
            .obtainStyledAttributes(attrs, R.styleable.StyledTextView, 0, 0);
        try {
            String fontName = arr.getString(R.styleable.StyledTextView_typeface);

            typeFaceFont_ = StyledFontHelper.getInstance(context).getTyprFaceFont(fontName);
            try {
                if (typeFaceFont_ != null) {
                    setTypeface(typeFaceFont_);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        } finally {
            arr.recycle();
        }
    }

    public void setTypefaceFont(Context context, String fontName) {
        Typeface typeFaceFont_ = null;
        typeFaceFont_ = StyledFontHelper.getInstance(context).getTyprFaceFont(fontName);

        if (typeFaceFont_ != null) {
            setTypeface(typeFaceFont_);
        }
    }
}