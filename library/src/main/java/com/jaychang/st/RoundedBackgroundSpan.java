package com.jaychang.st;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;

import static android.R.attr.padding;

public class RoundedBackgroundSpan extends ReplacementSpan {

  private int radius = 0;
  private int backgroundColor;
  private int textColor;

  public RoundedBackgroundSpan(@ColorInt int backgroundColor,
                                @ColorInt int textColor,
                                int radius) {
    super();
    this.backgroundColor = backgroundColor;
    this.textColor = textColor;
    this.radius = radius;
  }

  public RoundedBackgroundSpan(@ColorInt int backgroundColor,
                               int radius) {
    super();
    this.backgroundColor = backgroundColor;
    this.radius = radius;
  }

  @Override
  public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
    return (int) paint.measureText(text.subSequence(start, end).toString());
  }

  @Override
  public void draw(@NonNull Canvas canvas, CharSequence text,
                   int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
    float width = paint.measureText(text.subSequence(start, end).toString());
    int originalTextColor = paint.getColor();

    int extra1Dp = (int)(((TextPaint) paint).density * 1 + 0.5f);
    int newTop = (int) (bottom-paint.getFontSpacing()-paint.descent())+2*extra1Dp;
    int newBottom = bottom - extra1Dp;
    boolean isLeftEdge = x == 0.0f;
    int newLeft = (int) (isLeftEdge ? x : x - radius);
    int newRight = (int) (isLeftEdge ? x + width + 2 * radius : x + width + radius);
    RectF rect = new RectF(newLeft, newTop, newRight, newBottom);
    paint.setColor(backgroundColor);
    canvas.drawRoundRect(rect, radius, radius, paint);

    float textX = isLeftEdge ? x + radius : x;
    float textY = bottom - paint.getFontMetricsInt().bottom;
    paint.setColor(textColor == 0 ?  originalTextColor: textColor);
    canvas.drawText(text, start, end, textX, textY, paint);
  }

}