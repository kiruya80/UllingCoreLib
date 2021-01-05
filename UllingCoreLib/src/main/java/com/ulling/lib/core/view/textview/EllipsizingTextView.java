/*
 * Copyright (C) 2011 Micah Hainline
 * Copyright (C) 2012 Triposo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ulling.lib.core.view.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;

import java.util.regex.Pattern;

/**
 * 말줄임 상태체크를 위한 커스텀 텍스트뷰
 */
public class EllipsizingTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String ELLIPSIS = "…";
    private static final Pattern DEFAULT_END_PUNCTUATION = Pattern.compile("[\\.,…;\\:\\s]*$", Pattern.DOTALL);

    public interface EllipsizeListener {
        void ellipsizeStateChanged(View v, boolean ellipsized);
    }

    private EllipsizeListener ellipsizeListeners;
    private boolean isEllipsized = false;
    private boolean isStale;
    private boolean programmaticChange;
    private String fullText;
    private int maxLines;
    private float lineSpacingMultiplier = 1.0f;
    private float lineAdditionalVerticalPadding = 0.0f;
    /**
     * The end punctuation which will be removed when appending #ELLIPSIS.
     */
    private Pattern endPunctuationPattern;

    public EllipsizingTextView(Context context) {
        this(context, null);
    }

    public EllipsizingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EllipsizingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setEllipsize(null);
        TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.maxLines});
        setMaxLines(a.getInt(0, Integer.MAX_VALUE));
        setEndPunctuationPattern(DEFAULT_END_PUNCTUATION);
    }

    public void setEndPunctuationPattern(Pattern pattern) {
        this.endPunctuationPattern = pattern;
    }

    public void addEllipsizeListener(EllipsizeListener listener) {
        ellipsizeListeners = listener;
    }

    public boolean isEllipsized() {
        return isEllipsized;
    }

    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        this.maxLines = maxLines;
        isStale = true;
    }

    public int getMaxLines() {
        return maxLines;
    }

    public boolean ellipsizingLastFullyVisibleLine() {
        return maxLines == Integer.MAX_VALUE;
    }

    @Override
    public void setLineSpacing(float add, float mult) {
        this.lineAdditionalVerticalPadding = add;
        this.lineSpacingMultiplier = mult;
        super.setLineSpacing(add, mult);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before,
                                 int after) {
        super.onTextChanged(text, start, before, after);
        if (!programmaticChange) {
            fullText = text.toString();
            isStale = true;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (ellipsizingLastFullyVisibleLine()) {
            isStale = true;
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        if (ellipsizingLastFullyVisibleLine()) {
            isStale = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isStale) {
            resetText();
        }
        super.onDraw(canvas);
    }

    private void resetText() {
        String workingText = fullText;
        boolean ellipsized = false;
        isEllipsized = false;
        Layout layout = createWorkingLayout(workingText);
        int linesCount = getLinesCount();
        if (layout.getLineCount() > linesCount) {
            // We have more lines of text than we are allowed to display.
            workingText = fullText.substring(0, layout.getLineEnd(linesCount - 1)).trim();
            while (createWorkingLayout(workingText + ELLIPSIS).getLineCount() > linesCount) {
                int lastSpace = workingText.lastIndexOf(' ');
                if (lastSpace == -1) {
                    break;
                }
                workingText = workingText.substring(0, lastSpace);
            }
            // We should do this in the loop above, but it's cheaper this way.
            workingText = endPunctuationPattern.matcher(workingText).replaceFirst("");
            workingText = workingText + ELLIPSIS;
            ellipsized = true;
        }
        if (!workingText.equals(getText())) {
            // 말줄임이 된 상태
            programmaticChange = true;
            try {
                setText(workingText);
            } finally {
                programmaticChange = false;
            }
        }
        isStale = false;
        //  ellipsized = true , isEllipsized = true  >>>> 말줄임이 되지만 리스너로 넘겨주지 않았음
        // ellipsized = true , isEllipsized = false  정상적으로 리스너 전달
        if (ellipsized != isEllipsized) {
            isEllipsized = ellipsized;
            if (ellipsizeListeners != null)
                ellipsizeListeners.ellipsizeStateChanged(this, ellipsized);
        }
    }

    /**
     * Get how many lines of text we are allowed to display.
     */
    private int getLinesCount() {
        if (ellipsizingLastFullyVisibleLine()) {
            int fullyVisibleLinesCount = getFullyVisibleLinesCount();
            if (fullyVisibleLinesCount == -1) {
                return 1;
            } else {
                return fullyVisibleLinesCount;
            }
        } else {
            return maxLines;
        }
    }

    /**
     * Get how many lines of text we can display so their full height is visible.
     */
    private int getFullyVisibleLinesCount() {
        Layout layout = createWorkingLayout("");
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int lineHeight = layout.getLineBottom(0);
        return height / lineHeight;
    }

    private Layout createWorkingLayout(String workingText) {
        return new StaticLayout(workingText, getPaint(),
                getWidth() - getPaddingLeft() - getPaddingRight(),
                Alignment.ALIGN_NORMAL, lineSpacingMultiplier,
                lineAdditionalVerticalPadding, false /* includepad */);
    }

    @Override
    public void setEllipsize(TruncateAt where) {
        // Ellipsize settings are not respected
    }
}
