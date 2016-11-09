package com.jaychang.st;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {

  private StringUtils() {
  }

  public static List<Integer> indexesOf(String src, String target) {
    List<Integer> positions = new ArrayList<>();
    for (int index = src.indexOf(target);
         index >= 0;
         index = src.indexOf(target, index + 1)) {
      positions.add(index);
    }
    return positions;
  }

  public static List<Range> ranges(String src, String pattern) {
    List<Range> ranges = new ArrayList<>();
    Matcher matcher = Pattern.compile(pattern).matcher(src);
    while (matcher.find()) {
      Range range = Range.create(matcher.start(), matcher.end());
      ranges.add(range);
    }
    return ranges;
  }

}
