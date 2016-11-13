# SimpleText

[![Release](https://jitpack.io/v/jaychang0917/SimpleText.svg)](https://jitpack.io/#jaychang0917/SimpleText)

##Features
- [x] text background (with round corner)
- [x] click event (with pressed color state)
- [x] text size
- [x] text style (bold, italic)
- [x] url
- [x] text font
- [x] strikethrough
- [x] underline
- [x] subscript
- [x] superscript



##Setup
In your project level build.gradle :

```java
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

In your app level build.gradle :

```java
dependencies {
    compile 'com.github.jaychang0917:SocialLoginManager:1.1.2'
}
```

##Usage
```java
TextView textView = (TextView) findViewById(R.id.textView);

String text = "This is a simple #foo @bar text \n SimpleText";
String url = "https://github.com/jaychang0917/SimpleText";

SimpleText simpleText = SimpleText.create(this, text)
  .allStartWith("#", "@").textColor(R.color.link)
  .clickable(R.color.pressedText, R.color.pressedBg, 2, new SimpleText.OnTextClickListener() {
    @Override
    public void onTextClicked(CharSequence text, Range range) {
      Toast.makeText(MainActivity.this, text.toString(), Toast.LENGTH_SHORT).show();
    }
  })
  .first("simple").textColor(R.color.colorAccent)
  .first("SimpleText").bold().textColor(R.color.link).url(url);

simpleText.linkify(textView); // enable click event for `clickable()` and `url()`

textView.setText(simpleText);
```

##License
```
Copyright 2016 Jay Chang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
