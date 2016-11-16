package com.jaychang.st;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.tag;

public class SimpleText extends SpannableString {

  private static final int SPAN_MODE = SPAN_EXCLUSIVE_EXCLUSIVE;
  private ArrayList<Range> rangeList = new ArrayList<>();
  private ArrayMap<Range,Object> tagsMap = new ArrayMap<>();
  private Context context;
  private int textColor;
  private int pressedTextColor;
  private int pressedBackgroundColor;
  private int pressedBackgroundRadius;

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

  public SimpleText all() {
    rangeList.clear();
    Range range = Range.create(0, toString().length());
    rangeList.add(range);
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

  public SimpleText between(String startText, String endText) {
    rangeList.clear();
    int startIndex = toString().indexOf(startText) + startText.length();
    int endIndex = toString().lastIndexOf(endText);
    Range range = Range.create(startIndex, endIndex);
    rangeList.add(range);
    return this;
  }

  public SimpleText size(int dp) {
    for (Range range : rangeList) {
      setSpan(new AbsoluteSizeSpan(dp, true), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText scaleSize(int proportion) {
    for (Range range : rangeList) {
      setSpan(new RelativeSizeSpan(proportion), range.from, range.to, SPAN_MODE);
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

  public SimpleText background(@ColorRes int colorRes, int radiusDp) {
    int color = ContextCompat.getColor(context, colorRes);
    int radiusPx = Utils.dp2px(context, radiusDp);
    for (Range range : rangeList) {
      setSpan(new RoundedBackgroundSpan(color, textColor, radiusPx), range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText textColor(@ColorRes int colorRes) {
    textColor = ContextCompat.getColor(context, colorRes);
    for (Range range : rangeList) {
      setSpan(new ForegroundColorSpan(textColor), range.from, range.to, SPAN_MODE);
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

  public SimpleText pressedTextColor(@ColorRes int colorRes) {
    this.pressedTextColor = ContextCompat.getColor(context, colorRes);
    return this;
  }

  public SimpleText pressedBackground(@ColorRes int colorRes, int radiusDp) {
    this.pressedBackgroundColor = ContextCompat.getColor(context, colorRes);
    this.pressedBackgroundRadius = Utils.dp2px(context, radiusDp);
    return this;
  }

  public SimpleText pressedBackground(@ColorRes int colorRes) {
    return pressedBackground(colorRes, 0);
  }

  public SimpleText onClick(final com.jaychang.st.OnTextClickListener onTextClickListener) {
    for (final Range range : rangeList) {
      NoUnderLineClickableSpan span = new NoUnderLineClickableSpan(
        subSequence(range.from, range.to),
        tagsMap.get(range),
        range,
        onTextClickListener);
      setSpan(span, range.from, range.to, SPAN_MODE);
    }

    return this;
  }

  public SimpleText tag(Object tag) {
    Range lastRange = rangeList.get(rangeList.size() - 1);
    tagsMap.put(lastRange, tag);
    return this;
  }

  public SimpleText tags(Object... tags) {
    int i = 0;
    for (Object tag : tags) {
      tagsMap.put(rangeList.get(i++), tag);
    }
    return this;
  }

  public void linkify(TextView textView) {
    textView.setMovementMethod(new LinkTouchMovementMethod(pressedTextColor, pressedBackgroundColor, pressedBackgroundRadius));
  }

  @Deprecated
  public interface OnTextClickListener {
    void onTextClicked(CharSequence text, Range range);
  }

  @Deprecated
  public SimpleText clickable(@ColorRes int pressedTextColor,
                              @ColorRes int pressedBackgroundColor,
                              int pressedBackgroundRadius,
                              final OnTextClickListener onTextClickListener) {

    this.pressedTextColor = ContextCompat.getColor(context, pressedTextColor);
    this.pressedBackgroundColor = ContextCompat.getColor(context, pressedBackgroundColor);
    this.pressedBackgroundRadius = Utils.dp2px(context, pressedBackgroundRadius);

    for (final Range range : rangeList) {
      ClickableSpan span = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
          onTextClickListener.onTextClicked(subSequence(range.from, range.to), range);
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

}
