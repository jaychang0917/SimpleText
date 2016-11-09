package com.jaychang.st;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

public class LinkTouchMovementMethod extends LinkMovementMethod {
  
  private TouchableSpan touchableSpan;

  @Override
  public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      touchableSpan = getTouchableSpan(textView, spannable, event);
      if (touchableSpan != null) {
        touchableSpan.setPressed(true);
        Selection.setSelection(spannable, spannable.getSpanStart(touchableSpan),
          spannable.getSpanEnd(touchableSpan));
      }
    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
      TouchableSpan touchedSpan = getTouchableSpan(textView, spannable, event);
      if (touchableSpan != null && touchedSpan != touchableSpan) {
        touchableSpan.setPressed(false);
        touchableSpan = null;
        Selection.removeSelection(spannable);
      }
    } else {
      if (touchableSpan != null) {
        touchableSpan.setPressed(false);
        super.onTouchEvent(textView, spannable, event);
      }
      touchableSpan = null;
      Selection.removeSelection(spannable);
    }
    return true;
  }

  private TouchableSpan getTouchableSpan(TextView textView, Spannable spannable, MotionEvent event) {

    int x = (int) event.getX();
    int y = (int) event.getY();

    x -= textView.getTotalPaddingLeft();
    y -= textView.getTotalPaddingTop();

    x += textView.getScrollX();
    y += textView.getScrollY();

    Layout layout = textView.getLayout();
    int line = layout.getLineForVertical(y);
    int off = layout.getOffsetForHorizontal(line, x);

    TouchableSpan[] link = spannable.getSpans(off, off, TouchableSpan.class);
    TouchableSpan touchedSpan = null;
    if (link.length > 0) {
      touchedSpan = link[0];
    }
    return touchedSpan;
  }

}