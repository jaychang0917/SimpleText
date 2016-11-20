# SimpleText

[![Release](https://jitpack.io/v/jaychang0917/SimpleText.svg)](https://jitpack.io/#jaychang0917/SimpleText)

This libary aims to simplify the creation of spannable string.

##Features
- [x] long click event (added in v1.2.0)
- [x] bind a tag with the clicked text (added in v1.1.1)
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

![](https://github.com/jaychang0917/SimpleText/blob/master/SimpleText.gif)

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
    compile 'com.github.jaychang0917:SimpleText:1.2.0'
}
```

##Usage
####Step 1: Match your target text(s)
| Method                       | Description                                     |
| -------------                |:-----------------------------------------------:|
| `first(text)`                | match first occurrence                          |
| `last(text)`                 | match last occurrence                           |
| `all(text)`                  | match all occurrences                           |
| `all()`                      | match whole text                                |
| `allStartWith(prefixs...)`   | match all occurrences with specified prefix(s)  |
| `range(from:to)`             | match text at specified position                |
| `ranges(ranges)`             | match all texts at specified positions          |
| `between(startText:endText)` | match text between two texts                    |

####Step 2: Apply style(s)

##Example
```java
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

simpleText.linkify(textView); // enable click event

textView.setText(simpleText);
```

##Change Log
[Change Log](https://github.com/jaychang0917/SimpleText/blob/master/CHANGLOG.md)

##Todo
- []

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
