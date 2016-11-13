package com.jaychang.st;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SimpleText extends SpannableString {

  private static final int SPAN_MODE = SPAN_EXCLUSIVE_EXCLUSIVE;
  private ArrayList<Range> rangeList = new ArrayList<>();
  private Context context;

  private SimpleText(Context context, CharSequence text) {
    super(text);
    this.context = context.getApplicationContext();
  }

  public static SimpleText create(Context context, CharSequence text) {
    return new SimpleText(context, text);
  }

  public SimpleText first(String target) {
    rangeList.clear();
    int index = toString().indexOf(target);
    Range range = Range.create(index, index + target.length());
    rangeList.add(range);
    return this;
  }

  public SimpleText last(String target) {
    rangeList.clear();
    int index = toString().lastIndexOf(target);
    Range range = Range.create(index, index + target.length());
    rangeList.add(range);
    return this;
  }

  public SimpleText all(String target) {
    rangeList.clear();
    List<Integer> indexes = StringUtils.indexesOf(toString(), target);
    for (Integer index : indexes) {
      Range range = Range.create(index, index + target.length());
      rangeList.add(range);
    }
    return this;
  }

  public SimpleText allStartWith(String... prefixs) {
    rangeList.clear();
    for (String prefix : prefixs) {
      List<Range> ranges = StringUtils.ranges(toString(), prefix + "\\w+");
      for (Range range : ranges) {
        rangeList.add(range);
      }
    }
    return this;
  }

  public SimpleText between(String startText, String endText) {
    rangeList.clear();
    int startIndex = toString().indexOf(startText) + startText.length();
    int endIndex = toString().lastIndexOf(endText);
    Range range = Range.create(startIndex, endIndex);
    rangeList.add(range);
    return this;
  }

  public SimpleText at(int position, int length) {
    rangeList.clear();
    Range range = Range.create(position, position + length);
    rangeList.add(range);
    return this;
  }

  public SimpleText all() {
    rangeList.clear();
    Range range = Range.create(0, toString().length());
    rangeList.add(range);
    return this;
  }

  public SimpleText range(int from, int to) {
    rangeList.clear();
    Range range = Range.create(from, to + 1);
    rangeList.add(range);
    return this;
  }

  public SimpleText ranges(List<Range> ranges) {
    rangeList.clear();
    rangeList.addAll(ranges);
    return this;
  }

  public SimpleText size(int dp) {
    for (Range range : rangeList) {
      setSpan(new AbsoluteSizeSpan(dp, true), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText bold() {
    for (Range range : rangeList) {
      setSpan(new StyleSpan(Typeface.BOLD), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText italic() {
    for (Range range : rangeList) {
      setSpan(new StyleSpan(Typeface.ITALIC), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText font(String font) {
    for (Range range : rangeList) {
      setSpan(new TypefaceSpan(font), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText strikethrough() {
    for (Range range : rangeList) {
      setSpan(new StrikethroughSpan(), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText underline() {
    for (Range range : rangeList) {
      setSpan(new UnderlineSpan(), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText background(@ColorRes int colorRes) {
    int color = ContextCompat.getColor(context, colorRes);
    for (Range range : rangeList) {
      setSpan(new BackgroundColorSpan(color), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText background(@ColorRes int colorRes, int radius) {
    int color = ContextCompat.getColor(context, colorRes);
    int radiusDp = Utils.dp2px(context, radius);
    for (Range range : rangeList) {
      setSpan(new RoundedBackgroundSpan(color, radiusDp), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText textColor(@ColorRes int colorRes) {
    int color = ContextCompat.getColor(context, colorRes);
    for (Range range : rangeList) {
      setSpan(new ForegroundColorSpan(color), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText subscript() {
    for (Range range : rangeList) {
      setSpan(new SubscriptSpan(), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText superscript() {
    for (Range range : rangeList) {
      setSpan(new SuperscriptSpan(), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText url(String url) {
    for (Range range : rangeList) {
      setSpan(new NoUnderLineUrlSpan(url), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText clickable(final TextView view,
                              @ColorRes int pressedTextColor,
                              @ColorRes int pressedBackgroundColor,
                              int pressedBackgroundRadius,
                              final OnTextClickListener onTextClickListener) {

    int pTextColor = ContextCompat.getColor(context, pressedTextColor);
    int pBgColor = ContextCompat.getColor(context, pressedBackgroundColor);
    final int radiusDp = Utils.dp2px(context, pressedBackgroundRadius);

    view.setMovementMethod(LinkTouchMovementMethod.getInstance(pTextColor, pBgColor, radiusDp));

    for (final Range range : rangeList) {
      ClickableSpan span = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
          onTextClickListener.onTextClicked(subSequence(range.from, range.to));
        }

        @Override
        public void updateDrawState(TextPaint ds) {
          ds.setUnderlineText(false);
        }
      };
      setSpan(span, range.from, range.to, SPAN_MODE);
    }

    return this;
  }

  public interface OnTextClickListener {
    void onTextClicked(CharSequence text);
  }

}
