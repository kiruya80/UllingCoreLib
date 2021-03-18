package com.ulling.lib.core.view.fontview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;
import com.ulling.lib.core.R;

public class StyledEditTextView extends AppCompatEditText {

    public StyledEditTextView(Context context) {
        super(context);
    }

    public StyledEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getTypefaceFont(context, attrs);
    }

    public StyledEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getTypefaceFont(context, attrs);
    }

    //	private void setBackLine() {
    //		setBackgroundResource(R.drawable.edittext_field_default);
    //	}

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
            //			typeFaceFont_ = CustormFontHelper.getInstances(context, fontName).getTypeFace();
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
        //		typeFaceFont_ = CustormFontHelper.getInstances(context, fontName).getTypeFace();

        if (typeFaceFont_ != null) {
            setTypeface(typeFaceFont_);
        }
    }
}