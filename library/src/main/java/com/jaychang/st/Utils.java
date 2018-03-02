package com.jaychang.st;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

class Utils {

  static int dp2px(Context context, int dp) {
    float density = context.getApplicationContext().getResources().getDisplayMetrics().density;
    return (int) (dp * density + 0.5f);
  }

  static void openURL(Context context, String url) {
    Uri uri = Uri.parse(url);
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  static List<Integer> indexesOf(String src, String target) {
    List<Integer> positions = new ArrayList<>();
    for (int index = src.indexOf(target);
         index >= 0;
         index = src.indexOf(target, index + 1)) {
      positions.add(index);
    }
    return positions;
  }

  static List<Range> ranges(String src, String pattern) {
    List<Range> ranges = new ArrayList<>();
    Matcher matcher = Pattern.compile(pattern).matcher(src);
    while (matcher.find()) {
      Range range = Range.create(matcher.start(), matcher.end());
      ranges.add(range);
    }
    return ranges;
  }
}
