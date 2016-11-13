package com.jaychang.st;

import android.content.Context;

public class Utils {

  public static int dp2px(Context context, int dp) {
    float density = context.getApplicationContext().getResources().getDisplayMetrics().density;
    return (int) (dp * density + 0.5f);
  }

}
