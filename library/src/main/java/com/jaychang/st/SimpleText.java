package com.jaychang.st;

import android.content.Context;
import android.graphics.Typeface;
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
import android.text.style.MetricAffectingSpan;
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

import static android.R.attr.radius;

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

  public SimpleText allStartWith(String prefix) {
    rangeList.clear();
    List<Range> ranges = StringUtils.ranges(toString(), prefix + "\\w+");
    for (Range range : ranges) {
      rangeList.add(range);
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

  private void applySpans(CharacterStyle span) {
    for (Range range : rangeList) {
      setSpan(span, range.from, range.to, SPAN_MODE);
    }
  }

  public SimpleText size(int dp) {
    applySpans(new AbsoluteSizeSpan(dp, true));
    return this;
  }

  public SimpleText bold() {
    applySpans(new StyleSpan(Typeface.BOLD));
    return this;
  }

  public SimpleText italic() {
    applySpans(new StyleSpan(Typeface.ITALIC));
    return this;
  }

  public SimpleText font(String font) {
    applySpans(new TypefaceSpan(font));
    return this;
  }

  public SimpleText strikethrough() {
    applySpans(new StrikethroughSpan());
    return this;
  }

  public SimpleText underline() {
    applySpans(new UnderlineSpan());
    return this;
  }

  public SimpleText backgroundColor(@ColorRes int colorRes) {
    int color = ContextCompat.getColor(context, colorRes);
    applySpans(new BackgroundColorSpan(color));
    return this;
  }

  public SimpleText roundBackground(@ColorRes int colorRes, int radius) {
    int color = ContextCompat.getColor(context, colorRes);
    applySpans(new RoundedBackgroundSpan(color, radius));
    return this;
  }

  public SimpleText textColor(@ColorRes int colorRes) {
    int color = ContextCompat.getColor(context, colorRes);
    applySpans(new ForegroundColorSpan(color));
    return this;
  }

  public SimpleText subscript() {
    applySpans(new SubscriptSpan());
    return this;
  }

  public SimpleText superscript() {
    applySpans(new SuperscriptSpan());
    return this;
  }

  public SimpleText url(String url) {
    applySpans(new URLSpan(url));
    return this;
  }


  // todo clicktable
  public SimpleText bind(TextView textView) {
    textView.setMovementMethod(LinkTouchMovementMethod.getInstance());
    return this;
  }

  public SimpleText clickable(final View.OnClickListener onClickListener, final boolean underline) {
    for (Range range : rangeList) {
      ClickableSpan span = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
          onClickListener.onClick(widget);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
          ds.setUnderlineText(underline);
        }
      };
      setSpan(span, range.from, range.to, SPAN_MODE);
    }
    return this;
  }

  public SimpleText clickable(final View.OnClickListener onClickListener) {
    return clickable(onClickListener, false);
  }

}
