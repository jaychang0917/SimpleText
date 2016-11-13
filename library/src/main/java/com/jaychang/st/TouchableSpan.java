package com.jaychang.st;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class TouchableSpan extends ClickableSpan {

  private boolean isPressed;
  private int pressedTextColor;
  private int pressedBackgroundColor;

  public TouchableSpan(int pressedTextColor, int pressedBackgroundColor) {
    this.pressedTextColor = pressedTextColor;
    this.pressedBackgroundColor = pressedBackgroundColor;
  }

  public void setPressed(boolean pressed){
    this.isPressed = pressed;
  }

  public boolean isPressed(){
    return this.isPressed;
  }

  @Override
  public void onClick(View widget) {
    setPressed(!isPressed());
    widget.invalidate();
  }

  @Override
  public void updateDrawState(TextPaint ds) {
    if(!isPressed()){
      ds.setColor(pressedTextColor);
      ds.bgColor = pressedBackgroundColor;
    }
  }
}