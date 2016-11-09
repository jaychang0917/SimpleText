package com.jaychang.st;

import java.io.Serializable;

public class Range implements Serializable{

  public int from;
  public int to;

  private Range(int from, int to) {
    this.from = from;
    this.to = to;
  }

  public static Range create(int from, int to) {
    return new Range(from, to);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Range range = (Range) o;

    if (from != range.from) return false;
    return to == range.to;

  }

  @Override
  public int hashCode() {
    int result = from;
    result = 31 * result + to;
    return result;
  }

}