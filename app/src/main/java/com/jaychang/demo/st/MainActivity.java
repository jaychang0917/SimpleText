package com.jaychang.demo.st;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.jaychang.st.OnTextClickListener;
import com.jaychang.st.OnTextLongClickListener;
import com.jaychang.st.Range;
import com.jaychang.st.SimpleText;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView textView = (TextView) findViewById(R.id.textView);

    String text = "This is a simple #foo @bar text \n SimpleText";
    String url = "https://github.com/jaychang0917/SimpleText";

    User foo = new User("1001", "foo");
    User bar = new User("1002", "bar");

    SimpleText simpleText = SimpleText.create(this, text)
      .allStartWith("#", "@")
      .tags(foo, bar)
      .textColor(R.color.link)
      .pressedTextColor(R.color.pressedText)
      .pressedBackground(R.color.pressedBg, 2)
      .onClick(new OnTextClickListener() {
        @Override
        public void onClicked(CharSequence text, Range range, Object tag) {
          Toast.makeText(MainActivity.this, tag.toString(), Toast.LENGTH_SHORT).show();
        }
      })
      .first("simple").textColor(R.color.colorAccent)
      .first("SimpleText").bold().textColor(R.color.link).url(url)
      .onLongClick(new OnTextLongClickListener() {
        @Override
        public void onLongClicked(CharSequence text, Range range, Object tag) {
          Toast.makeText(MainActivity.this, "[long click] to share " + tag.toString(), Toast.LENGTH_SHORT).show();
        }
      });

    simpleText.linkify(textView);

    textView.setText(simpleText);
  }

}
