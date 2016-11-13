package com.jaychang.st;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.URLSpan;

@SuppressLint("ParcelCreator")
public class NoUnderLineUrlSpan extends URLSpan {

  public NoUnderLineUrlSpan(String url) {
    super(url);
  }

  @Override
  public void updateDrawState(TextPaint ds) {
  }
}
