package com.jaychang.demo.st;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jaychang.st.Main;
import com.jaychang.st.SimpleText;

import static com.jaychang.demo.st.R.color.link;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView textView = (TextView) findViewById(R.id.textView);

    String text = "This is a simple #foo @bar text \n SimpleText";
    String url = "https://github.com/jaychang0917/SimpleText";

    SimpleText simpleText = SimpleText.create(this, text)
      .allStartWith("#", "@")
      .textColor(R.color.link)
      .clickable(textView, R.color.pressedText, R.color.pressedBg, 2, new SimpleText.OnTextClickListener() {
        @Override
        public void onTextClicked(CharSequence text) {
          Toast.makeText(MainActivity.this, text.toString(), Toast.LENGTH_SHORT).show();
        }
      })
      .first("simple").textColor(R.color.colorAccent)
      .first("SimpleText").bold().textColor(R.color.link).url(url);

    textView.setText(simpleText);
  }
}
