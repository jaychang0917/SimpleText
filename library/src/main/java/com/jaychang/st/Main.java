package com.jaychang.st;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

  public static void main(String[] args) {
    String yourString = "hi #how are #foo";
    Matcher matcher = Pattern.compile("#\\w+").matcher(yourString);
    while (matcher.find()) {
      System.out.println(matcher.start());
      System.out.println(matcher.end());
    }
  }

}
