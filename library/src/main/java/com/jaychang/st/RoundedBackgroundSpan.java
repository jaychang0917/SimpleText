package com.jaychang.st;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;

public class RoundedBackgroundSpan extends ReplacementSpan {

  private int radius;
  private int backgroundColor;

  public RoundedBackgroundSpan(int backgroundColor, int radius) {
    this.backgroundColor = backgroundColor;
    this.radius = radius;
  }

  @Override
  public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
    return (int) (radius + paint.measureText(text.subSequence(start, end).toString()) + radius);
  }

  @Override
  public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
    int originalTextColor = paint.getColor();
    float width = paint.measureText(text.subSequence(start, end).toString());
    RectF rect = new RectF(x, top + radius, x + width + 2 * radius, bottom);
    paint.setColor(backgroundColor);
    canvas.drawRoundRect(rect, radius, radius, paint);
    paint.setColor(originalTextColor);
    canvas.drawText(text, start, end, x + radius, y, paint);
  }

}