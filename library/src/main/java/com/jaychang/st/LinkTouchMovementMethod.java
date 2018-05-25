package com.jaychang.st;

import android.os.Handler;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

class LinkTouchMovementMethod extends LinkMovementMethod {

  private static final int LONG_CLICK_THRESHOLD = 1000;
  private boolean isLongClicked;
  private Handler longClickHandler;
  private CustomClickableSpan customClickSpan;
  private RoundedBackgroundSpan backgroundSpan;

  LinkTouchMovementMethod(int pressedTextColor,
                          int pressedBackgroundColor,
                          int backgroundRadius) {
    backgroundSpan = new RoundedBackgroundSpan(pressedTextColor, pressedBackgroundColor, backgroundRadius);
    longClickHandler = new Handler();
  }

  @Override
  public boolean onTouchEvent(final TextView textView, final Spannable spannable, MotionEvent event) {
    int action = event.getAction();

    if (action == MotionEvent.ACTION_CANCEL && longClickHandler != null) {
      longClickHandler.removeCallbacksAndMessages(null);
    }

    if (action == MotionEvent.ACTION_DOWN) {
      customClickSpan = getPressedSpan(textView, spannable, event);

      longClickHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
          if (customClickSpan != null) {
            customClickSpan.onLongClick(textView);
            isLongClicked = true;
            removeBackground(spannable);
          }
        }
      }, LONG_CLICK_THRESHOLD);

      if (customClickSpan != null) {
        setBackground(spannable);
      }
    } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL){
      isLongClicked = false;

      if (longClickHandler != null) {
        longClickHandler.removeCallbacksAndMessages(null);
      }

      if (!isLongClicked && customClickSpan != null && action == MotionEvent.ACTION_UP) {
        customClickSpan.onClick(textView);
      }

      if (customClickSpan != null) {
        removeBackground(spannable);
      }
    }

    return true;
  }

  private CustomClickableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {
    int x = (int) event.getX();
    int y = (int) event.getY();

    x -= textView.getTotalPaddingLeft();
    y -= textView.getTotalPaddingTop();

    x += textView.getScrollX();
    y += textView.getScrollY();

    Layout layout = textView.getLayout();
    int line = layout.getLineForVertical(y);
    int off = layout.getOffsetForHorizontal(line, x);

    CustomClickableSpan[] link = spannable.getSpans(off, off, CustomClickableSpan.class);
    CustomClickableSpan touchedSpan = null;
    if (link.length == 2) {
      touchedSpan = link[0];
      CustomClickableSpan tempSpan = link[1];
      touchedSpan.setOnTextClickListener(touchedSpan.getOnTextClickListener() != null ? touchedSpan.getOnTextClickListener() : tempSpan.getOnTextClickListener());
      touchedSpan.setOnTextLongClickListener(touchedSpan.getOnTextLongClickListener() != null ? touchedSpan.getOnTextLongClickListener() : tempSpan.getOnTextLongClickListener());
    } else if (link.length == 1){
      touchedSpan = link[0];
    }

    return touchedSpan;
  }

  private void setBackground(Spannable spannable) {
    spannable.setSpan(backgroundSpan, spannable.getSpanStart(customClickSpan), spannable.getSpanEnd(customClickSpan), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    Selection.setSelection(spannable, spannable.getSpanStart(customClickSpan), spannable.getSpanEnd(customClickSpan));
  }

  private void removeBackground(Spannable spannable) {
    spannable.removeSpan(backgroundSpan);
    Selection.removeSelection(spannable);
    customClickSpan = null;
  }

}