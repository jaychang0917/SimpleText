package com.jaychang.st;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

public abstract class TouchableSpan extends ClickableSpan {

  private boolean isPressed;
  private int pressedBackgroundColor;
  private int normalTextColor;
  private int pressedTextColor;

  public TouchableSpan(int normalTextColor, int pressedTextColor, int pressedBackgroundColor) {
   this.normalTextColor = normalTextColor;
   this.pressedTextColor = pressedTextColor;
   this.pressedBackgroundColor = pressedBackgroundColor;
  }

  public void setPressed(boolean isSelected) {
    isPressed = isSelected;
  }

  @Override
  public void updateDrawState(TextPaint ds) {
    super.updateDrawState(ds);
    ds.setColor(isPressed ? pressedTextColor : normalTextColor);
    ds.bgColor = isPressed ? pressedBackgroundColor : 0xffeeeeee;
    ds.setUnderlineText(false);
  }
}