package com.jaychang.st;

import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class LinkTouchMovementMethod extends LinkMovementMethod {

  private ClickableSpan touchableSpan;
  private RoundedBackgroundSpan backgroundSpan;

  public LinkTouchMovementMethod(int pressedTextColor,
                                  int pressedBackgroundColor,
                                  int backgroundRadius) {
    backgroundSpan = new RoundedBackgroundSpan(pressedTextColor, pressedBackgroundColor, backgroundRadius);
  }

  @Override
  public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
    textView.setHighlightColor(Color.TRANSPARENT);

    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      touchableSpan = getPressedSpan(textView, spannable, event);
      if (touchableSpan != null) {
        Selection.setSelection(spannable, spannable.getSpanStart(touchableSpan), spannable.getSpanEnd(touchableSpan));
        setBackground(spannable);
      }
    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
      ClickableSpan touchedSpan = getPressedSpan(textView, spannable, event);
      if (touchableSpan != null && touchedSpan != touchableSpan) {
        removeBackground(spannable);
        touchableSpan = null;
        Selection.removeSelection(spannable);
      }
    } else {
      if (touchableSpan != null) {
        super.onTouchEvent(textView, spannable, event);
      }
      removeBackground(spannable);
      touchableSpan = null;
      Selection.removeSelection(spannable);
    }
    return true;
  }

  private ClickableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {

    int x = (int) event.getX();
    int y = (int) event.getY();

    x -= textView.getTotalPaddingLeft();
    y -= textView.getTotalPaddingTop();

    x += textView.getScrollX();
    y += textView.getScrollY();

    Layout layout = textView.getLayout();
    int line = layout.getLineForVertical(y);
    int off = layout.getOffsetForHorizontal(line, x);

    ClickableSpan[] link = spannable.getSpans(off, off, ClickableSpan.class);
    ClickableSpan touchedSpan = null;
    if (link.length > 0) {
      touchedSpan = link[0];
    }
    return touchedSpan;
  }

  public void setBackground(Spannable spannable) {
    spannable.setSpan(backgroundSpan, spannable.getSpanStart(touchableSpan), spannable.getSpanEnd(touchableSpan), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
  }

  public void removeBackground(Spannable spannable) {
    spannable.removeSpan(backgroundSpan);
  }

}