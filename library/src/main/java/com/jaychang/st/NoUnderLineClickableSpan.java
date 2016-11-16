package com.jaychang.st;

import android.annotation.SuppressLint;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

public class NoUnderLineClickableSpan extends ClickableSpan {

  private CharSequence text;
  private Object tag;
  private Range range;
  private OnTextClickListener onTextClickListener;

  public NoUnderLineClickableSpan(CharSequence text, Object tag, Range range, OnTextClickListener onTextClickListener) {
    this.text = text;
    this.tag = tag;
    this.range = range;
    this.onTextClickListener = onTextClickListener;
  }

  @Override
  public void onClick(View view) {
    onTextClickListener.onClicked(text, range, tag);
  }

  @Override
  public void updateDrawState(TextPaint ds) {
  }
}
